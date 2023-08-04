package com.zigaai.authentication.config;

import com.zigaai.authentication.model.enumeration.LoginType;
import com.zigaai.authentication.security.processor.LoginProcessor;
import com.zigaai.authentication.security.userdetails.service.MultiAuthenticationUserDetailsService;
import com.zigaai.common.core.infra.strategy.StrategyFactory;
import com.zigaai.common.core.model.enumeration.SysUserType;
import com.zigaai.common.security.service.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StrategyConfig {

    @Bean
    public StrategyFactory<SysUserType, MultiAuthenticationUserDetailsService> userDetailsServiceStrategy() {
        return new StrategyFactory<>(MultiAuthenticationUserDetailsService.class);
    }

    @Bean
    public StrategyFactory<LoginType, LoginProcessor> loginTypeLoginProcessorStrategy() {
        return new StrategyFactory<>(LoginProcessor.class);
    }

    @Bean
    public StrategyFactory<SysUserType, AuthenticationService> authenticationServiceStrategy() {
        return new StrategyFactory<>(AuthenticationService.class);
    }

}
