package com.io.bookstoreapi.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthTokenFilter extends OncePerRequestFilter
{

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserDetailsService userService;

    private Optional<String> parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        System.out.println("header auth: " + headerAuth);
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return Optional.of(headerAuth.substring(7, headerAuth.length()));
        }
        return Optional.empty();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {

            System.out.println("Inside doFilterInternal");
            // get JWT token from request
            Optional<String> jwtToken = parseJwt(request);
            // Check if token exists and its valid
            if(jwtToken.isPresent() && jwtUtils.isJwtTokenValid(jwtToken.get())) {

                String jwtTokenString = jwtToken.get();
                String username = jwtUtils.getUsernameFromJwtToken(jwtTokenString);

                // Get user with username from token
                UserDetails principal = userService.loadUserByUsername(username);

                // Create authentication object
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                            principal, null
                        );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                System.out.println("Nie wchodzi");
                // Set authentication object in Security Context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch(Exception ex) {
            logger.error("Cannot set user authentication: {}", ex);
        }

        filterChain.doFilter(request, response);
    }
}
