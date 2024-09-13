package com.RazahDev.SocialMedia.Services;

import com.RazahDev.SocialMedia.DTO.Request.Auth.LoginRequest;
import com.RazahDev.SocialMedia.DTO.Request.Auth.RegisterRequest;
import com.RazahDev.SocialMedia.DTO.Response.Auth.LoginResponse;
import com.RazahDev.SocialMedia.DTO.Response.Auth.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest loginRequest);
}
