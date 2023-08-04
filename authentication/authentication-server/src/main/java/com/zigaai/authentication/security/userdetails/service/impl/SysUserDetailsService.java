package com.zigaai.authentication.security.userdetails.service.impl;

import com.zigaai.authentication.mapper.PagePermissionMapper;
import com.zigaai.authentication.mapper.RoleMapper;
import com.zigaai.authentication.mapper.UserMapper;
import com.zigaai.authentication.model.entity.PagePermission;
import com.zigaai.authentication.model.entity.Role;
import com.zigaai.authentication.model.entity.User;
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
public class SysUserDetailsService implements MultiAuthenticationUserDetailsService {

    private final UserMapper userMapper;

    private final RoleMapper roleMapper;

    private final PagePermissionMapper pagePermissionMapper;

    @Override
    public SysUserType getKey() {
        return SysUserType.USER;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<Role> roleList = roleMapper.listBySysUserId(user.getId(), getKey());
        List<PagePermission> pagePermissionList = Collections.emptyList();
        if (!CollectionUtils.isEmpty(roleList)) {
            List<Long> roleIdList = roleList.stream().map(Role::getId).toList();
            pagePermissionList = pagePermissionMapper.listByRoleIds(roleIdList);
        }
        return SystemUser.of(user, roleList, pagePermissionList);
    }
}
