package edu.tcu.cs.frogcreweventscheduler.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationEntryPoint bearerEntryPoint;

    public JwtAuthenticationFilter(JwtProvider jwtProvider,
                                   CustomUserDetailsService userDetailsService,
                                   CustomBearerTokenAuthenticationEntryPoint bearerEntryPoint) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
        this.bearerEntryPoint = bearerEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtProvider.validateToken(token)) {
                String username = jwtProvider.getUsernameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                var auth = new JwtAuthenticationToken(userDetails, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                bearerEntryPoint.commence(request, response,
                        new org.springframework.security.core.AuthenticationException("Invalid JWT") {});
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
