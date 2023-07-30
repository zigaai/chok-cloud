package com.zigaai.authorization.service.impl;

import com.zigaai.common.core.model.enumeration.SysUserType;
import com.zigaai.authorization.mapper.AdminMapper;
import com.zigaai.authorization.service.AuthenticationService;
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
