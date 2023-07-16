package com.zigaai.upms.security.userdetails.service.impl;

import com.zigaai.upms.mapper.PagePermissionMapper;
import com.zigaai.upms.mapper.RoleMapper;
import com.zigaai.upms.mapper.UserMapper;
import com.zigaai.upms.model.entity.PagePermission;
import com.zigaai.upms.model.entity.Role;
import com.zigaai.upms.model.entity.User;
import com.zigaai.upms.model.enumeration.SysUserType;
import com.zigaai.upms.model.security.SystemUser;
import com.zigaai.upms.security.userdetails.service.MultiAuthenticationUserDetailsService;
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
            throw new UsernameNotFoundException("系统用户不存在");
        }
        List<Role> roleList = roleMapper.listBySysUserId(user.getId(), getKey());
        List<PagePermission> permissionList = Collections.emptyList();
        if (!CollectionUtils.isEmpty(roleList)) {
            List<Long> roleIdList = roleList.stream().map(Role::getId).toList();
            permissionList = pagePermissionMapper.listByRoleIds(roleIdList);
        }
        return SystemUser.of(user, getKey(), roleList, permissionList);
    }

}
