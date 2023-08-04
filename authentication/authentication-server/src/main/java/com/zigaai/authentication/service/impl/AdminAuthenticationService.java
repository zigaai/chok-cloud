package com.zigaai.authentication.service.impl;

import com.zigaai.authentication.mapper.AdminMapper;
import com.zigaai.common.core.model.enumeration.SysUserType;
import com.zigaai.common.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminAuthenticationService implements AuthenticationService {

    private final AdminMapper adminMapper;

    @Override
    public SysUserType getKey() {
        return SysUserType.ADMIN;
    }

    @Override
    public String getSaltByUsername(String username) {
        return adminMapper.getSaltByUsername(username);
    }
}
