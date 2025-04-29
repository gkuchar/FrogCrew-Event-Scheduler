// src/main/java/edu/tcu/cs/frogcreweventscheduler/security/AuthController.java
package edu.tcu.cs.frogcreweventscheduler.security;

import edu.tcu.cs.frogcreweventscheduler.system.Result;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Triggered by HTTP Basic.  Client must send:
     *   Authorization: Basic base64(username:password)
     */
    @PostMapping("/login")
    public Result login(Authentication authentication) {
        return authService.createLoginInfo(authentication);
    }
}
