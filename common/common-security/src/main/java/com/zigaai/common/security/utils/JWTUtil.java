package com.zigaai.common.security.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.zigaai.common.core.infra.exception.BizIllegalArgumentException;
import com.zigaai.common.core.infra.exception.JwtExpiredException;
import com.zigaai.common.core.infra.exception.JwtInvalidException;
import com.zigaai.common.security.model.dto.PayloadDTO;
import com.zigaai.common.core.model.vo.UPMSToken;
import com.zigaai.common.core.utils.JsonUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@UtilityClass
public final class JWTUtil {

    public static UPMSToken generateToken(PayloadDTO claims, String salt) throws JsonProcessingException, JOSEException {
        Long duration = claims.getDuration();
        if (duration == null) {
            throw BizIllegalArgumentException.of("claims duration cloud not be null");
        }
        // 创建JWS头，设置签名算法和类型
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256).
                type(JOSEObjectType.JWT)
                .build();
        long iat = System.currentTimeMillis();
        long exp = iat + TimeUnit.SECONDS.toMillis(duration);
        claims.setIat(iat);
        claims.setExp(exp);
        // 将负载信息封装到Payload中
        Payload payload = new Payload(JsonUtil.toJson(claims));
        // 创建JWS对象
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        // 创建HMAC签名器
        JWSSigner jwsSigner = new MACSigner(generateSecret(claims.getId(), salt));
        // 签名
        jwsObject.sign(jwsSigner);
        String tokenVal = jwsObject.serialize();
        return new UPMSToken(tokenVal, iat, exp, duration);
    }

    public static Pair<JWSObject, PayloadDTO> parseUnverified(String token) throws ParseException, JsonProcessingException {
        // 从token中解析JWS对象
        JWSObject jwsObject = JWSObject.parse(token);
        // 创建HMAC验证器
        String payloadStr = jwsObject.getPayload().toString();
        return Pair.of(jwsObject, JsonUtil.readValue(payloadStr, PayloadDTO.class));
    }

    public static PayloadDTO parseVerified(String token, String salt) throws ParseException, JsonProcessingException, JOSEException {
        Pair<JWSObject, PayloadDTO> pair = parseUnverified(token);
        JWSObject jwsObject = pair.getLeft();
        PayloadDTO payload = pair.getRight();
        check(jwsObject, payload, salt);
        return payload;
    }

    public static void check(JWSObject jwsObject, PayloadDTO payload, String salt) throws JOSEException {
        JWSVerifier jwsVerifier = new MACVerifier(generateSecret(payload.getId(), salt));
        if (!jwsObject.verify(jwsVerifier)) {
            throw new JwtInvalidException("token签名不合法！");
        }
        if (payload.getExp() < new Date().getTime()) {
            throw new JwtExpiredException("token已过期");
        }
    }

    private static String generateSecret(Long userId, String salt) {
        return userId + salt;
    }

}
