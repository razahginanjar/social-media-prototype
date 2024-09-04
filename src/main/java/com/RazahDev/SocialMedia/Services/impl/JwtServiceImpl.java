package com.RazahDev.SocialMedia.Services.impl;

import com.RazahDev.SocialMedia.DTO.Response.Auth.JWTClaims;
import com.RazahDev.SocialMedia.Entities.User;
import com.RazahDev.SocialMedia.Services.JwtService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Objects;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    private final String JWT_SECRET;
    private final String JWT_ISSUE;
    private final Long JWT_EXPIRED_AT;

    public JwtServiceImpl(@Value(value = "${social_media-project.jwt.secret-key}") String JWT_SECRET,
                          @Value(value = "${social_media-project.jwt.issue}") String JWT_ISSUE,
                          @Value(value = "${social_media-project.jwt.expiredatmiliseconds}") Long JWT_EXPIRED_AT) {
        this.JWT_SECRET = JWT_SECRET;
        this.JWT_ISSUE = JWT_ISSUE;
        this.JWT_EXPIRED_AT = JWT_EXPIRED_AT;
    }

    @Override
    public String generateToken(User userAccount) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
            return JWT.create()
                    .withSubject(userAccount.getId())
                    .withClaim("Role", userAccount.getAuthorities().stream().map(
                            GrantedAuthority::getAuthority
                    ).toList())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plusSeconds(JWT_EXPIRED_AT))
                    .withIssuer(JWT_ISSUE)
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            // Invalid Signing configuration / Couldn't convert Claims.
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Error: While Creating Token");
        }
    }

    @Override
    public Boolean verifyToken(String token) {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(JWT_ISSUE)
                    .build();

            decodedJWT = verifier.verify(parseJWTToken(token));
            return true;
        } catch (JWTVerificationException exception){
            // Invalid signature/claims
            log.info(exception.getLocalizedMessage());
        }
        return false;
    }

    @Override
    public JWTClaims claimToken(String token) {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(JWT_ISSUE)
                    .build();

            decodedJWT = verifier.verify(parseJWTToken(token));
            log.info(parseJWTToken(token));
            return JWTClaims.builder()
                    .userID(decodedJWT.getSubject())
                    .build();
        } catch (JWTVerificationException exception){
            // Invalid signature/claims
            log.info(exception.getLocalizedMessage());
        }
        return null;
    }

    private String parseJWTToken(String token)
    {
        if (Objects.nonNull(token) && token.startsWith("Bearer "))
        {
            return token.substring(7);
        }
        return null;
    }
}
