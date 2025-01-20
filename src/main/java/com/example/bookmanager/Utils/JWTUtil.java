package com.example.bookmanager.Utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JWTUtil {
    private static final SecretKey key = Keys.hmacShaKeyFor("114514_1919810_book_manager_spring_boot".getBytes());
    private static final long expirationTime = 2 * 60 * 60 * 1000; // 2 hours

    public static String generateToken(UserClaims claims) {
        Map<String, Object> claimsMap = claims.toMap();
        return Jwts.builder()
                .claims(claimsMap)
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .issuedAt(new Date())
                .signWith(key)
                .compact();
    }

    public static UserClaims parseToken(String token) throws JwtException {
        token = token.replace("Bearer ", "");
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return new UserClaims((String) claims.get("userName"));
    }

    public static boolean isExpired(String token) {
        token = token.replace("Bearer ", "");
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }
}
