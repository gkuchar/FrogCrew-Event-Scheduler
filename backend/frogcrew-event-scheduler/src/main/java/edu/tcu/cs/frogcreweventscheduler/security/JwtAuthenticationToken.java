package edu.tcu.cs.frogcreweventscheduler.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public JwtAuthenticationToken(Object principal,
                                  Collection<? extends GrantedAuthority> authorities) {
        super(principal, null, authorities);
    }
}
