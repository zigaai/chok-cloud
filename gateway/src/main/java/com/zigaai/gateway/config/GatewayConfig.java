package com.zigaai.gateway.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class GatewayConfig {

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create();
        httpClient.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        httpClient.responseTimeout(Duration.ofMillis(5000));
        httpClient.doOnConnected(conn -> {
            conn.addHandlerLast(new ReadTimeoutHandler(5));
            conn.addHandlerLast(new WriteTimeoutHandler(5));
        });
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
