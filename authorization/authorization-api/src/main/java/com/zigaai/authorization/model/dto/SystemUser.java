package com.zigaai.authorization.model.dto;

import com.zigaai.common.core.model.enumeration.TbStateEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Getter
@ToString
@RequiredArgsConstructor
public class SystemUser implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Long id;

    private final String username;

    /**
     * 密码
     */
    private final String password;

    /**
     * token盐值
     */
    private final String salt;

    /**
     * 状态: 0: 正常, 1: 删除
     */
    private final Byte state;

    /**
     * 权限
     */
    private final Collection<? extends GrantedAuthority> authorities;


    // public static SystemUser of(User user) {
    //     return new SystemUser(user.getId(), user.getUsername(), user.getPassword(), user.getSalt(), user.getState(), authorities);
    // }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return TbStateEnum.toBool(this.state);
    }
}
