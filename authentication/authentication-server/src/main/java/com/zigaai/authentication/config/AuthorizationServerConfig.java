package com.zigaai.authentication.config;

import com.zigaai.authentication.config.keygen.UUIDOAuth2AuthorizationCodeGenerator;
import com.zigaai.authentication.security.filter.JwtFilter;
import com.zigaai.authentication.security.handler.OAuth2AuthenticationEntryPoint;
import com.zigaai.authentication.security.handler.OAuth2AuthenticationSuccessHandler;
import com.zigaai.authentication.security.handler.OAuth2AuthorizationErrorHandler;
import com.zigaai.authentication.security.oauth2.RedisOAuth2AuthorizationService;
import com.zigaai.common.security.handler.DefaultAccessDeniedHandler;
import com.zigaai.common.security.handler.DefaultAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationConsentAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthorizationServerConfig {

    private final OAuth2AuthenticationEntryPoint oauth2AuthenticationEntryPoint;

    private final RegisteredClientRepository registeredClientRepository;

    private final RedisOAuth2AuthorizationService redisOAuth2AuthorizationService;

    private final OAuth2AuthorizationErrorHandler oauth2AuthorizationErrorHandler;

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private final DefaultAccessDeniedHandler defaultAccessDeniedHandler;

    private final DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;

    private final JwtFilter jwtFilter;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults())    // Enable OpenID Connect 1.0
                .clientAuthentication(clientAuthentication -> clientAuthentication.errorResponseHandler(oauth2AuthorizationErrorHandler))
                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint.errorResponseHandler(oauth2AuthorizationErrorHandler)
                                .authenticationProviders(authenticationProviders -> {
                                    if (!CollectionUtils.isEmpty(authenticationProviders)) {
                                        authenticationProviders.subList(0, authenticationProviders.size()).clear();
                                    }
                                    authenticationProviders.addAll(buildProviders());
                                })
                        // .consentPage("https://cn.bing.com")
                )
                .tokenEndpoint(tokenEndpoint -> tokenEndpoint
                        .errorResponseHandler(oauth2AuthorizationErrorHandler)
                        .accessTokenResponseHandler(oAuth2AuthenticationSuccessHandler)
                        // .accessTokenRequestConverters(converters -> converters.add(0, buildOAuth2AutoRefreshTokenAuthenticationConverter()))
                );
        http
                // Redirect to the login page when not authenticated from the
                // authorization endpoint
                .exceptionHandling(exceptions -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                        .authenticationEntryPoint(oauth2AuthenticationEntryPoint)
                )
                // Accept access tokens for User Info and/or Client Registration
                .oauth2ResourceServer((resourceServer) -> resourceServer
                        .jwt(Customizer.withDefaults())
                        .accessDeniedHandler(defaultAccessDeniedHandler)
                        .authenticationEntryPoint(defaultAuthenticationEntryPoint)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);
        http.addFilterAfter(jwtFilter, SecurityContextHolderFilter.class);
        return http.build();
    }

    private List<AuthenticationProvider> buildProviders() {
        InMemoryOAuth2AuthorizationConsentService inMemoryOAuth2AuthorizationConsentService = new InMemoryOAuth2AuthorizationConsentService();
        UUIDOAuth2AuthorizationCodeGenerator UUIDOAuth2AuthorizationCodeGenerator = new UUIDOAuth2AuthorizationCodeGenerator();
        OAuth2AuthorizationCodeRequestAuthenticationProvider oAuth2AuthorizationCodeRequestAuthenticationProvider =
                new OAuth2AuthorizationCodeRequestAuthenticationProvider(
                        registeredClientRepository,
                        redisOAuth2AuthorizationService,
                        inMemoryOAuth2AuthorizationConsentService
                );
        oAuth2AuthorizationCodeRequestAuthenticationProvider.setAuthorizationCodeGenerator(UUIDOAuth2AuthorizationCodeGenerator);
        OAuth2AuthorizationConsentAuthenticationProvider oAuth2AuthorizationConsentAuthenticationProvider =
                new OAuth2AuthorizationConsentAuthenticationProvider(
                        registeredClientRepository,
                        redisOAuth2AuthorizationService,
                        inMemoryOAuth2AuthorizationConsentService
                );
        oAuth2AuthorizationConsentAuthenticationProvider.setAuthorizationCodeGenerator(UUIDOAuth2AuthorizationCodeGenerator);
        return Arrays.asList(oAuth2AuthorizationCodeRequestAuthenticationProvider, oAuth2AuthorizationConsentAuthenticationProvider);
    }

    // private OAuth2AutoRefreshTokenAuthenticationConverter buildOAuth2AutoRefreshTokenAuthenticationConverter() {
    //     return new OAuth2AutoRefreshTokenAuthenticationConverter(redisTemplate, jwtDecoder, registeredClientRepository);
    // }

}
