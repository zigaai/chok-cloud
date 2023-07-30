package com.zigaai.authorization.controller;

import com.zigaai.common.core.model.dto.ResponseData;
import com.zigaai.authorization.model.convertor.SystemUserConvertor;
import com.zigaai.authorization.model.vo.SystemUserVO;
import com.zigaai.authorization.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限相关接口
 */
@RestController
@RequestMapping("/authorization")
@RequiredArgsConstructor
public class AuthorizationController {

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public ResponseData<SystemUserVO> getInfo() {
        SystemUserVO info = SystemUserConvertor.INSTANCE.toVO(SecurityUtil.currentUser());
        return ResponseData.success(info);
    }

}
