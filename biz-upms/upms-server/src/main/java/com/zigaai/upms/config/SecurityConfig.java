// package com.zigaai.upms.config;
//
// import com.zigaai.common.core.infra.strategy.StrategyFactory;
// import com.zigaai.common.core.model.enumeration.SysUserType;
// import com.zigaai.upms.model.properties.CustomSecurityProperties;
// import com.zigaai.upms.model.enumeration.LoginType;
// import com.zigaai.upms.security.DaoMultiAuthenticationProvider;
// import com.zigaai.upms.security.filter.JwtFilter;
// import com.zigaai.upms.security.filter.LoginAuthenticationFilter;
// import com.zigaai.upms.security.handler.DefaultAccessDeniedHandler;
// import com.zigaai.upms.security.handler.DefaultAuthenticationEntryPoint;
// import com.zigaai.upms.security.processor.LoginProcessor;
// import com.zigaai.upms.security.userdetails.service.MultiAuthenticationUserDetailsService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.boot.context.properties.EnableConfigurationProperties;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.ProviderManager;
// import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.header.HeaderWriterFilter;
//
// @Configuration
// @EnableWebSecurity
// @EnableMethodSecurity
// @RequiredArgsConstructor
// @EnableConfigurationProperties(CustomSecurityProperties.class)
// public class SecurityConfig {
//
//     private final MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;
//
//     private final CustomSecurityProperties securityProperties;
//
//     private final DefaultAccessDeniedHandler defaultAccessDeniedHandler;
//
//     private final DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;
//
//     private final StrategyFactory<LoginType, LoginProcessor> loginTypeLoginProcessorStrategy;
//
//     private final StrategyFactory<SysUserType, MultiAuthenticationUserDetailsService> userDetailsServiceStrategy;
//
//     // private final LoginAuthenticationFilter loginAuthenticationFilter;
//
//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         AuthenticationManager authenticationManager = buildAuthenticationManager();
//         http.csrf(AbstractHttpConfigurer::disable)
//                 .formLogin(AbstractHttpConfigurer::disable)
//                 .logout(AbstractHttpConfigurer::disable)
//                 .sessionManagement(AbstractHttpConfigurer::disable)
//                 .authorizeHttpRequests(config -> config.requestMatchers(securityProperties.getIgnoreUrls().toArray(String[]::new))
//                         .permitAll()
//                         .anyRequest()
//                         .authenticated()
//                 )
//                 .exceptionHandling(config -> config
//                         .authenticationEntryPoint(defaultAuthenticationEntryPoint)
//                         .accessDeniedHandler(defaultAccessDeniedHandler))
//         // .authenticationManager(authenticationManager)
//         ;
//         LoginAuthenticationFilter loginAuthenticationFilter = buildLoginFilter(authenticationManager);
//         JwtFilter jwtFilter = buildJwtFilter();
//         http.addFilterAfter(loginAuthenticationFilter, HeaderWriterFilter.class)
//                 .addFilterBefore(jwtFilter, LoginAuthenticationFilter.class);
//         return http.build();
//     }
//
//     private LoginAuthenticationFilter buildLoginFilter(AuthenticationManager authenticationManager) {
//         return new LoginAuthenticationFilter(loginTypeLoginProcessorStrategy, authenticationManager, securityProperties, jackson2HttpMessageConverter);
//     }
//
//     private JwtFilter buildJwtFilter() {
//         return new JwtFilter(securityProperties, userDetailsServiceStrategy);
//     }
//
//     private AuthenticationManager buildAuthenticationManager() {
//         DaoMultiAuthenticationProvider daoMultiAuthenticationProvider = new DaoMultiAuthenticationProvider(userDetailsServiceStrategy);
//         return new ProviderManager(daoMultiAuthenticationProvider);
//     }
//
// }
