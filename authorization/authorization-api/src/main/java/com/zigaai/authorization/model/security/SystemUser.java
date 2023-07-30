package com.zigaai.authorization.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zigaai.authorization.model.convertor.AdminConvertor;
import com.zigaai.authorization.model.convertor.UserConvertor;
import com.zigaai.authorization.model.entity.Admin;
import com.zigaai.authorization.model.entity.PagePermission;
import com.zigaai.authorization.model.entity.Role;
import com.zigaai.authorization.model.entity.User;
import com.zigaai.common.core.model.enumeration.SysUserType;
import com.zigaai.common.core.model.enumeration.TbStateEnum;
import lombok.Getter;
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
public class SystemUser implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * id
     */
    private final Long id;

    /**
     * 用户名
     */
    private final String username;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * token盐值
     */
    @JsonIgnore
    private String salt;

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

    public SystemUser(Long id, String username, String password, String salt, Byte state, SysUserType userType, List<Role> roleList, List<PagePermission> permissionList, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.state = state;
        this.userType = userType;
        this.roleList = roleList;
        this.permissionList = permissionList;
        this.authorities = authorities;
    }

    public static SystemUser of(Admin admin, SysUserType userType, List<Role> roleList, List<PagePermission> permissionList) {
        return AdminConvertor.INSTANCE.toSystemUser(admin, userType, roleList, permissionList);
    }

    public static SystemUser of(User user, SysUserType userType, List<Role> roleList, List<PagePermission> permissionList) {
        return UserConvertor.INSTANCE.toSystemUser(user, userType, roleList, permissionList);
    }

    public SystemUser desensitization() {
        this.password = null;
        this.salt = null;
        return this;
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
