package com.zigaai.common.core.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class PayloadDTO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户类型
     */
    private Byte userType;

    /**
     * 过期时间
     */
    private Long exp;

    /**
     * 签发时间
     */
    private Long iat;

    /**
     * token持续时间
     */
    @JsonIgnore
    private Long duration;
}
