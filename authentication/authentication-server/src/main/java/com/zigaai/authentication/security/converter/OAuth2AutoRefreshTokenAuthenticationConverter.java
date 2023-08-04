package com.zigaai.authentication.security.converter;

import com.zigaai.authentication.model.constants.OAuth2RedisKeys;
import com.zigaai.authentication.security.processor.refreshtoken.OAuth2AutoRefreshTokenAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public final class OAuth2AutoRefreshTokenAuthenticationConverter implements AuthenticationConverter {

    private final RedisTemplate<String, Object> redisTemplate;

    private final JwtDecoder jwtDecoder;

    @Override
    public Authentication convert(HttpServletRequest request) {
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(grantType) || !StringUtils.hasText(accessToken)) {
            return null;
        }
        accessToken = accessToken.substring(OAuth2AccessToken.TokenType.BEARER.getValue().length()).trim();
        Jwt jwt = jwtDecoder.decode(accessToken);
        if (System.currentTimeMillis() - jwt.getExpiresAt().toEpochMilli() < TimeUnit.MINUTES.toMillis(5)) {
            System.out.println("aaaaaaa");
        }
        String refreshToken = (String) redisTemplate.opsForValue().get(OAuth2RedisKeys.REL_ACCESS_TOKEN_REFRESH_TOKEN.apply(accessToken));
        if (!StringUtils.hasText(refreshToken)) {
            return null;
        }
        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
        MultiValueMap<String, String> parameters = this.getParameters(request);
        // scope (OPTIONAL)
        String scope = parameters.getFirst(OAuth2ParameterNames.SCOPE);
        if (StringUtils.hasText(scope) &&
                parameters.get(OAuth2ParameterNames.SCOPE).size() != 1) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST,
                    "OAuth 2.0 Parameter: " + OAuth2ParameterNames.SCOPE,
                    "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2");
            throw new OAuth2AuthenticationException(error);
        }
        Set<String> requestedScopes = null;
        if (StringUtils.hasText(scope)) {
            requestedScopes = new HashSet<>(
                    Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")));
        }

        Map<String, Object> additionalParameters = new HashMap<>();
        parameters.forEach((key, value) -> {
            if (!key.equals(OAuth2ParameterNames.GRANT_TYPE) &&
                    !key.equals(OAuth2ParameterNames.REFRESH_TOKEN) &&
                    !key.equals(OAuth2ParameterNames.SCOPE)) {
                additionalParameters.put(key, (value.size() == 1) ? value.get(0) : value.toArray(new String[0]));
            }
        });

        return new OAuth2AutoRefreshTokenAuthenticationToken(
                refreshToken, clientPrincipal, requestedScopes, additionalParameters);
    }

    private MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
        parameterMap.forEach((key, values) -> {
            for (String value : values) {
                parameters.add(key, value);
            }
        });
        return parameters;
    }

}
