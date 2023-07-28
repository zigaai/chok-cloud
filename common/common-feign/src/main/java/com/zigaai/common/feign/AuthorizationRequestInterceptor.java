package com.zigaai.common.feign;

import com.zigaai.common.core.model.constants.SecurityConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@RequiredArgsConstructor
public class AuthorizationRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (StringUtils.hasText(token)) {
                template.header(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));
            }
            String preToken = request.getHeader(SecurityConstant.PRE_AUTHORIZATION_HEADER);
            if (StringUtils.hasText(preToken)) {
                template.header(SecurityConstant.PRE_AUTHORIZATION_HEADER, preToken);
            }
        }
    }

}
