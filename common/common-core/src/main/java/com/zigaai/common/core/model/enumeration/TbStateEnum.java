package com.zigaai.common.core.model.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum TbStateEnum {

    /**
     * 正常
     */
    NORMAL((byte) 0),

    /**
     * 删除
     */
    DELETE((byte) 1);

    private final byte val;

    private static final Map<Byte, TbStateEnum> VALUE_MAP = Collections.unmodifiableMap(Arrays.stream(values()).collect(Collectors.toMap(TbStateEnum::getVal, Function.identity())));

    public static boolean toBool(Byte val) {
        TbStateEnum state = VALUE_MAP.get(val);
        return state != null && !Objects.equals(state.getVal(), DELETE.getVal());
    }
}
