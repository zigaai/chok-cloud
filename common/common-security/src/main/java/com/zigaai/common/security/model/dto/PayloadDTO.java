package com.zigaai.common.security.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zigaai.common.core.model.constants.SecurityConstant;
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
        payload.setUsername(claims.get(SecurityConstant.TokenKey.SUB).toString());
        payload.setId(Long.parseLong(claims.get(SecurityConstant.TokenKey.ID).toString()));
        payload.setUserType(Byte.parseByte(claims.get(SecurityConstant.TokenKey.USER_TYPE).toString()));
        payload.setExp(Instant.parse(claims.get(SecurityConstant.TokenKey.EXP).toString()).getEpochSecond());
        payload.setIat(Instant.parse(claims.get(SecurityConstant.TokenKey.IAT).toString()).getEpochSecond());
        payload.setClientId((ArrayList<String>) claims.get(SecurityConstant.TokenKey.AUD));
        payload.setScopes((ArrayList<String>) claims.get(SecurityConstant.TokenKey.SCOPE));
        return payload;
    }
}
