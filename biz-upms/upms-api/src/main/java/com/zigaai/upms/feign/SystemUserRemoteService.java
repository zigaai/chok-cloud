package com.zigaai.upms.feign;

import com.zigaai.common.core.model.dto.ResponseData;
import com.zigaai.upms.model.vo.SystemUserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "upms-service", url = "127.0.0.1:9100")
public interface SystemUserRemoteService {

    String PREFIX = "/authorization";

    @GetMapping(PREFIX + "/info")
    ResponseData<SystemUserVO> getInfo();

}
