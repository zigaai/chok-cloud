package com.zigaai.upms.utils;

import com.zigaai.upms.model.entity.PagePermission;
import com.zigaai.upms.model.entity.Role;
import com.zigaai.upms.model.security.SystemUser;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SecurityUtil {

    private SecurityUtil() {
    }

    public static SystemUser currentUser() {
        return currentUser(true);
    }

    public static SystemUser currentUser(boolean desensitization) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthenticationCredentialsNotFoundException("用户未登录, 请重新登录");
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof SystemUser currentUser)) {
            throw new AuthenticationCredentialsNotFoundException("用户未登录或登录已过期, 请重新登录");
        }
        if (desensitization) {
            return currentUser.desensitization();
        }
        return currentUser;
    }

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
