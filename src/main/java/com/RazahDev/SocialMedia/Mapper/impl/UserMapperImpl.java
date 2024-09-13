package com.RazahDev.SocialMedia.Mapper.impl;

import com.RazahDev.SocialMedia.DTO.Response.Auth.LoginResponse;
import com.RazahDev.SocialMedia.DTO.Response.Auth.RegisterResponse;
import com.RazahDev.SocialMedia.Entities.User;
import com.RazahDev.SocialMedia.Mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public RegisterResponse userToRegisterRes(User user) {
        return RegisterResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    @Override
    public LoginResponse userToLoginRes(User user) {
        return null;
    }
}
