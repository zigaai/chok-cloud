// package com.zigaai.upms.config;
//
// import com.zigaai.common.core.infra.strategy.StrategyFactory;
// import com.zigaai.upms.model.enumeration.LoginType;
// import com.zigaai.common.core.model.enumeration.SysUserType;
// import com.zigaai.upms.security.processor.LoginProcessor;
// import com.zigaai.upms.security.userdetails.service.MultiAuthenticationUserDetailsService;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// @Configuration
// public class StrategyConfig {
//
//     @Bean
//     public StrategyFactory<SysUserType, MultiAuthenticationUserDetailsService> userDetailsServiceStrategy() {
//         return new StrategyFactory<>(MultiAuthenticationUserDetailsService.class);
//     }
//
//     @Bean
//     public StrategyFactory<LoginType, LoginProcessor> loginTypeLoginProcessorStrategy() {
//         return new StrategyFactory<>(LoginProcessor.class);
//     }
//
// }
