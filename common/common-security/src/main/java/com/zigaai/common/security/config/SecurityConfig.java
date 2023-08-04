package com.zigaai.common.security.config;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.zigaai.common.core.infra.strategy.StrategyFactory;
import com.zigaai.common.core.model.enumeration.SysUserType;
import com.zigaai.common.security.feign.AuthenticationRemoteService;
import com.zigaai.common.security.handler.DefaultAccessDeniedHandler;
import com.zigaai.common.security.handler.DefaultAuthenticationEntryPoint;
import com.zigaai.common.security.service.AuthenticationService;
import com.zigaai.common.security.utils.RsaUtil;
import com.zigaai.common.security.validator.JwtSaltValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@AutoConfiguration
@RequiredArgsConstructor
public class SecurityConfig {

    private final StrategyFactory<SysUserType, AuthenticationService> authenticationServiceStrategy;

    private final DefaultAccessDeniedHandler defaultAccessDeniedHandler;

    private final DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;

    @Bean
    @Order(3)
    @ConditionalOnBean(AuthenticationRemoteService.class)
    public SecurityFilterChain defaultResourceSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                // Form login handles the redirect to the login page from the
                // authorization server filter chain
                // .formLogin(Customizer.withDefaults());
                .sessionManagement(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(resourceServer -> resourceServer
                        // TODO 用户信息表存 Authorization Server 中, 每次通过feign调用并缓存Redis
                        // .jwt(new Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer>() {
                        //     @Override
                        //     public void customize(OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer jwtConfigurer) {
                        //         jwtConfigurer.jwtAuthenticationConverter(new Converter<Jwt, AbstractAuthenticationToken>() {
                        //             @Override
                        //             public AbstractAuthenticationToken convert(Jwt source) {
                        //                 log.info("claims: {}", source.getClaims());
                        //                 return new UsernamePasswordAuthenticationToken();
                        //             }
                        //         });
                        //     }
                        // })
                        .jwt(Customizer.withDefaults())
                        .authenticationEntryPoint(defaultAuthenticationEntryPoint)
                        .accessDeniedHandler(defaultAccessDeniedHandler)
                )
                .exceptionHandling(config -> config
                        .authenticationEntryPoint(defaultAuthenticationEntryPoint)
                        .accessDeniedHandler(defaultAccessDeniedHandler)
                )
        ;
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        Set<JWSAlgorithm> jwsAlgs = new HashSet<>();
        jwsAlgs.addAll(JWSAlgorithm.Family.RSA);
        jwsAlgs.addAll(JWSAlgorithm.Family.EC);
        jwsAlgs.addAll(JWSAlgorithm.Family.HMAC_SHA);
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        JWSKeySelector<SecurityContext> jwsKeySelector =
                new JWSVerificationKeySelector<>(jwsAlgs, jwkSource);
        jwtProcessor.setJWSKeySelector(jwsKeySelector);
        // Override the default Nimbus claims set verifier as NimbusJwtDecoder handles it instead
        jwtProcessor.setJWTClaimsSetVerifier((claims, context) -> {
        });
        NimbusJwtDecoder decoder = new NimbusJwtDecoder(jwtProcessor);
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(Arrays.asList(new JwtTimestampValidator(), new JwtSaltValidator(authenticationServiceStrategy))));
        return decoder;
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = new RSAKey.Builder(RsaUtil.getPublicKey())
                .privateKey(RsaUtil.getPrivateKey())
                // .keyID(uuid)
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }



}
