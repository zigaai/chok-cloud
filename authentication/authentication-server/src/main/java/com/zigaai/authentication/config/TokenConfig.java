package com.zigaai.authentication.config;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.zigaai.authentication.config.keygen.UUIDOAuth2RefreshTokenGenerator;
import com.zigaai.authentication.model.security.SystemUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.*;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TokenConfig {

    @Bean
    public OAuth2TokenGenerator<OAuth2Token> tokenGenerator(JwtEncoder jwtEncoder,
                                                            OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer) {
        JwtGenerator jwtGenerator = new JwtGenerator(jwtEncoder);
        jwtGenerator.setJwtCustomizer(jwtCustomizer);
        UUIDOAuth2RefreshTokenGenerator uuidoAuth2RefreshTokenGenerator = new UUIDOAuth2RefreshTokenGenerator();
        return new DelegatingOAuth2TokenGenerator(jwtGenerator, uuidoAuth2RefreshTokenGenerator);
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return context -> {
            JwtClaimsSet.Builder claims = context.getClaims();
            SystemUser systemUser = (SystemUser) context.getPrincipal().getPrincipal();
            if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)
                    || context.getTokenType().getValue().equals(OidcParameterNames.ID_TOKEN)) {
                claims.claim("id", systemUser.getId());
                claims.claim("userType", systemUser.getUserType());
                String kid = encryptSalt(systemUser.getUsername(), systemUser.getSalt());
                claims.claim("kid", kid);
                claims.claim("sid", UUID.randomUUID());
                claims.claim(IdTokenClaimNames.AUTH_TIME, new Date());
            }
        };
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    private String encryptSalt(String username, String salt) {
        return DigestUtils.md5Hex(username + salt);
    }

}
