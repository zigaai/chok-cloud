package com.zigaai.common.core.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zigaai.common.core.infra.exception.BizException;
import com.zigaai.common.core.model.enumeration.ResponseDataStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@JsonInclude
public class ResponseData<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @see ResponseDataStatus#getValue()
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应结果
     */
    private T data;

    public static ResponseData<Void> success() {
        return success(null);
    }

    public static <T> ResponseData<T> success(T data) {
        return success(ResponseDataStatus.SUCCESS.getMsg(), data);
    }

    public static <T> ResponseData<T> success(String message) {
        return success(message, null);
    }

    public static <T> ResponseData<T> success(String message, T data) {
        return build(ResponseDataStatus.SUCCESS.getValue(), message, data);
    }

    public static <T> ResponseData<T> fail(T data) {
        return fail(ResponseDataStatus.FAILED.getMsg(), data);
    }

    public static <T> ResponseData<T> fail(String message) {
        return fail(message, null);
    }

    public static <T> ResponseData<T> fail(String message, T data) {
        return build(ResponseDataStatus.FAILED.getValue(), message, data);
    }

    public static <T> ResponseData<T> forbidden(T data) {
        return forbidden(ResponseDataStatus.FORBIDDEN.getMsg(), data);
    }

    public static <T> ResponseData<T> forbidden(String message, T data) {
        return build(ResponseDataStatus.FORBIDDEN.getValue(), message, data);
    }

    public static <T> ResponseData<T> unauthorized(String msg) {
        return unauthorized(msg, null);
    }

    public static <T> ResponseData<T> unauthorized(T data) {
        return unauthorized(ResponseDataStatus.UNAUTHORIZED.getMsg(), data);
    }

    public static <T> ResponseData<T> unauthorized(String message, T data) {
        return build(ResponseDataStatus.UNAUTHORIZED.getValue(), message, data);
    }

    public static <T> ResponseData<T> badRequest(String message) {
        return badRequest(message, null);
    }

    public static <T> ResponseData<T> badRequest(String message, T data) {
        return build(ResponseDataStatus.BAD_REQUEST.getValue(), message, data);
    }

    public static <T> ResponseData<T> unknownError(String message) {
        return build(ResponseDataStatus.UNKNOWN_ERROR.getValue(), message, null);
    }

    public static <T> ResponseData<T> methodNotAllowed(String message) {
        return build(ResponseDataStatus.METHOD_NOT_ALLOW.getValue(), message, null);
    }

    public static <T> ResponseData<T> notFound() {
        return build(ResponseDataStatus.NOT_FOUND.getValue(), ResponseDataStatus.NOT_FOUND.getMsg(), null);
    }

    public static <T> ResponseData<T> notFound(String message) {
        return build(ResponseDataStatus.NOT_FOUND.getValue(), message, null);
    }

    public static <T> ResponseData<T> build(Integer code, String message, T data) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.code = code;
        responseData.message = message;
        responseData.data = data;
        return responseData;
    }

    public static <T> boolean isEmpty(ResponseData<T> res) {
        return res == null || !Objects.equals(ResponseDataStatus.SUCCESS.getValue(), res.getCode()) || res.getData() == null;
    }

    public static <T> T unwrap(ResponseData<T> res) {
        if (isEmpty(res)) {
            Assert.notNull(res, "res can not be null");
            throw BizException.of(res.getMessage());
        }
        return res.getData();
    }

}
