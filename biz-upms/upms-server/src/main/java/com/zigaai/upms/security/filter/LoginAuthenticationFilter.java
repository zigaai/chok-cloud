package com.zigaai.upms.security.filter;

import com.nimbusds.jose.JOSEException;
import com.zigaai.common.core.infra.strategy.StrategyFactory;
import com.zigaai.common.core.model.dto.PayloadDTO;
import com.zigaai.common.core.model.dto.ResponseData;
import com.zigaai.common.core.model.vo.UPMSToken;
import com.zigaai.common.core.utils.JWTUtil;
import com.zigaai.upms.exception.LoginIllegalArgumentException;
import com.zigaai.upms.model.convertor.SystemUserConvertor;
import com.zigaai.upms.model.enumeration.LoginType;
import com.zigaai.upms.model.security.SystemUser;
import com.zigaai.upms.security.processor.LoginProcessor;
import com.zigaai.upms.security.properties.CustomSecurityProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

@Slf4j
// @Configuration
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final RequestMatcher LOGIN_REQUEST_MATCHER = new AntPathRequestMatcher("/login", "POST");

    private final MappingJackson2HttpMessageConverter jackson2HttpMessageConverter;

    private final StrategyFactory<LoginType, LoginProcessor> loginTypeLoginProcessorStrategy;

    private final CustomSecurityProperties securityProperties;

    public LoginAuthenticationFilter(StrategyFactory<LoginType, LoginProcessor> loginTypeLoginProcessorStrategy,
                                     AuthenticationManager authenticationManager,
                                     CustomSecurityProperties securityProperties,
                                     MappingJackson2HttpMessageConverter jackson2HttpMessageConverter) {
        super(LOGIN_REQUEST_MATCHER);
        super.setAuthenticationManager(authenticationManager);
        this.loginTypeLoginProcessorStrategy = loginTypeLoginProcessorStrategy;
        this.securityProperties = securityProperties;
        this.jackson2HttpMessageConverter = jackson2HttpMessageConverter;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // TODO tenant 租户
        LoginType loginType = obtainLoginType(request);
        Authentication unauthenticated = loginTypeLoginProcessorStrategy.getStrategy(loginType).buildUnauthenticated(request);
        return this.getAuthenticationManager().authenticate(unauthenticated);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // super.successfulAuthentication(request, response, chain, authResult);
        log.info("authResult: {}", authResult);
        log.info("securityProperties: {}", securityProperties);
        SystemUser systemUser = (SystemUser) authResult.getPrincipal();
        PayloadDTO payloadDTO = SystemUserConvertor.INSTANCE.toPayloadDTO(systemUser, securityProperties.getToken().getTimeToLive());
        UPMSToken upmsToken;
        try {
            upmsToken = JWTUtil.generateToken(payloadDTO, systemUser.getSalt());
        } catch (JOSEException e) {
            log.error("生成token错误: ", e);
            jackson2HttpMessageConverter.write(ResponseData.unknownError("生成token错误"), MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
            return;
        }
        jackson2HttpMessageConverter.write(ResponseData.success("登录成功", upmsToken), MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String msg = "未知错误";
        if (failed instanceof LoginIllegalArgumentException
                || failed instanceof BadCredentialsException
                || failed instanceof UsernameNotFoundException) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            msg = failed.getMessage();
        }
        jackson2HttpMessageConverter.write(ResponseData.badRequest(msg), MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
    }

    protected LoginType obtainLoginType(HttpServletRequest request) {
        String loginTypeStr = request.getParameter("loginType");
        if (StringUtils.isBlank(loginTypeStr)) {
            throw new LoginIllegalArgumentException("登录类型为空");
        }
        LoginType loginType;
        try {
            loginType = LoginType.getByVal(Byte.parseByte(loginTypeStr));
        } catch (NumberFormatException e) {
            throw new LoginIllegalArgumentException("非法的登录类型");
        }
        if (loginType == null) {
            throw new LoginIllegalArgumentException("暂不支持类型: " + loginTypeStr);
        }
        return loginType;
    }

}
