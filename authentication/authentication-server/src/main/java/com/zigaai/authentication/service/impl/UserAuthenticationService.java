package com.zigaai.authentication.service.impl;

import com.zigaai.authentication.mapper.UserMapper;
import com.zigaai.common.core.model.enumeration.SysUserType;
import com.zigaai.common.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAuthenticationService implements AuthenticationService {

    private final UserMapper userMapper;

    @Override
    public SysUserType getKey() {
        return SysUserType.USER;
    }

    @Override
    public String getSaltByUsername(String username) {
        return userMapper.getSaltByUsername(username);
    }
}
