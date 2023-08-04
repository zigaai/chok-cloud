package com.zigaai.authentication.security.filter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.zigaai.authentication.security.userdetails.service.MultiAuthenticationUserDetailsService;
import com.zigaai.authentication.model.properties.CustomSecurityProperties;
import com.zigaai.authentication.model.security.SystemUser;
import com.zigaai.common.core.infra.exception.JwtExpiredException;
import com.zigaai.common.core.infra.strategy.StrategyFactory;
import com.zigaai.common.core.model.constants.SecurityConstant;
import com.zigaai.common.security.model.dto.PayloadDTO;
import com.zigaai.common.core.model.enumeration.SysUserType;
import com.zigaai.common.security.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final CustomSecurityProperties securityProperties;

    private final StrategyFactory<SysUserType, MultiAuthenticationUserDetailsService> userDetailsServiceStrategy;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(SecurityConstant.PRE_AUTHORIZATION_HEADER);
        String prefix = securityProperties.getToken().getPrefix();
        if (StringUtils.isBlank(token) || !token.startsWith(prefix)) {
            chain.doFilter(request, response);
            return;
        }
        token = token.substring(prefix.length());
        SystemUser systemUser;
        try {
            Pair<JWSObject, PayloadDTO> pair = JWTUtil.parseUnverified(token);
            PayloadDTO payload = pair.getRight();
            SysUserType userType = SysUserType.getByVal(payload.getUserType());
            systemUser = (SystemUser) userDetailsServiceStrategy.getStrategy(userType).loadUserByUsername(payload.getUsername());
            JWTUtil.check(pair.getLeft(), payload, systemUser.getSalt());
        } catch (ParseException | JOSEException | JwtExpiredException e) {
            log.warn("解析token失败: ", e);
            chain.doFilter(request, response);
            return;
        }
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(systemUser, null, systemUser.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

}
