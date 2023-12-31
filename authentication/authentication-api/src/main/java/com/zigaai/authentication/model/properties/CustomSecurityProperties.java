package com.zigaai.authentication.model.properties;

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


    /**
     * token 配置
     */
    private Token token;

    @Getter
    @Setter
    @ToString
    public static class Token {

        /**
         * 登录token过期时间
         */
        private Long timeToLive = 3600L;

    }
}
