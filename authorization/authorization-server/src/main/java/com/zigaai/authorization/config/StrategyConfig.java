package com.zigaai.authorization.config;

import com.zigaai.common.core.infra.strategy.StrategyFactory;
import com.zigaai.common.core.model.enumeration.SysUserType;
import com.zigaai.authorization.model.enumeration.LoginType;
import com.zigaai.authorization.security.processor.LoginProcessor;
import com.zigaai.authorization.security.userdetails.service.MultiAuthenticationUserDetailsService;
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

}
