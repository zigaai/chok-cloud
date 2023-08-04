package com.zigaai.authentication.security.userdetails.service.impl;

import com.zigaai.authentication.mapper.AdminMapper;
import com.zigaai.authentication.mapper.PagePermissionMapper;
import com.zigaai.authentication.mapper.RoleMapper;
import com.zigaai.authentication.model.entity.Admin;
import com.zigaai.authentication.model.entity.PagePermission;
import com.zigaai.authentication.model.entity.Role;
import com.zigaai.authentication.model.security.SystemUser;
import com.zigaai.authentication.security.userdetails.service.MultiAuthenticationUserDetailsService;
import com.zigaai.common.core.model.enumeration.SysUserType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminUserDetailsService implements MultiAuthenticationUserDetailsService {

    private final AdminMapper adminMapper;

    private final RoleMapper roleMapper;

    private final PagePermissionMapper pagePermissionMapper;

    @Override
    public SysUserType getKey() {
        return SysUserType.ADMIN;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminMapper.getByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("系统管理员不存在");
        }
        List<Role> roleList = roleMapper.listBySysUserId(admin.getId(), getKey());
        List<PagePermission> pagePermissionList = Collections.emptyList();
        if (!CollectionUtils.isEmpty(roleList)) {
            List<Long> roleIdList = roleList.stream().map(Role::getId).toList();
            pagePermissionList = pagePermissionMapper.listByRoleIds(roleIdList);
        }
        return SystemUser.of(admin, roleList, pagePermissionList);
    }

}
