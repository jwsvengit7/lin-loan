package com.lin.apigateway.filters;

import com.lin.apigateway.routes.GatewayRouteValidate;
import com.lin.commonsshared.jwt.JwtServiceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

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
        return ((exchange, chain) -> {
            if (validator.isSecured().test(exchange.getRequest())) {
                LOGGER.info("Incoming request to secured endpoint: {}", exchange.getRequest().getURI());
                HttpHeaders headers = exchange.getRequest().getHeaders();
                if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                    LOGGER.warn("Missing authorization header");
                    throw new RuntimeException("Missing authorization header");
                }

                String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                   String user= jwtUtil.extractUsernameFromToken(authHeader);
                   LOGGER.info("USER DETAILS {}, >>>> {}",user,AuthenticationFilter.class.getName());
                   jwtUtil.isTokenValidApi(authHeader,user);

                } catch (Exception e) {
                    LOGGER.warn("Invalid access...!");
                    throw new RuntimeException("Unauthorized access to application");
                }


//                exchange.getRequest().mutate()
//                        .header(Headers.APPID.name(), APPID)
//                        .header(Headers.APP_KEY.name(), KEYS)
//                        .header(Headers.APP_SECRET.name(), APP_SECRET)
//                        .build();
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {
    }
}




