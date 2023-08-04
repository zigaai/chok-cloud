package com.zigaai.authentication.security.processor.refreshtoken;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2RefreshTokenAuthenticationToken;

import java.util.Map;
import java.util.Set;

public class OAuth2AutoRefreshTokenAuthenticationToken extends OAuth2RefreshTokenAuthenticationToken {

    /**
     * Constructs an {@code OAuth2RefreshTokenAuthenticationToken} using the provided parameters.
     *
     * @param refreshToken         the refresh token
     * @param clientPrincipal      the authenticated client principal
     * @param scopes               the requested scope(s)
     * @param additionalParameters the additional parameters
     */
    public OAuth2AutoRefreshTokenAuthenticationToken(String refreshToken, Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
        super(refreshToken, clientPrincipal, scopes, additionalParameters);
    }

}
