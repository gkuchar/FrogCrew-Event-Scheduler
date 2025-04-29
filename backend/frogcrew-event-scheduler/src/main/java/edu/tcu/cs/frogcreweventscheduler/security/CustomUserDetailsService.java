package edu.tcu.cs.frogcreweventscheduler.security;

import edu.tcu.cs.frogcreweventscheduler.crew.CrewMember;
import edu.tcu.cs.frogcreweventscheduler.crew.CrewMemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CrewMemberRepository repo;

    public CustomUserDetailsService(CrewMemberRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CrewMember user = repo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // we assume crewMember.getRole() returns e.g. "ADMIN" or "USER"
        var authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                true, true, true, true,
                List.of(authority)
        );
    }
}
