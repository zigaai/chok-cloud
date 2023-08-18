package com.zigaai.authentication.controller;

import com.zigaai.authentication.model.security.SystemUser;
import com.zigaai.authentication.security.userdetails.service.MultiAuthenticationUserDetailsService;
import com.zigaai.common.core.infra.strategy.StrategyFactory;
import com.zigaai.common.core.model.dto.ResponseData;
import com.zigaai.common.core.model.enumeration.ResponseDataStatus;
import com.zigaai.common.core.model.enumeration.SysUserType;
import com.zigaai.common.security.model.dto.PayloadDTO;
import com.zigaai.common.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限相关接口
 */
@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final StrategyFactory<SysUserType, MultiAuthenticationUserDetailsService> userDetailsServiceStrategy;

    private final StrategyFactory<SysUserType, AuthenticationService> authenticationServiceStrategy;

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public ResponseData<SystemUser> getInfo() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PayloadDTO payloadDTO = PayloadDTO.fromAccessTokenClaims(jwt.getClaims());
        String username = payloadDTO.getUsername();
        SysUserType userType = SysUserType.getByVal(payloadDTO.getUserType());
        SystemUser systemUser = (SystemUser) userDetailsServiceStrategy.getStrategy(userType).loadUserByUsername(username);
        return ResponseData.success(systemUser);
    }

    /**
     * 获取盐值
     *
     * @param username 用户名
     * @param userType 用户类型
     */
    @GetMapping("/salt")
    public ResponseData<String> getSalt(@RequestParam String username, @RequestParam Byte userType) {
        String salt = authenticationServiceStrategy.getStrategy(SysUserType.getByVal(userType)).getSaltByUsername(username);
        return ResponseData.success(ResponseDataStatus.SUCCESS.getMsg(), salt);
    }

}
