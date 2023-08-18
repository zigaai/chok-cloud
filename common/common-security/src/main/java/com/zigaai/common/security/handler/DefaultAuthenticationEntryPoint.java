package com.zigaai.common.security.handler;

import com.zigaai.common.core.model.dto.ResponseData;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 当未登录或者token失效访问接口时，自定义的返回结果
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.warn("Authentication error: ", e);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String msg = "用户未登录或登录已过期, 请重新登录";
        if (e instanceof InvalidBearerTokenException) {
            msg = e.getMessage();
        }
        jackson2HttpMessageConverter.write(ResponseData.unauthorized(msg), MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
    }
}
