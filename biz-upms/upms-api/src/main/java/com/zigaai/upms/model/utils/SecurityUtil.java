package com.zigaai.upms.model.utils;

import com.zigaai.upms.model.entity.PagePermission;
import com.zigaai.upms.model.entity.Role;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@UtilityClass
public final class SecurityUtil {

    public static Set<SimpleGrantedAuthority> toAuthorities(List<Role> roleList, List<PagePermission> permissionList) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        if (!CollectionUtils.isEmpty(roleList)) {
            for (Role role : roleList) {
                authorities.add(new SimpleGrantedAuthority(role.getRoleCode()));
            }
        }
        if (!CollectionUtils.isEmpty(permissionList)) {
            for (PagePermission permission : permissionList) {
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }
        return authorities;
    }

}
