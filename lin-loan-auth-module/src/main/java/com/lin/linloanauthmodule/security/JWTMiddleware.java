package com.lin.linloanauthmodule.security;

import com.lin.commonsshared.jwt.JwtServiceInfo;
import com.lin.linloanauthmodule.config.AppUserConfigService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class JWTMiddleware  extends OncePerRequestFilter {
    private final JwtServiceInfo jwtService;
    private final AppUserConfigService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @Nullable HttpServletResponse response,
                                    @Nullable FilterChain filterChain) throws ServletException, IOException {

        String accessToken = null;
        String username = null;
        String authHeader = request.getHeader("Authorization");

        if(Objects.nonNull(authHeader) && authHeader.startsWith("Bearer ")) {
            accessToken = authHeader.substring(7);
            username = jwtService.extractUsernameFromToken(accessToken);
        }

        if(Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())){
            UserDetails userDetails =  userService.loadUserByUsername(username);

            if (jwtService.isTokenValid(accessToken, userDetails.getUsername())){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        assert filterChain != null;
        filterChain.doFilter(request, response);
    }
}
