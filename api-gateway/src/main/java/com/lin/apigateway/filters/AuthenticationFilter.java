package com.lin.apigateway.filters;

import com.lin.apigateway.routes.GatewayRouteValidate;
import com.lin.commonsshared.jwt.JwtServiceInfo;
import com.lin.commonsshared.model.response.LinLoanResponse;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import static com.lin.apigateway.config.CustomFilter.handleError;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    private GatewayRouteValidate validator;

    @Autowired
    private JwtServiceInfo jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        LOGGER.info("Applying AuthenticationFilter");

        return (exchange, chain) -> {
            if (validator.isSecured().test(exchange.getRequest())) {
                LOGGER.info("Secured endpoint detected: {}", exchange.getRequest().getURI());

                HttpHeaders headers = exchange.getRequest().getHeaders();
                LOGGER.info("Request headers: {}", headers);

                if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                    LOGGER.warn("Missing authorization header");
                    return handleError(exchange,HttpStatus.UNAUTHORIZED,"Missing authorization header");

                }

                String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);

                } else {
                    LOGGER.warn("Invalid authorization header format");
                    return handleError(exchange,HttpStatus.UNAUTHORIZED,"Invalid authorization header format");
                }

                try {
                    String user =  jwtUtil.extractUsernameFromToken(authHeader);
                    Claims data= jwtUtil.extractClaimsDetails(authHeader);
                    String role = data.get("Role", String.class);
                    LOGGER.info("Claims user: {}", data.toString());
                  if(jwtUtil.isTokenValidApi(authHeader,user)) {
                      LOGGER.info("Request user: {}", user);
                      ServerHttpRequest updatedRequest = exchange.getRequest().mutate()
                              .header("Role", role)
                              .build();

                      LOGGER.info("Header ID: {}", updatedRequest.getId());
                      LOGGER.info("headers: {}", updatedRequest.getHeaders());
                      return chain.filter(exchange.mutate().request(updatedRequest).build());
                  }
                    LOGGER.warn("Token Expired ");
                    return handleError(exchange,HttpStatus.UNAUTHORIZED,"Token Expired");


                } catch (Exception e) {
                    LOGGER.error("Invalid access: {}", e.getMessage(), e);
                    return handleError(exchange,HttpStatus.UNAUTHORIZED,e.getMessage());
                }
            } else {
                return chain.filter(exchange);
            }
        };
    }


    public static class Config {
    }
}
