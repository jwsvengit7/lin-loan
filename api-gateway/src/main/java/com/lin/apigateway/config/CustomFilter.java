package com.lin.apigateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CustomFilter implements GatewayFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange)
                .onErrorResume(Exception.class, ex -> handleError(exchange,HttpStatus.UNAUTHORIZED,ex.getMessage()));
    }
    public static Mono<Void> handleError(ServerWebExchange exchange, HttpStatus status, String message) {
        ServerHttpResponse response = exchange.getResponse();
        String requestId = exchange.getRequest().getId();
        response.setStatusCode(status);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json");
        String body = String.format(
                "{\"error\": \"%s\", \"status\": %d, \"requestId\": \"%s\"}",
                message, status.value(), requestId
        );

        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }



    @Override
    public int getOrder() {
        return -1;
    }
}