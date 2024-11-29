package com.soutenances.soutenances.Filter;

import com.soutenances.soutenances.Models.User;
import com.soutenances.soutenances.Service.JWTService;
import com.soutenances.soutenances.Service.UserDetailServiceI;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	private final JWTService jwtService;
    private final UserDetailServiceI userDetailsService;


    public JwtAuthenticationFilter(JWTService jwtService, UserDetailServiceI userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = null;
            try {
                username = jwtService.extractUsername(token);
            } catch (ExpiredJwtException e) {
                // Token has expired, check for refresh token
                String refreshToken = request.getHeader("Refresh-Token");
                if (refreshToken != null) {
                    try {
                        username = jwtService.extractUsername(refreshToken);
                        // Generate a new access token
                        User user = (User) userDetailsService.loadUserByUsername(username);
                        String newToken = jwtService.generateToken(user);
                        response.setHeader("New-Token", newToken);
                    } catch (Exception ex) {
                        // Refresh token is invalid or expired
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired, please log in again");
                        return;
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired, please log in again");
                    return;
                }
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.isValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

	}

