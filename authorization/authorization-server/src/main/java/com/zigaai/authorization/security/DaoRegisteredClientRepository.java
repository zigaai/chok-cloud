package com.zigaai.authorization.security;

import com.zigaai.authorization.mapper.Oauth2RegisteredClientMapper;
import com.zigaai.authorization.model.entity.Oauth2RegisteredClient;
import com.zigaai.common.core.model.constants.DateTimeConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.ZoneId;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DaoRegisteredClientRepository implements RegisteredClientRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    private final Oauth2RegisteredClientMapper oauth2RegisteredClientMapper;

    @Override
    public void save(RegisteredClient registeredClient) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RegisteredClient findById(String id) {
        String clientId = oauth2RegisteredClientMapper.getClientIdById(id);
        if (!StringUtils.hasText(clientId)) {
            return null;
        }
        return findByClientId(clientId);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        // String key = OAuth2RedisKeys.OAUTH2_REGISTERED_CLIENT.apply(clientId);
        // RegisteredClient client = (RegisteredClient) redisTemplate.opsForValue().get(key);
        // if (client != null) {
        //     return client;
        // }
        RegisteredClient client;
        Oauth2RegisteredClient registeredClient = oauth2RegisteredClientMapper.getByClientId(clientId);
        if (registeredClient == null) {
            return null;
        }
        RegisteredClient.Builder builder = RegisteredClient.withId(registeredClient.getId().toString())
                .clientId(registeredClient.getClientId())
                .clientIdIssuedAt(registeredClient.getClientIdIssuedAt().atZone(DateTimeConstant.UTC_ZONE_ID).toInstant())
                .clientName(registeredClient.getClientName())
                .clientSecret(registeredClient.getClientSecret())
                .clientName(registeredClient.getClientName())
                .clientAuthenticationMethods(clientAuthenticationMethods ->
                        clientAuthenticationMethods.addAll(
                                StringUtils.commaDelimitedListToSet(registeredClient.getClientAuthenticationMethods()).stream().map(ClientAuthenticationMethod::new).toList()
                        )
                )
                .authorizationGrantTypes(authorizationGrantTypes -> authorizationGrantTypes.addAll(
                        StringUtils.commaDelimitedListToSet(registeredClient.getAuthorizationGrantTypes()).stream().map(AuthorizationGrantType::new).toList()
                ))
                .redirectUris(redirectUris -> redirectUris.addAll(StringUtils.commaDelimitedListToSet(registeredClient.getRedirectUris())))
                .scopes(scopes -> scopes.addAll(StringUtils.commaDelimitedListToSet(registeredClient.getScopes())));
        if (registeredClient.getClientSecretExpiresAt() != null) {
            builder.clientSecretExpiresAt(registeredClient.getClientSecretExpiresAt().atZone(DateTimeConstant.UTC_ZONE_ID).toInstant());
        }
        ClientSettings.Builder clientSettingsBuilder = ClientSettings.builder()
                .requireProofKey(false)
                .requireAuthorizationConsent(true);
        Map<String, Object> clientSettingsMap = registeredClient.parseClientSettings();
        if (!CollectionUtils.isEmpty(clientSettingsMap)) {
            clientSettingsMap.forEach((k, v) -> {
                if (v != null) {
                    clientSettingsBuilder.setting(k, v);
                }
            });
        }
        builder.clientSettings(clientSettingsBuilder.build());
        TokenSettings.Builder tokenSettingsBuilder = TokenSettings.builder()
                .authorizationCodeTimeToLive(Duration.ofMinutes(5))
                .accessTokenTimeToLive(Duration.ofMinutes(60))
                .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                .deviceCodeTimeToLive(Duration.ofMinutes(5))
                .reuseRefreshTokens(true)
                .refreshTokenTimeToLive(Duration.ofDays(7))
                .idTokenSignatureAlgorithm(SignatureAlgorithm.RS256);
        Map<String, Object> tokenSettingsMap = registeredClient.parseTokenSettings();
        if (!CollectionUtils.isEmpty(tokenSettingsMap)) {
            tokenSettingsMap.forEach((k, v) -> {
                if (v != null) {
                    tokenSettingsBuilder.setting(k, v);
                }
            });
        }
        builder.tokenSettings(tokenSettingsBuilder.build());
        if (StringUtils.hasText(registeredClient.getPostLogoutRedirectUris())) {
            builder.postLogoutRedirectUris(logoutRedirectUris -> logoutRedirectUris.addAll(StringUtils.commaDelimitedListToSet(registeredClient.getPostLogoutRedirectUris())));
        }
        client = builder.build();
        // redisTemplate.opsForValue().set(key, client);
        return client;
    }
}
