package com.RazahDev.SocialMedia.Mapper;

import com.RazahDev.SocialMedia.DTO.Response.Auth.LoginResponse;
import com.RazahDev.SocialMedia.DTO.Response.Auth.RegisterResponse;
import com.RazahDev.SocialMedia.Entities.User;


public interface UserMapper {
    RegisterResponse userToRegisterRes(User user);
    LoginResponse userToLoginRes(User user);
}
