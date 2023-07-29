// package com.zigaai.authorization.config;
//
// import com.zigaai.authorization.security.JwtFilter;
// import com.zigaai.upms.feign.SystemUserRemoteService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.annotation.Order;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.access.intercept.AuthorizationFilter;
//
// @Configuration
// @EnableWebSecurity
// @RequiredArgsConstructor
// public class ResourceServerConfig {
//
//     private final SystemUserRemoteService systemUserRemoteService;
//
//     @Bean
//     @Order(2)
//     public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
//             throws Exception {
//         // DaoMultiAuthenticationProvider daoMultiAuthenticationProvider = new DaoMultiAuthenticationProvider();
//         // daoMultiAuthenticationProvider.setUserDetailsService(userDetailsService);
//         http
//                 .authorizeHttpRequests((authorize) -> authorize
//                         .anyRequest().authenticated()
//                 )
//                 // Form login handles the redirect to the login page from the
//                 // authorization server filter chain
//                 // .formLogin(Customizer.withDefaults())
//                 .formLogin(AbstractHttpConfigurer::disable)
//                 .sessionManagement(AbstractHttpConfigurer::disable)
//                 .csrf(AbstractHttpConfigurer::disable)
//                 .logout(AbstractHttpConfigurer::disable)
//                 .exceptionHandling(config -> {
//                     config
//                             .accessDeniedHandler((request, response, e) -> {
//                                 System.out.println("错误1: " + e.getLocalizedMessage());
//                             })
//                             .authenticationEntryPoint((request, response, e) -> {
//                                 System.out.println("错误2: " + e.getLocalizedMessage());
//                             });
//                 })
//         // .authenticationManager(new ProviderManager(daoMultiAuthenticationProvider))
//         ;
//         http.addFilterBefore(new JwtFilter(systemUserRemoteService), AuthorizationFilter.class);
//
//         return http.build();
//     }
//
// }
