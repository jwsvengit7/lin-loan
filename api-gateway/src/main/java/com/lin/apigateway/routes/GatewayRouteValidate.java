package com.lin.apigateway.routes;


import com.lin.commonsshared.api.ApiEndpoints;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;


import java.util.*;
import java.util.function.Predicate;

@Component
public class GatewayRouteValidate {
    private final static String BASEURL = ApiEndpoints.AUTH_BASEURL;

    public static final List<String> APIEndpoint = List.of(
            BASEURL+ApiEndpoints.AUTH_SIGNUP,
            BASEURL+ApiEndpoints.AUTH_LOGIN,
            BASEURL+ApiEndpoints.AUTH_RESEND_OTP,
            BASEURL+ApiEndpoints.AUTH_VERIFY_OTP,
            "/eureka"
    );
    public Predicate<ServerHttpRequest> isSecured() {
        return  serverRequest ->
                APIEndpoint
                        .stream()
                        .noneMatch(url -> serverRequest.getURI().getPath().startsWith(url));
    }
}