package com.zigaai.authorization.model.vo;

import com.zigaai.authorization.model.entity.PagePermission;
import com.zigaai.authorization.model.entity.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 管理员表 VO
 * </p>
 *
 * @author zigaai
 * @since 2023-07-15
 */
@Getter
@Setter
@ToString
public class AdminVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 状态: 	0: 正常 	1: 删除
     */
    private Byte state;

    /**
     * 角色列表
     */
    private List<Role> roleList;

    /**
     * 页面权限
     */
    private List<PagePermission> pagePermissionList;

}
