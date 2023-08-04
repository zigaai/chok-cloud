package com.zigaai.common.security.feign;

import com.zigaai.common.core.model.dto.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("authorization-service")
public interface AuthenticationRemoteService {

    String PREFIX = "/authentication";

    @GetMapping(PREFIX + "/salt")
    ResponseData<String> getSalt(@RequestParam("username") String username, @RequestParam("userType") Byte userType);

}
