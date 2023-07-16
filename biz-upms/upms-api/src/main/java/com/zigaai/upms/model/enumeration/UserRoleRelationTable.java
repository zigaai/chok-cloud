package com.zigaai.upms.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRoleRelationTable {

    /**
     * 管理员角色关联表
     */
    rel_admin_role("admin_id"),

    /**
     * 用户角色关联表
     */
    rel_user_role("user_id");

    private final String userIdField;

}
