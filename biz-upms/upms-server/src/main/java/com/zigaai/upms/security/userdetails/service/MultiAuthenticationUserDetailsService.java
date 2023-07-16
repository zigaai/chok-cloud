package com.zigaai.upms.security.userdetails.service;

import com.zigaai.common.core.infra.strategy.Strategy;
import com.zigaai.upms.model.enumeration.SysUserType;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MultiAuthenticationUserDetailsService extends UserDetailsService, Strategy<SysUserType> {
}
