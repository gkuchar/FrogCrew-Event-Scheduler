package edu.tcu.cs.frogcreweventscheduler.security;

import edu.tcu.cs.frogcreweventscheduler.system.Result;
import edu.tcu.cs.frogcreweventscheduler.system.StatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final JwtProvider jwtProvider;

    public AuthService(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    public Result createLoginInfo(Authentication authentication) {
        String token = jwtProvider.generateToken(authentication);

        // you can include whatever userInfo you like here; Spring Security principal exposes username and authorities:
        Map<String,Object> data = Map.of(
                "userInfo", Map.of(
                        "username", authentication.getName(),
                        "roles", authentication.getAuthorities()
                ),
                "token", token
        );

        return new Result(true, StatusCode.SUCCESS, "User Info and JSON Web Token", data);
    }
}
