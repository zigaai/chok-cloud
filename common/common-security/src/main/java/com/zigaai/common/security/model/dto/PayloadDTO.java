package com.zigaai.common.security.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class PayloadDTO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 客户端ID
     */
    private List<String> clientId;

    /**
     * 客户端作用域
     */
    private List<String> scopes;

    /**
     * 用户类型
     */
    private Byte userType;

    /**
     * 过期时间
     */
    private Long exp;

    /**
     * 签发时间
     */
    private Long iat;

    /**
     * token持续时间
     */
    @JsonIgnore
    private Long duration;

    @SuppressWarnings("unchecked")
    public static PayloadDTO fromAccessTokenClaims(Map<String, Object> claims) {
        Assert.notEmpty(claims, "claims can not be empty");
        PayloadDTO payload = new PayloadDTO();
        payload.setUsername(claims.get("sub").toString());
        payload.setId(Long.parseLong(claims.get("id").toString()));
        payload.setUserType(Byte.parseByte(claims.get("userType").toString()));
        payload.setExp(Instant.parse(claims.get("exp").toString()).getEpochSecond());
        payload.setIat(Instant.parse(claims.get("iat").toString()).getEpochSecond());
        payload.setClientId((ArrayList<String>) claims.get("aud"));
        payload.setScopes((ArrayList<String>) claims.get("scope"));
        return payload;
    }
}
