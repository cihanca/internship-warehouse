package com.kafein.intern.warehouse.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private String APP_SECRET = "yeahyayeahyayeahyayeahyayeahyayeahyayeahyayeahyayeahyayeahyayeahyayeahyayeahyayeahyayeahyayeahya";
    private long EXPIRES_IN = 604800L;

    public String generateJwtToken(Authentication auth) {
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        Date expireDate = java.sql.Date.valueOf(LocalDate.now().plusDays(EXPIRES_IN));
        return Jwts.builder()
                .setSubject(Integer.toString(userDetails.getId()))
                .claim("username",auth.getName())
                .claim("authorities", (auth.getAuthorities()))
                .setIssuedAt(java.sql.Date.valueOf(LocalDate.now()))
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, APP_SECRET)
                .compact();
    }

    public String generateJwtTokenByUserId(Long userId) {
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder().setSubject(Long.toString(userId))
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
    }
    Integer getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
        return Integer.parseInt(claims.getSubject());
    }

    boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJwt(token);
            return !isTokenExpired(token);
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            return false;
        }
    }

    boolean isTokenExpired(String token) {
        Date expired = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();
        return expired.before(java.sql.Date.valueOf(LocalDate.now()));
    }

}
