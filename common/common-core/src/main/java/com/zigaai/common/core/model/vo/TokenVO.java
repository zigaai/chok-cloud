package com.zigaai.common.core.model.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@ToString
@RequiredArgsConstructor
public class TokenVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String token;

}
