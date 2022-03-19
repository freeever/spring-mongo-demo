package com.spring.mongo.demo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final HandlerExceptionResolver handlerExceptionResolver;

    public SecurityConfig(HandlerExceptionResolver handlerExceptionResolver) {
        super();
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //Disabling session management
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            // Set permissions on endpoints
            .authorizeRequests()
                .anyRequest()
                .authenticated().and()
            .cors().and()
            .csrf().disable()
            // Add JWT token filter
            .addFilterBefore(new JWTRequestFilter(new JwtUtil(), handlerExceptionResolver),
                UsernamePasswordAuthenticationFilter.class);
    }
}
