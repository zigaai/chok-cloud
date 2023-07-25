package com.zigaai.upms.model.security;

import com.zigaai.common.core.model.enumeration.SysUserType;
import com.zigaai.common.core.model.enumeration.TbStateEnum;
import com.zigaai.upms.model.convertor.AdminConvertor;
import com.zigaai.upms.model.convertor.UserConvertor;
import com.zigaai.upms.model.entity.Admin;
import com.zigaai.upms.model.entity.PagePermission;
import com.zigaai.upms.model.entity.Role;
import com.zigaai.upms.model.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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
     * 用户类型
     */
    private final SysUserType userType;

    /**
     * 角色列表
     */
    private final List<Role> roleList;

    /**
     * 页面权限
     */
    private final List<PagePermission> permissionList;

    /**
     * 权限
     */
    private final Collection<? extends GrantedAuthority> authorities;

    public static SystemUser of(Admin admin, SysUserType userType, List<Role> roleList, List<PagePermission> permissionList) {
        return AdminConvertor.INSTANCE.toSystemUser(admin, userType, roleList, permissionList);
    }

    public static SystemUser of(User user, SysUserType userType, List<Role> roleList, List<PagePermission> permissionList) {
        return UserConvertor.INSTANCE.toSystemUser(user, userType, roleList, permissionList);
    }

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
