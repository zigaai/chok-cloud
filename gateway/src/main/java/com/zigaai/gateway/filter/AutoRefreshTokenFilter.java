// package com.zigaai.gateway.filter;
//
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.nimbusds.jose.JWSObject;
// import com.zigaai.common.core.model.constants.SecurityConstant;
// import com.zigaai.common.core.model.dto.ResponseData;
// import com.zigaai.common.core.utils.JsonUtil;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.apache.commons.lang3.exception.ExceptionUtils;
// import org.springframework.cloud.gateway.filter.GatewayFilter;
// import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.MediaType;
// import org.springframework.http.server.reactive.ServerHttpRequest;
// import org.springframework.stereotype.Component;
// import org.springframework.util.LinkedMultiValueMap;
// import org.springframework.util.MultiValueMapAdapter;
// import org.springframework.util.StringUtils;
// import org.springframework.web.reactive.function.BodyInserters;
// import org.springframework.web.reactive.function.client.WebClient;
//
// import java.text.ParseException;
// import java.time.Duration;
// import java.util.Collections;
// import java.util.Map;
//
// @Slf4j
// @Component
// @RequiredArgsConstructor
// public class AutoRefreshTokenFilter extends AbstractGatewayFilterFactory<Object> {
//
//     private static final String REFRESH_TOKEN_PATH = "/oauth2/token";
//
//     private final WebClient webClient;
//
//     @Override
//     public GatewayFilter apply(Object config) {
//         return (exchange, chain) -> {
//             ServerHttpRequest request = exchange.getRequest();
//             if (REFRESH_TOKEN_PATH.equals(request.getPath().value())) {
//                 return chain.filter(exchange);
//             }
//             String originToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//             if (!StringUtils.hasText(originToken) || !originToken.startsWith(SecurityConstant.TOKEN_PREFIX)) {
//                 return chain.filter(exchange);
//             }
//             String accessToken = originToken.substring(SecurityConstant.TOKEN_PREFIX.length());
//             JWSObject jwsObject;
//             try {
//                 jwsObject = JWSObject.parse(accessToken);
//             } catch (ParseException e) {
//                 log.warn("gateway parse token base64 error: ", e);
//                 return chain.filter(exchange);
//             }
//             Map<String, Object> payload = jwsObject.getPayload().toJSONObject();
//             Object expObj = payload.get(SecurityConstant.TokenKey.EXP);
//             if (expObj == null) {
//                 return chain.filter(exchange);
//             }
//             long exp;
//             try {
//                 exp = Duration.ofSeconds(Long.parseLong(expObj.toString())).toMillis();
//             } catch (NumberFormatException e) {
//                 return chain.filter(exchange);
//             }
//             if (exp - System.currentTimeMillis() > SecurityConstant.REFRESH_DURATION) {
//                 return chain.filter(exchange);
//             }
//             MultiValueMapAdapter<String, String> formData = new LinkedMultiValueMap<>();
//             formData.put("grant_type", Collections.singletonList("refresh_token"));
//             return webClient.post()
//                     .uri("http://127.0.0.1:9000/oauth2/token")
//                     .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
//                     .header(HttpHeaders.AUTHORIZATION, originToken)
//                     .body(BodyInserters.fromFormData(formData))
//                     .retrieve()
//                     .bodyToMono(String.class)
//                     .flatMap(json -> {
//                         ResponseData<?> responseData;
//                         try {
//                             responseData = JsonUtil.readValue(json, ResponseData.class);
//                         } catch (JsonProcessingException e) {
//                             log.warn("gateway parse json response error, json: {}\nerr: {}", json, ExceptionUtils.getStackTrace(e));
//                             return chain.filter(exchange);
//                         }
//                         if (responseData.getData() == null) {
//                             return chain.filter(exchange);
//                         }
//                         @SuppressWarnings("unchecked")
//                         Map<String, String> data = (Map<String, String>) responseData.getData();
//                         String newAccessToken = data.get("access_token");
//                         String newRefreshToken = data.get("refresh_token");
//                         HttpHeaders headers = exchange.getResponse().getHeaders();
//                         headers.add(HttpHeaders.AUTHORIZATION, data.getOrDefault("token_type", "") + " " + newAccessToken);
//                         headers.add("refresh_token", newRefreshToken);
//                         return chain.filter(exchange);
//                     })
//                     .onErrorResume(e -> {
//                         log.warn("gateway refresh token error, err: {}", ExceptionUtils.getStackTrace(e));
//                         return chain.filter(exchange);
//                     });
//         };
//     }
// }
