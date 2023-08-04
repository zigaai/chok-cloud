package com.zigaai.common.security.config;

import com.zigaai.common.core.infra.strategy.StrategyFactory;
import com.zigaai.common.core.model.enumeration.SysUserType;
import com.zigaai.common.security.feign.AuthenticationRemoteService;
import com.zigaai.common.security.service.AuthenticationService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(before = SecurityConfig.class)
@ConditionalOnBean(AuthenticationRemoteService.class)
public class StrategyConfig {

    @Bean
    public StrategyFactory<SysUserType, AuthenticationService> authenticationServiceStrategy() {
        return new StrategyFactory<>(AuthenticationService.class);
    }

}
