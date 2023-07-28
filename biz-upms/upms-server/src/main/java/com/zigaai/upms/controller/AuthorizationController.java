package com.zigaai.upms.controller;

import com.zigaai.common.core.model.dto.ResponseData;
import com.zigaai.upms.model.security.SystemUser;
import com.zigaai.upms.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorization")
@RequiredArgsConstructor
public class AuthorizationController {

    @GetMapping("/info")
    public ResponseData<SystemUser> getInfo() {
        return ResponseData.success(SecurityUtil.currentUser());
    }

}
