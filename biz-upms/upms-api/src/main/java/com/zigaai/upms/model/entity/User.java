// package com.zigaai.upms.model.entity;
//
// import java.io.Serial;
// import java.io.Serializable;
// import java.time.LocalDateTime;
// import com.baomidou.mybatisplus.annotation.*;
// import lombok.Getter;
// import lombok.Setter;
// import lombok.ToString;
//
// /**
//  * <p>
//  * 用户表
//  * </p>
//  *
//  * @author zigaai
//  * @since 2023-07-15
//  */
// @Getter
// @Setter
// @ToString
// @TableName("user")
// public class User implements Serializable {
//
//     @Serial
//     private static final long serialVersionUID = 1L;
//
//     @TableId(value = "id", type = IdType.AUTO)
//     private Long id;
//
//     /**
//      * 用户名
//      */
//     @TableField("username")
//     private String username;
//
//     /**
//      * 密码
//      */
//     @TableField("`password`")
//     private String password;
//
//     /**
//      * 盐值
//      */
//     @TableField("salt")
//     private String salt;
//
//     /**
//      * 昵称
//      */
//     @TableField("nick_name")
//     private String nickName;
//
//     /**
//      * 真实姓名
//      */
//     @TableField("real_name")
//     private String realName;
//
//     /**
//      * 电话号码
//      */
//     @TableField("phone")
//     private String phone;
//
//     /**
//      * 头像地址
//      */
//     @TableField("avatar")
//     private String avatar;
//
//     /**
//      * 创建时间
//      */
//     @TableField("create_time")
//     private LocalDateTime createTime;
//
//     /**
//      * 状态: 	0: 正常 	1: 删除
//      */
//     @TableField("state")
//     @TableLogic(value = "0", delval = "1")
//     private Byte state;
//
// }
