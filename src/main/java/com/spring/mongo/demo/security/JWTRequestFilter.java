package com.spring.mongo.demo.security;

import com.spring.mongo.demo.common.error.MessageCode;
import com.spring.mongo.demo.common.exception.DocStoreAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JWTRequestFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;
    // Exception handler
    private HandlerExceptionResolver handlerExceptionResolver;

    public JWTRequestFilter(JwtUtil jwtUtil, HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtUtil = jwtUtil;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            if (StringUtils.isEmpty(header) || !header.startsWith(JwtUtil.TOKEN_PREFIX)) {
                handlerExceptionResolver.resolveException(request, response, null, null);
                chain.doFilter(request, response);
                return;
            }

            // Get jwt token and validate
            final String token = header.split(" ")[1].trim();
            if (!jwtUtil.validateToken(token)) {
                handlerExceptionResolver.resolveException(request, response, null, null);
                chain.doFilter(request, response);
                return;
            }

                UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request, response, token);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            chain.doFilter(request, response);

//        } catch (SignatureException ex) {
//            this.resolveJwtException(request, response, "Invalid JWT signature");
//        } catch (ExpiredJwtException ex) {
//            this.resolveJwtException(request, response, "JWT token expired.");
//        } catch (AccessDeniedException ex) {
//            this.resolveJwtException(request, response, "Invalid Token");
        } catch (Exception ex) {
            log.error("Error during JWT Token validation", ex);
            this.resolveJwtException(request, response, "Access denied.");
        }
    }

    private void resolveJwtException(HttpServletRequest request, HttpServletResponse response, String message) {
        log.error(message);
        DocStoreAuthenticationException exception = new DocStoreAuthenticationException();
        exception.setMessageCode(MessageCode.AUTHENTICATION_ERROR);
        exception.setArguments(new String[]{message});

        handlerExceptionResolver.resolveException(request, response, null, exception);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request,
            HttpServletResponse response, String token) {
        // Keep it simple for now, which only check username
        String username = jwtUtil.getUsernameFromToken(token);
        UserDetails userDetails = new User(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                Optional.ofNullable(userDetails).map(UserDetails::getAuthorities).orElse(List.of())
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authentication;
    }
}
