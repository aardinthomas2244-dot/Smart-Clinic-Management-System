package com.smartclinic.management.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    // Injecting a base64 encoded secret from application.properties (or fallback to a secure default)
    @Value("${jwt.secret:Y2hvb29zZS1hLXNlY3JldC1rZXktdGhhdC1pcy1hdC1sZWFzdC0yNTYtYml0cy1sb25nLWZvci1oczI1Ng==}")
    private String secretKey;

    // Token expiration configuration (e.g., 24 hours in milliseconds)
    private final long expirationTimeInMs = 86400000;

    /**
     * Criteria 2: Implements a method to return the signing key using the configured secret.
     * Converts the raw string secret into a cryptographically sound HMAC signing key.
     * @return the Key object used for signing JWTs
     */
    public Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Criteria 1: Defines a method to generate a JWT token using the user's email.
     * @param email the user's unique email address used as the subject
     * @return a signed, compact JWT string
     */
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>(); // Add custom roles/claims here if needed
        long now = System.currentTimeMillis();
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email) // Sets the user's email as the principal subject
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationTimeInMs)) // Sets token validity length
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Signs the token using our key method
                .compact();
    }
}
