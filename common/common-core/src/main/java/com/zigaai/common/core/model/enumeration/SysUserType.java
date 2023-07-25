package com.zigaai.common.core.model.enumeration;

import com.zigaai.common.core.infra.exception.BizIllegalArgumentException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 系统用户类型
 */
@Getter
@RequiredArgsConstructor
public enum SysUserType {

    /**
     * 管理员
     */
    ADMIN((byte) 1, UserRoleRelationTable.rel_admin_role),

    /**
     * 普通用户
     */
    USER((byte) 2, UserRoleRelationTable.rel_user_role);

    private final byte val;

    private final UserRoleRelationTable userRoleRelationTable;

    private static final Map<Byte, SysUserType> USER_TYPE_MAP = Collections.unmodifiableMap(Arrays.stream(values()).collect(Collectors.toMap(SysUserType::getVal, Function.identity())));

    public static SysUserType getByVal(Byte val) {
        SysUserType userType = USER_TYPE_MAP.get(val);
        if (userType == null) {
            throw BizIllegalArgumentException.of("用户类型: " + val + " 不存在, 请检查");
        }
        return userType;
    }

}
