package com.spring.mongo.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

public class JwtUtil implements Serializable {

    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * Bearer token:
     * eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJzdWIiOiJKd3RUZXN0VXNlciIsIklzc3VlciI6Iklzc3VlciIsImV4cCI6MTY3OTAyNDk3OSwiaWF0IjoxNjQ3NDg4OTc5fQ.Jd-lvq6lfcK7FvxXZZvfkUBr7UOLVtLDWtDZD1WnEL4
     */
//    String jwtSecret = "00XlTUGku3kmEw0ks280Cb7TUpartX0VqXGqqgMfIPc=";
    private String jwtSecret = "dfDWLfMr+v01XWqsriGgiMxVqSp82FvIYUoADqDxvj0=";

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token) {
        return StringUtils.isNotBlank(getUsernameFromToken(token))
            && !isTokenExpired(token);
    }
}
