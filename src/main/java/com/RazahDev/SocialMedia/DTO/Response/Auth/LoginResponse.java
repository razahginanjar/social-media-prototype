package com.RazahDev.SocialMedia.DTO.Response.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class LoginResponse {
    private String username;
    private List<String> roles;
    private String token;
}
