package com.zigaai.authentication.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zigaai.authentication.model.convertor.AdminConvertor;
import com.zigaai.authentication.model.convertor.UserConvertor;
import com.zigaai.authentication.model.entity.Admin;
import com.zigaai.authentication.model.entity.PagePermission;
import com.zigaai.authentication.model.entity.Role;
import com.zigaai.authentication.model.entity.User;
import com.zigaai.common.core.model.enumeration.TbStateEnum;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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
    private final Byte userType;

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

    public static SystemUser of(Admin admin, List<Role> roleList, List<PagePermission> permissionList) {
        return AdminConvertor.INSTANCE.toSystemUser(admin, roleList, permissionList);
    }

    public static SystemUser of(User user, List<Role> roleList, List<PagePermission> permissionList) {
        return UserConvertor.INSTANCE.toSystemUser(user, roleList, permissionList);
    }

    /**
     * 用户数据脱敏
     */
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
