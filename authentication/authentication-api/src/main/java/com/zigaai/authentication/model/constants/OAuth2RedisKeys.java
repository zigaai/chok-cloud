package com.zigaai.authentication.model.constants;

import com.zigaai.authentication.model.dto.command.PrincipalKey;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

import java.util.function.Function;

public final class OAuth2RedisKeys {

    private OAuth2RedisKeys() {
    }

    public static final String OAUTH2_PREFIX = "oauth2:";

    public static final Function<String, String> AUTHORIZATION_CODE = key -> OAUTH2_PREFIX + AuthorizationGrantType.AUTHORIZATION_CODE.getValue() + ":" + key;

    public static final Function<String, String> ACCESS_TOKEN = key -> OAUTH2_PREFIX + OAuth2ParameterNames.ACCESS_TOKEN + ":" + key;

    public static final Function<PrincipalKey, String> RESOURCE_PRINCIPAL = principalKey -> OAUTH2_PREFIX + "principal:" + principalKey.getPrincipalName() + ":" + principalKey.getAuthorizationId();

    public static final Function<String, String> REFRESH_TOKEN = key -> OAUTH2_PREFIX + OAuth2ParameterNames.REFRESH_TOKEN + ":" + key;

    public static final Function<String, String> REL_ACCESS_TOKEN_REFRESH_TOKEN = key -> OAUTH2_PREFIX + OAuth2ParameterNames.ACCESS_TOKEN + "_to_" + OAuth2ParameterNames.REFRESH_TOKEN + ":" + key;

    public static final Function<String, String> OIDC_TOKEN = key -> OAUTH2_PREFIX + "oidc_token:" + key;

    public static final Function<String, String> OAUTH2_STATE_CODE = key -> OAUTH2_PREFIX + OAuth2ParameterNames.STATE + ":" + key;

    public static final Function<String, String> OAUTH2_AUTHORIZATION = key -> OAUTH2_PREFIX + "authorization:" + key;

    public static final Function<String, String> OAUTH2_REGISTERED_CLIENT = key -> OAUTH2_PREFIX + "client:" + key;

}
