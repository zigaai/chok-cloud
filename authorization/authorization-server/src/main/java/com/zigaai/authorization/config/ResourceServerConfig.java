package com.zigaai.authorization.config;

import com.zigaai.authorization.model.enumeration.LoginType;
import com.zigaai.authorization.model.properties.CustomSecurityProperties;
import com.zigaai.authorization.security.DaoMultiAuthenticationProvider;
import com.zigaai.authorization.security.JwtFilter;
import com.zigaai.authorization.security.filter.LoginAuthenticationFilter;
import com.zigaai.authorization.security.handler.DefaultAccessDeniedHandler;
import com.zigaai.authorization.security.handler.DefaultAuthenticationEntryPoint;
import com.zigaai.authorization.security.processor.LoginProcessor;
import com.zigaai.authorization.security.userdetails.service.MultiAuthenticationUserDetailsService;
import com.zigaai.common.core.infra.strategy.StrategyFactory;
import com.zigaai.common.core.model.enumeration.SysUserType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.HeaderWriterFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(CustomSecurityProperties.class)
public class ResourceServerConfig {

    private final MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;

    private final JwtFilter jwtFilter;

    private final CustomSecurityProperties securityProperties;

    private final DefaultAccessDeniedHandler defaultAccessDeniedHandler;

    private final DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;

    private final StrategyFactory<LoginType, LoginProcessor> loginTypeLoginProcessorStrategy;

    private final StrategyFactory<SysUserType, MultiAuthenticationUserDetailsService> userDetailsServiceStrategy;

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        // // DaoMultiAuthenticationProvider daoMultiAuthenticationProvider = new DaoMultiAuthenticationProvider();
        // // daoMultiAuthenticationProvider.setUserDetailsService(userDetailsService);
        // http
        //         .authorizeHttpRequests((authorize) -> authorize
        //                 .anyRequest().authenticated()
        //         )
        //         // Form login handles the redirect to the login page from the
        //         // authorization server filter chain
        //         // .formLogin(Customizer.withDefaults())
        //         .formLogin(AbstractHttpConfigurer::disable)
        //         .sessionManagement(AbstractHttpConfigurer::disable)
        //         .csrf(AbstractHttpConfigurer::disable)
        //         .logout(AbstractHttpConfigurer::disable)
        //         .exceptionHandling(config -> {
        //             config
        //                     .accessDeniedHandler((request, response, e) -> {
        //                         System.out.println("错误1: " + e.getLocalizedMessage());
        //                     })
        //                     .authenticationEntryPoint((request, response, e) -> {
        //                         System.out.println("错误2: " + e.getLocalizedMessage());
        //                     });
        //         })
        // // .authenticationManager(new ProviderManager(daoMultiAuthenticationProvider))
        // ;
        // http.addFilterBefore(new JwtFilter(systemUserRemoteService), AuthorizationFilter.class);
        //
        // return http.build();

        AuthenticationManager authenticationManager = buildAuthenticationManager();
        http.csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(config -> config.requestMatchers(securityProperties.getIgnoreUrls().toArray(String[]::new))
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .exceptionHandling(config -> config
                        .authenticationEntryPoint(defaultAuthenticationEntryPoint)
                        .accessDeniedHandler(defaultAccessDeniedHandler))
                .oauth2ResourceServer(resourceServer -> resourceServer
                        .jwt(Customizer.withDefaults())
                        .authenticationEntryPoint(defaultAuthenticationEntryPoint)
                        .accessDeniedHandler(defaultAccessDeniedHandler)
                );
        LoginAuthenticationFilter loginAuthenticationFilter = buildLoginFilter(authenticationManager);
        http.addFilterAfter(loginAuthenticationFilter, HeaderWriterFilter.class)
                .addFilterBefore(jwtFilter, LoginAuthenticationFilter.class);
        return http.build();
    }

    private LoginAuthenticationFilter buildLoginFilter(AuthenticationManager authenticationManager) {
        return new LoginAuthenticationFilter(loginTypeLoginProcessorStrategy, authenticationManager, securityProperties, jackson2HttpMessageConverter);
    }

    private AuthenticationManager buildAuthenticationManager() {
        DaoMultiAuthenticationProvider daoMultiAuthenticationProvider = new DaoMultiAuthenticationProvider(userDetailsServiceStrategy);
        return new ProviderManager(daoMultiAuthenticationProvider);
    }

}
