package com.zigaai.authorization.security.processor;

import com.zigaai.common.core.infra.strategy.Strategy;
import com.zigaai.authorization.model.enumeration.LoginType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface LoginProcessor extends Strategy<LoginType> {

    Authentication buildUnauthenticated(HttpServletRequest request);

}
