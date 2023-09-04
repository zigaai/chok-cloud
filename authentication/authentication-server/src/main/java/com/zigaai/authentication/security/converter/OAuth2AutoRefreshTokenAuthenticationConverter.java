// package com.zigaai.authentication.security.converter;
//
// import com.zigaai.authentication.model.constants.OAuth2RedisKeys;
// import com.zigaai.authentication.security.processor.refreshtoken.OAuth2AutoRefreshTokenAuthenticationToken;
// import com.zigaai.common.core.model.constants.SecurityConstant;
// import jakarta.servlet.http.HttpServletRequest;
// import lombok.RequiredArgsConstructor;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.http.HttpHeaders;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.oauth2.core.AuthorizationGrantType;
// import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
// import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
// import org.springframework.security.oauth2.jwt.Jwt;
// import org.springframework.security.oauth2.jwt.JwtDecoder;
// import org.springframework.security.oauth2.jwt.JwtException;
// import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
// import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
// import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
// import org.springframework.security.web.authentication.AuthenticationConverter;
// import org.springframework.util.LinkedMultiValueMap;
// import org.springframework.util.MultiValueMap;
// import org.springframework.util.StringUtils;
//
// import java.util.*;
//
// @RequiredArgsConstructor
// public final class OAuth2AutoRefreshTokenAuthenticationConverter implements AuthenticationConverter {
//
//     private final RedisTemplate<String, Object> redisTemplate;
//
//     private final JwtDecoder jwtDecoder;
//
//     private final RegisteredClientRepository registeredClientRepository;
//
//     @Override
//     public Authentication convert(HttpServletRequest request) {
//         String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
//         if (!AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(grantType)) {
//             return null;
//         }
//         String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
//         if (!StringUtils.hasText(accessToken) || !accessToken.startsWith(SecurityConstant.TOKEN_PREFIX)) {
//             return null;
//         }
//         accessToken = accessToken.substring(SecurityConstant.TOKEN_PREFIX.length());
//         Jwt jwt;
//         try {
//             jwt = jwtDecoder.decode(accessToken);
//         } catch (JwtException e) {
//             return null;
//         }
//         if (jwt == null || jwt.getExpiresAt() == null) {
//             return null;
//         }
//         if (jwt.getExpiresAt().toEpochMilli() - System.currentTimeMillis() > SecurityConstant.REFRESH_DURATION) {
//             return null;
//         }
//         String redisKey = OAuth2RedisKeys.REL_ACCESS_TOKEN_REFRESH_TOKEN.apply(accessToken);
//         String refreshToken = (String) redisTemplate.opsForValue().get(redisKey);
//         redisTemplate.delete(redisKey);
//         if (!StringUtils.hasText(refreshToken)) {
//             return null;
//         }
//         String clientId = jwt.getClaimAsString(SecurityConstant.TokenKey.CLIENT_ID);
//         RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientId);
//         if (registeredClient == null) {
//             return null;
//         }
//         MultiValueMap<String, String> parameters = this.getParameters(request);
//         // scope (OPTIONAL)
//         String scope = request.getParameter(OAuth2ParameterNames.SCOPE);
//         Set<String> requestedScopes = null;
//         if (StringUtils.hasText(scope)) {
//             requestedScopes = new HashSet<>(
//                     Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")));
//         }
//
//         Map<String, Object> additionalParameters = new HashMap<>();
//         parameters.forEach((key, value) -> {
//             if (!key.equals(OAuth2ParameterNames.GRANT_TYPE) &&
//                     !key.equals(OAuth2ParameterNames.REFRESH_TOKEN) &&
//                     !key.equals(OAuth2ParameterNames.SCOPE)) {
//                 additionalParameters.put(key, (value.size() == 1) ? value.get(0) : value.toArray(new String[0]));
//             }
//         });
//
//         OAuth2ClientAuthenticationToken oAuth2ClientAuthenticationToken = new OAuth2ClientAuthenticationToken(registeredClient,
//                 ClientAuthenticationMethod.CLIENT_SECRET_BASIC, Collections.emptyMap());
//         return new OAuth2AutoRefreshTokenAuthenticationToken(
//                 refreshToken, oAuth2ClientAuthenticationToken, requestedScopes, additionalParameters);
//     }
//
//     private MultiValueMap<String, String> getParameters(HttpServletRequest request) {
//         Map<String, String[]> parameterMap = request.getParameterMap();
//         MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
//         parameterMap.forEach((key, values) -> {
//             for (String value : values) {
//                 parameters.add(key, value);
//             }
//         });
//         return parameters;
//     }
//
// }
