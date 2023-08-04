package com.zigaai.common.security.service;

import com.zigaai.common.core.model.dto.ResponseData;
import com.zigaai.common.core.model.enumeration.SysUserType;
import com.zigaai.common.security.feign.AuthenticationRemoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(AuthenticationRemoteService.class)
@RequiredArgsConstructor
public class AdminAuthenticationRemoteService implements AuthenticationService {

    private final AuthenticationRemoteService authenticationRemoteService;

    @Override
    public SysUserType getKey() {
        return SysUserType.ADMIN;
    }

    @Override
    public String getSaltByUsername(String username) {
        return ResponseData.unwrap(authenticationRemoteService.getSalt(username, getKey().getVal()));
    }

}
