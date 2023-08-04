package com.zigaai.common.security.validator;

import com.zigaai.common.core.infra.strategy.StrategyFactory;
import com.zigaai.common.core.model.enumeration.SysUserType;
import com.zigaai.common.security.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.Assert;

import java.util.Objects;

@Slf4j
public class JwtSaltValidator implements OAuth2TokenValidator<Jwt> {

    private final StrategyFactory<SysUserType, AuthenticationService> authenticationServiceStrategy;

    public JwtSaltValidator(StrategyFactory<SysUserType, AuthenticationService> authenticationServiceStrategy) {
        Assert.notNull(authenticationServiceStrategy, "authenticationServiceStrategy cannot be null");
        this.authenticationServiceStrategy = authenticationServiceStrategy;
    }

    @Override
    public OAuth2TokenValidatorResult validate(Jwt token) {
        String kid = token.getClaimAsString("kid");
        String username = token.getClaimAsString("sub");
        SysUserType userType = SysUserType.getByVal(Byte.valueOf(token.getClaimAsString("userType")));
        String salt;
        try {
            salt = authenticationServiceStrategy.getStrategy(userType).getSaltByUsername(username);
        } catch (Exception e) {
            log.error("获取salt失败", e);
            return OAuth2TokenValidatorResult.failure(new OAuth2Error(OAuth2ErrorCodes.INVALID_TOKEN, e.getLocalizedMessage(), StringUtils.EMPTY));
        }
        if (Objects.equals(kid, DigestUtils.md5Hex(username + salt))) {
            return OAuth2TokenValidatorResult.success();
        }
        return OAuth2TokenValidatorResult.failure(new OAuth2Error(OAuth2ErrorCodes.INVALID_TOKEN, "invalid salt", StringUtils.EMPTY));
    }
}
