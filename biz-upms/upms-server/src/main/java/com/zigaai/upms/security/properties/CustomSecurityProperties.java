package com.zigaai.upms.security.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "security")
public class CustomSecurityProperties {

    /**
     * 忽略鉴权路径
     */
    private List<String> ignoreUrls;

}
