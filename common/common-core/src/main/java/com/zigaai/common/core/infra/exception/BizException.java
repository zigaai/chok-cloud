package com.zigaai.common.core.infra.exception;

/**
 * 业务异常
 */
public class BizException extends RuntimeException {

    protected BizException(String message) {
        super(message);
    }

    /**
     * 避免对api异常进行昂贵且无用的堆栈跟踪
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public static BizException of(String message) {
        return new BizException(message);
    }
}
