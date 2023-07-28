package com.zigaai.upms.service;

import com.zigaai.common.core.infra.strategy.Strategy;
import com.zigaai.common.core.model.enumeration.SysUserType;

public interface AuthenticationService extends Strategy<SysUserType> {

    String getSaltByUsername(String username);
}
