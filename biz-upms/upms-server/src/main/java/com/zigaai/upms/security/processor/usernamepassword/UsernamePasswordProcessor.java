// package com.zigaai.upms.security.processor.usernamepassword;
//
// import com.zigaai.upms.exception.LoginIllegalArgumentException;
// import com.zigaai.upms.model.enumeration.LoginType;
// import com.zigaai.common.core.model.enumeration.SysUserType;
// import com.zigaai.upms.security.processor.LoginProcessor;
// import jakarta.servlet.http.HttpServletRequest;
// import org.apache.commons.lang3.StringUtils;
// import org.springframework.security.core.Authentication;
// import org.springframework.stereotype.Component;
//
// @Component
// public class UsernamePasswordProcessor implements LoginProcessor {
//
//     @Override
//     public LoginType getKey() {
//         return LoginType.USERNAME_PASSWORD;
//     }
//
//
//     @Override
//     public Authentication buildUnauthenticated(HttpServletRequest request) {
//         String username = request.getParameter("username");
//         String userTypeStr = request.getParameter("userType");
//         if (StringUtils.isBlank(userTypeStr)) {
//             throw new LoginIllegalArgumentException("用户类型不可为空");
//         }
//         SysUserType userType;
//         try {
//             userType = SysUserType.getByVal(Byte.parseByte(userTypeStr));
//         } catch (NumberFormatException e) {
//             throw new LoginIllegalArgumentException("非法的用户类型");
//         }
//         if (StringUtils.isBlank(username)) {
//             throw new LoginIllegalArgumentException("请输入用户名");
//         }
//         String password = request.getParameter("password");
//         if (StringUtils.isBlank(password)) {
//             throw new LoginIllegalArgumentException("请输入密码");
//         }
//         return SysUsernamePasswordToken.unauthenticated(username, password, userType);
//     }
// }
