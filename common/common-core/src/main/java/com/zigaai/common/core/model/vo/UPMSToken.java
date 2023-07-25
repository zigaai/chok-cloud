package com.zigaai.common.core.model.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@ToString
@RequiredArgsConstructor
public class UPMSToken implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * token 值
     */
    private final String token;

    /**
     * 签发时间
     */
    private final Long iat;

    /**
     * 过期时间
     */
    private final Long exp;

}
