package edu.tcu.cs.frogcreweventscheduler.security;

import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    private final CustomBasicAuthenticationEntryPoint basicEntryPoint;
    private final CustomBearerTokenAuthenticationEntryPoint bearerEntryPoint;
    private final CustomBearerTokenAccessDeniedHandler accessDeniedHandler;
    private final JwtAuthenticationFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfiguration(CustomBasicAuthenticationEntryPoint basicEntryPoint,
                                 CustomBearerTokenAuthenticationEntryPoint bearerEntryPoint,
                                 CustomBearerTokenAccessDeniedHandler accessDeniedHandler,
                                 JwtAuthenticationFilter jwtFilter,
                                 CustomUserDetailsService userDetailsService) {
        this.basicEntryPoint = basicEntryPoint;
        this.bearerEntryPoint = bearerEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(bearerEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> auth
                        // Public:
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/crewMember").permitAll()

                        // Invite = Admin only
                        .requestMatchers(HttpMethod.GET,  "/invite/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/invite").hasRole("ADMIN")

                        // CrewMember delete = Admin
                        .requestMatchers(HttpMethod.DELETE, "/crewMembers/**").hasRole("ADMIN")
                        // other CrewMember endpoints = any authenticated
                        .requestMatchers("/crewMembers/**").authenticated()

                        // GameSchedule management
                        .requestMatchers(HttpMethod.POST, "/gameSchedule/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,  "/gameSchedule/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/gameSchedule/**").hasRole("ADMIN")
                        .requestMatchers("/gameSchedule/**").authenticated()

                        // Availability = crew members only
                        .requestMatchers("/availability/**").authenticated()

                        // All other API calls require authentication
                        .anyRequest().authenticated()
                )
                // Basic for login (calls to /auth/login will be handled by Basic)
                .httpBasic(b -> b.authenticationEntryPoint(basicEntryPoint))
                // Add JWT filter for Bearer tokens
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }
}
