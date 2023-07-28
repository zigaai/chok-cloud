package com.zigaai.upms.security.filter;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.zigaai.common.core.infra.strategy.StrategyFactory;
import com.zigaai.common.core.model.constants.SecurityConstant;
import com.zigaai.common.core.model.dto.PayloadDTO;
import com.zigaai.common.core.model.enumeration.SysUserType;
import com.zigaai.upms.model.properties.CustomSecurityProperties;
import com.zigaai.common.core.utils.JWTUtil;
import com.zigaai.upms.model.security.SystemUser;
import com.zigaai.upms.security.userdetails.service.MultiAuthenticationUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final CustomSecurityProperties securityProperties;

    // private final MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;

    private final StrategyFactory<SysUserType, MultiAuthenticationUserDetailsService> userDetailsServiceStrategy;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            token = request.getHeader(SecurityConstant.PRE_AUTHORIZATION_HEADER);
        }
        String prefix = securityProperties.getToken().getPrefix();
        if (StringUtils.isBlank(token) || !token.startsWith(prefix)) {
            chain.doFilter(request, response);
            return;
        }
        token = token.substring(prefix.length());
        SystemUser systemUser;
        try {
            Pair<JWSObject, PayloadDTO> pair = JWTUtil.parseUnverified(token);
            PayloadDTO payload = pair.getSecond();
            SysUserType userType = SysUserType.getByVal(payload.getUserType());
            systemUser = (SystemUser) userDetailsServiceStrategy.getStrategy(userType).loadUserByUsername(payload.getUsername());
            JWTUtil.check(pair.getFirst(), payload, systemUser.getSalt());
        } catch (ParseException | JOSEException e) {
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
