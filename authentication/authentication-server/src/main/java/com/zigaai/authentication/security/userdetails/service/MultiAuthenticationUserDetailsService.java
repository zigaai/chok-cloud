package com.zigaai.authentication.security.userdetails.service;

import com.zigaai.common.core.infra.strategy.Strategy;
import com.zigaai.common.core.model.enumeration.SysUserType;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MultiAuthenticationUserDetailsService extends UserDetailsService, Strategy<SysUserType> {

}
