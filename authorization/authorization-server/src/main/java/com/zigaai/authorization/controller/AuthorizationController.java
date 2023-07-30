package com.zigaai.authorization.controller;

import com.zigaai.common.core.model.dto.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限相关接口
 */
@Slf4j
@RestController
@RequestMapping("/authorization")
@RequiredArgsConstructor
public class AuthorizationController {

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public ResponseData<JwtAuthenticationToken> getInfo() {
        // JwtAuthenticationToken
        // SystemUserVO info = SystemUserConvertor.INSTANCE.toVO(SecurityUtil.currentUser());
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        log.info("username: {}", jwtAuthenticationToken.getName());
        log.info("client consents: {}", jwtAuthenticationToken.getAuthorities());
        Jwt jwt = (Jwt) jwtAuthenticationToken.getPrincipal();
        // jwt.getTokenValue()
        log.info("token信息: {}", jwt.getClaims());
        return ResponseData.success(jwtAuthenticationToken);
    }

}
