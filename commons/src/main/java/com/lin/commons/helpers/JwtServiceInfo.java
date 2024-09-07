package com.lin.commons.helpers;

import com.lin.commons.model.enums.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.lin.commons.utils.AppUtils.*;

@Component
public class JwtServiceInfo {

    @Value("${jwt.expiration}")
    private int EXPIRATION_DATE;
    @Value("${jwt.expiration_time}")
    private int REFRESH_TOKEN;
    @Value("${jwt.secrete.key}")
    private String SECRET_KEY;

    public String extractUsernameFromToken(String token) {
        return extractClaims(token, Claims::getSubject);
    }
    public <T> T extractClaims(String token, Function<Claims,T> claimsTFunction){
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }
    public String generateToken(Authentication authentication,UserType role) {
        final HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("Company",APP_NAME);
        hashMap.put("Department",APP_LOCATION);
        hashMap.put("Role",role.name());
        return buildToken(hashMap, authentication, EXPIRATION_DATE);
    }
    public String generateRefreshToken(Authentication authentication, UserType role) {

        final HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("Company",APP_NAME);
        hashMap.put("Department",APP_LOCATION);
        hashMap.put("Role",role.name());
        return buildToken(hashMap, authentication, REFRESH_TOKEN);
    }
    private String buildToken(Map<String, Object> extraClaims,Authentication authentication, long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(authentication.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
