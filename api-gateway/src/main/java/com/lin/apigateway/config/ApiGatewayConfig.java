package com.lin.apigateway.config;

import com.lin.apigateway.filters.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-module", r -> r.path("/api/auth/**")
                        .uri("lb://Lin-Loan-AUTH-MODULE"))
                .route("profile-module", r -> r.path("/api/profile/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://Lin-Loan-Profile-module"))
                .route("loan-module", r -> r.path("/api/loan/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://Lin-Loan-module"))
                .build();
    }
}
