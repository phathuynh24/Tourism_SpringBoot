package com.tourism.backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTUtil {

    private final SecretKey SECRET_KEY;
    private static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * 10; // 10 hours

    public JWTUtil(@Value("${JWT_SECRET_KEY}") String secretKey) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String extractUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject, SECRET_KEY);
    }

    private Date extractExpiration(String token, SecretKey key) {
        return extractClaim(token, Claims::getExpiration, key);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver, SecretKey key) {
        final Claims claims = extractAllClaims(token, key);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, SecretKey key) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token, SecretKey key) {
        return extractExpiration(token, key).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        return createToken(new HashMap<>(), userDetails.getUsername(), SECRET_KEY, JWT_TOKEN_VALIDITY);
    }

    private String createToken(Map<String, Object> claims, String subject, SecretKey key, long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
    
        long expMillis = nowMillis + ttlMillis;
        Date exp = new Date(expMillis);
    
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, SECRET_KEY));
    }
}
