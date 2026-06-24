package com.K955.the4thSector.Security;

import com.K955.the4thSector.Entity.User;
import com.K955.the4thSector.Exception.ResourceNotFoundException;
import com.K955.the4thSector.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtAuthUtil {

    private final UserRepository userRepository;

    @Value("${jwt.secret.access}")
    private String accessTokenSecret;

    @Value("${jwt.secret.refresh}")
    private String refreshTokenSecret;

    @Value("${jwt.expiration.access}")
    private Long accessTokenExpiration;

    @Value("${jwt.expiration.refresh}")
    private Long refreshTokenExpiration;

    public SecretKey getAccessTokenSecretKey() {
        return Keys.hmacShaKeyFor(accessTokenSecret.getBytes(StandardCharsets.UTF_8));
    }

    public SecretKey getRefreshTokenSecretKey() {
        return Keys.hmacShaKeyFor(refreshTokenSecret.getBytes(StandardCharsets.UTF_8));
    }

    // ========== ACCESS TOKEN METHODS ==========

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("user_id", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(getAccessTokenSecretKey())
                .compact();
    }

    public Claims extractAllClaimsFromAccessToken(String accessToken) {
        return Jwts.parser()
                .verifyWith(getAccessTokenSecretKey())
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
    }

    public String extractUsernameFromAccessToken(String accessToken) {
        return extractAllClaimsFromAccessToken(accessToken).getSubject();
    }

    public Long extractUserIdFromAccessToken(String accessToken) {
        return extractAllClaimsFromAccessToken(accessToken).get("userId", Long.class);
    }

    public Boolean isAccessTokenExpired(String accessToken) {
        return extractAllClaimsFromAccessToken(accessToken).getExpiration().before(new Date());
    }

    public Boolean isAccessTokenValid(User user, String accessToken) {
        return (extractAllClaimsFromAccessToken(accessToken).getSubject().equals(user.getEmail()))
                && (!isAccessTokenExpired(accessToken));
    }

    public String getCurrentUsernameFromAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public User getCurrentUserFromAccessToken() {
        return userRepository.findByEmail(getCurrentUsernameFromAccessToken())
                .orElseThrow(() -> new ResourceNotFoundException(getCurrentUsernameFromAccessToken(), "User"));
    }

    public Long getCurrentUserIdFromAccessToken() {
        return getCurrentUserFromAccessToken().getId();
    }

    // ========== REFRESH TOKEN METHODS ==========

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("user_id", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(getRefreshTokenSecretKey())
                .compact();
    }

    public Claims extractAllClaimsFromRefreshToken(String refreshToken) {
        return Jwts.parser()
                .verifyWith(getRefreshTokenSecretKey())
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload();
    }

    public String extractUsernameFromRefreshToken(String refreshToken) {
        return extractAllClaimsFromRefreshToken(refreshToken).getSubject();
    }

    public Long extractUserIdFromRefreshToken(String refreshToken) {
        return extractAllClaimsFromRefreshToken(refreshToken).get("user_id", Long.class);
    }

    public Boolean isRefreshTokenExpired(String refreshToken) {
        return extractAllClaimsFromRefreshToken(refreshToken).getExpiration().before(new Date());
    }

    public Boolean isRefreshTokenValid(String refreshToken, User user) {
        return (extractAllClaimsFromRefreshToken(refreshToken).getSubject().equals(user.getEmail()))
                && (!isAccessTokenExpired(refreshToken));
    }

}
