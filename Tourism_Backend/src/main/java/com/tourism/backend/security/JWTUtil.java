package com.tourism.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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
    private final SecretKey REFRESH_SECRET_KEY;

    public JWTUtil() {
        this.SECRET_KEY = Keys.hmacShaKeyFor("HuynhPhatSuperSecretKeyForJWTGeneration2024".getBytes());
        this.REFRESH_SECRET_KEY = Keys.hmacShaKeyFor("HuynhPhatRefreshTokenSecretKey2024".getBytes());
    }

    public String extractUsername(String token, boolean isRefreshToken) {
        return extractClaim(token, Claims::getSubject, isRefreshToken ? REFRESH_SECRET_KEY : SECRET_KEY);
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
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername(), SECRET_KEY, 1000 * 60 * 60 * 10); // 10 hours
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        long nowMillis = System.currentTimeMillis();
        long ttlMillis = 1000 * 60 * 60 * 24 * 30; // 30 days
        return createToken(claims, userDetails.getUsername(), REFRESH_SECRET_KEY, nowMillis + ttlMillis);
    }

    private String createToken(Map<String, Object> claims, String subject, SecretKey key, long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
    
        long expMillis = nowMillis + ttlMillis;
        Date exp = new Date(expMillis);

        // Log thời gian để kiểm tra
        System.out.println("Creating token with iat: " + now + " and exp: " + exp);
    
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)  // Sử dụng thời gian hiện tại
                .setExpiration(exp) // Đặt thời gian hết hạn chính xác
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }    

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token, false);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, SECRET_KEY));
    }

    public Boolean validateRefreshToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token, true);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, REFRESH_SECRET_KEY));
    }
}
