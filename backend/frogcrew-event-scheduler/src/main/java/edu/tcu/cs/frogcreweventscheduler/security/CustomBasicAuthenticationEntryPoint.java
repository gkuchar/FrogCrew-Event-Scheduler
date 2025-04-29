package edu.tcu.cs.frogcreweventscheduler.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         org.springframework.security.core.AuthenticationException authEx)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        Map<String,Object> body = Map.of(
                "flag", false,
                "code", 401,
                "message", "Full authentication is required to access this resource",
                "data", null
        );
        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("FrogCrew");
        super.afterPropertiesSet();
    }
}
