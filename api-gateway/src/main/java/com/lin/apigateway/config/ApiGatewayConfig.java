package com.lin.apigateway.config;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.Route;
import com.lin.apigateway.filters.AuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ApiGatewayConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiGatewayConfig.class);


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        RouteLocator routeLocator= builder.routes()
                .route("AUTH-MODULE", r -> r.path("/api/auth/**")
                        .uri("lb://Lin-Loan-AUTH-MODULE"))
                .route("Lin-Loan-profile-module", r -> r.path("/api/profile/**")
                        .uri("lb://Lin-Loan-Profile-module"))
                .route("LIN-LOAN-MODULE", r -> r.path("/api/loan/**")
                        .uri("lb://Lin-Loan-module"))
                .build();
      List<List<GatewayFilter>> stringList=  routeLocator.getRoutes()
              .collectList()
              .map(routes -> routes.stream()
                      .map(Route::getFilters) // Correctly use Route here
                      .collect(Collectors.toList()))
              .block();
        LOGGER.info("Routes {}: >>>>> ",stringList);
        return routeLocator;
    }
}
