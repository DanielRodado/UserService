package com.example.userservice.configurations;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    private final SecretKey secretKey;

    private String secret = System.getenv("SECRET_KEY");

    @Value("${jwt.expiration}")
    private long expiration;

    public JwtUtils() {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secret));
    }

    private String buildToken(String username, Map<String, String> claims) {
        return Jwts.builder()
                .subject(username)
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    private String generateClaims(String username, String rol) {
        Map<String, String> claims = new HashMap<>();
        claims.put("rol", rol);
        return buildToken(username, claims);
    }

    public String generateToken(String username, String rol) {
        return generateClaims(username, rol);
    }

}
