package com.io.bookstoreapi.config.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@PropertySource(value = {"classpath:application-dev.properties"})
public class JWTUtils {

    private static final Logger logger = LoggerFactory.getLogger(JWTUtils.class);


    @Value("${JWT_AUTH_SECRET}")
    private String jwtSecret;

    @Value("${JWT_AUTH_EXPIRATION_MS}")
    private int jwtExpirationMs;

    public String generateJWTToken(Authentication authentication) {

        UserDetails principal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwtToken(String authToken) {
        return Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(authToken)
                .getBody()
                .getSubject();
    }

    public boolean isJwtTokenValid(String jwtToken) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(jwtToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
