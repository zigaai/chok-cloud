package com.zigaai.authorization.security;

import com.zigaai.authorization.model.exception.RemoteServiceAuthenticationException;
import com.zigaai.common.core.model.dto.ResponseData;
import com.zigaai.upms.feign.SystemUserRemoteService;
import com.zigaai.upms.model.vo.SystemUserVO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final SystemUserRemoteService systemUserRemoteService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        ResponseData<SystemUserVO> res = systemUserRemoteService.getInfo();
        if (ResponseData.isEmpty(res)) {
            chain.doFilter(request, response);
            String errMsg = "调用upms服务失败";
            if (res != null) {
                errMsg = res.getMessage();
            }
            throw new RemoteServiceAuthenticationException(errMsg);
        }
        SystemUserVO systemUser = res.getData();
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(systemUser, null, systemUser.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

}
