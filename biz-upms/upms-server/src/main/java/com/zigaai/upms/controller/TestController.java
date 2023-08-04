package com.zigaai.upms.controller;

import com.zigaai.common.core.model.dto.ResponseData;
import com.zigaai.common.security.model.dto.PayloadDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/info")
    public ResponseData<PayloadDTO> getInfo(){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PayloadDTO payloadDTO = PayloadDTO.fromAccessTokenClaims(jwt.getClaims());
        return ResponseData.success(payloadDTO);
    }

}
