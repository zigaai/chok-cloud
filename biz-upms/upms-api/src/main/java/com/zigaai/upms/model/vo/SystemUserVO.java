// package com.zigaai.upms.model.vo;
//
// import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
// import com.zigaai.common.core.model.enumeration.SysUserType;
// import com.zigaai.upms.model.entity.PagePermission;
// import com.zigaai.upms.model.entity.Role;
// import com.zigaai.upms.serializer.SimpleGrantedAuthorityDeserializer;
// import lombok.Getter;
// import lombok.Setter;
// import lombok.ToString;
// import org.springframework.security.core.GrantedAuthority;
//
// import java.io.Serial;
// import java.io.Serializable;
// import java.util.Collection;
// import java.util.List;
//
// @Getter
// @Setter
// @ToString
// public class SystemUserVO implements Serializable {
//
//     @Serial
//     private static final long serialVersionUID = 1L;
//
//     /**
//      * id
//      */
//     private Long id;
//
//     /**
//      * 用户名
//      */
//     private String username;
//
//     /**
//      * 状态: 0: 正常, 1: 删除
//      */
//     private Byte state;
//
//     /**
//      * 用户类型
//      */
//     private SysUserType userType;
//
//     /**
//      * 角色列表
//      */
//     private List<Role> roleList;
//
//     /**
//      * 页面权限
//      */
//     private List<PagePermission> permissionList;
//
//     /**
//      * 权限
//      */
//     @JsonDeserialize(using = SimpleGrantedAuthorityDeserializer.class)
//     private Collection<? extends GrantedAuthority> authorities;
// }
