package com.zigaai.common.core.infra.exception;

/**
 * 业务参数异常
 */
public class BizIllegalArgumentException extends BizException {

    protected BizIllegalArgumentException(String message) {
        super(message);
    }

    public static BizIllegalArgumentException of(String message) {
        return new BizIllegalArgumentException(message);
    }

}
