package com.zigaai.authentication.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zigaai.common.core.utils.JsonUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.ConfigurationSettingNames;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

/**
 * <p>
 * 认证客户端
 * </p>
 *
 * @author zigaai
 * @since 2023-07-02
 */
@Slf4j
@Getter
@Setter
@ToString
@TableName("oauth2_registered_client")
public class Oauth2RegisteredClient implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("client_id")
    private String clientId;

    @TableField("client_id_issued_at")
    private LocalDateTime clientIdIssuedAt;

    @TableField("client_secret")
    private String clientSecret;

    @TableField("client_secret_expires_at")
    private LocalDateTime clientSecretExpiresAt;

    @TableField("client_name")
    private String clientName;

    @TableField("client_authentication_methods")
    private String clientAuthenticationMethods;

    @TableField("authorization_grant_types")
    private String authorizationGrantTypes;

    @TableField("redirect_uris")
    private String redirectUris;

    @TableField("scopes")
    private String scopes;

    @TableField("client_settings")
    private String clientSettings;

    @TableField("token_settings")
    private String tokenSettings;

    @TableField("post_logout_redirect_uris")
    private String postLogoutRedirectUris;

    @SuppressWarnings("unchecked")
    public Map<String, Object> parseTokenSettings() {
        Map<String, Object> map = Collections.emptyMap();
        if (StringUtils.isNotBlank(this.tokenSettings)) {
            try {
                map = JsonUtil.readValue(this.tokenSettings, Map.class);
            } catch (JsonProcessingException e) {
                log.error("解析客户端tokenSettings字段错误, 将使用默认配置; err: {}", ExceptionUtils.getStackTrace(e));
            }
            Object authorizationCodeTimeToLiveObj = map.get(ConfigurationSettingNames.Token.AUTHORIZATION_CODE_TIME_TO_LIVE);
            if (authorizationCodeTimeToLiveObj != null) {
                map.put(ConfigurationSettingNames.Token.AUTHORIZATION_CODE_TIME_TO_LIVE, Duration.ofSeconds(Long.parseLong(authorizationCodeTimeToLiveObj.toString())));
            }
            Object accessTokenTimeToLiveObj = map.get(ConfigurationSettingNames.Token.ACCESS_TOKEN_TIME_TO_LIVE);
            if (accessTokenTimeToLiveObj != null) {
                map.put(ConfigurationSettingNames.Token.ACCESS_TOKEN_TIME_TO_LIVE, Duration.ofSeconds(Long.parseLong(accessTokenTimeToLiveObj.toString())));
            }
            Object deviceCodeTimeToLiveObj = map.get(ConfigurationSettingNames.Token.DEVICE_CODE_TIME_TO_LIVE);
            if (deviceCodeTimeToLiveObj != null) {
                map.put(ConfigurationSettingNames.Token.DEVICE_CODE_TIME_TO_LIVE, Duration.ofSeconds(Long.parseLong(deviceCodeTimeToLiveObj.toString())));
            }
            Object refreshTokenTimeToLiveObj = map.get(ConfigurationSettingNames.Token.REFRESH_TOKEN_TIME_TO_LIVE);
            if (refreshTokenTimeToLiveObj != null) {
                map.put(ConfigurationSettingNames.Token.REFRESH_TOKEN_TIME_TO_LIVE, Duration.ofSeconds(Long.parseLong(refreshTokenTimeToLiveObj.toString())));
            }
            Object idTokenSignatureAlgorithmObj = map.get(ConfigurationSettingNames.Token.ID_TOKEN_SIGNATURE_ALGORITHM);
            if (idTokenSignatureAlgorithmObj != null) {
                map.put(ConfigurationSettingNames.Token.ID_TOKEN_SIGNATURE_ALGORITHM, SignatureAlgorithm.from(idTokenSignatureAlgorithmObj.toString()));
            }
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> parseClientSettings() {
        Map<String, Object> map = Collections.emptyMap();
        if (StringUtils.isNotBlank(this.clientSettings)) {
            try {
                map = JsonUtil.readValue(this.clientSettings, Map.class);
            } catch (JsonProcessingException e) {
                log.error("解析客户端clientSettings字段错误, 将使用默认配置; err: {}", ExceptionUtils.getStackTrace(e));
            }
            Object requireProofKeyObj = map.get(ConfigurationSettingNames.Client.REQUIRE_PROOF_KEY);
            if (requireProofKeyObj != null) {
                map.put(ConfigurationSettingNames.Client.REQUIRE_PROOF_KEY, Boolean.parseBoolean(requireProofKeyObj.toString()));
            }
            Object requireAuthorizationConsentObj = map.get(ConfigurationSettingNames.Client.REQUIRE_AUTHORIZATION_CONSENT);
            if (requireAuthorizationConsentObj != null) {
                map.put(ConfigurationSettingNames.Client.REQUIRE_AUTHORIZATION_CONSENT, Boolean.parseBoolean(requireAuthorizationConsentObj.toString()));
            }
            Object jwkSetUrlObj = map.get(ConfigurationSettingNames.Client.JWK_SET_URL);
            if (jwkSetUrlObj != null) {
                map.put(ConfigurationSettingNames.Client.JWK_SET_URL, jwkSetUrlObj.toString());
            }
            Object tokenEndpointAuthenticationSigningAlgorithmObj = map.get(ConfigurationSettingNames.Client.TOKEN_ENDPOINT_AUTHENTICATION_SIGNING_ALGORITHM);
            if (tokenEndpointAuthenticationSigningAlgorithmObj != null) {
                map.put(ConfigurationSettingNames.Client.TOKEN_ENDPOINT_AUTHENTICATION_SIGNING_ALGORITHM, SignatureAlgorithm.from(tokenEndpointAuthenticationSigningAlgorithmObj.toString()));
            }
            Object accessTokenFormatObj = map.get(ConfigurationSettingNames.Token.ACCESS_TOKEN_FORMAT);
            if (accessTokenFormatObj != null) {
                map.put(ConfigurationSettingNames.Token.ACCESS_TOKEN_FORMAT, new OAuth2TokenFormat(accessTokenFormatObj.toString()));
            }
        }
        return map;
    }

}
