package com.RazahDev.SocialMedia.Services;

import com.RazahDev.SocialMedia.DTO.Response.Auth.JWTClaims;
import com.RazahDev.SocialMedia.Entities.User;

public interface JwtService {
    String generateToken(User userAccount);
    Boolean verifyToken(String token);
    JWTClaims claimToken(String token);
}
