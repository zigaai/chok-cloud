package com.zigaai.common.core.model.constants;

import org.springframework.http.HttpHeaders;

public final class SecurityConstant {

    private SecurityConstant() {
    }

    public static final String PRE_AUTHORIZATION_HEADER = "Pre-" + HttpHeaders.AUTHORIZATION;

}
