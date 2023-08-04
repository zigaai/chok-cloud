package com.zigaai.authentication.security.processor.usernamepassword;

import com.zigaai.common.core.model.enumeration.SysUserType;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SysUsernamePasswordToken extends UsernamePasswordAuthenticationToken {

    @Getter
    private SysUserType userType;

    private SysUsernamePasswordToken(Object username, Object password, SysUserType userType) {
        super(username, password);
        this.userType = userType;
    }

    private SysUsernamePasswordToken(Object username, Object password, SysUserType userType,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userType = userType;
    }

    public static SysUsernamePasswordToken unauthenticated(Object username, Object password, SysUserType userType) {
        return new SysUsernamePasswordToken(username, password, userType);
    }

    public static SysUsernamePasswordToken authenticated(Object username, Object password, SysUserType userType, Collection<? extends GrantedAuthority> authorities) {
        return new SysUsernamePasswordToken(username, password, userType, authorities);
    }

}
