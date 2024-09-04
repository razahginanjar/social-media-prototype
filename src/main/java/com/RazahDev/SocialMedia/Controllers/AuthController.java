package com.RazahDev.SocialMedia.Controllers;

import com.RazahDev.SocialMedia.Constant.APIUrl;
import com.RazahDev.SocialMedia.DTO.Request.Auth.LoginRequest;
import com.RazahDev.SocialMedia.DTO.Request.Auth.RegisterRequest;
import com.RazahDev.SocialMedia.DTO.Response.Auth.LoginResponse;
import com.RazahDev.SocialMedia.DTO.Response.Auth.RegisterResponse;
import com.RazahDev.SocialMedia.DTO.Response.CommonResponse;
import com.RazahDev.SocialMedia.Services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIUrl.AUTH_API)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/login")
    public ResponseEntity<CommonResponse<LoginResponse>> login(@RequestBody LoginRequest request)
    {
        LoginResponse login = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(
                        CommonResponse.<LoginResponse>builder()
                                .statusCode(HttpStatus.OK.value())
                                .data(login)
                                .message("Successfully login")
                                .build()
                );
    }

    @PostMapping(
            path = "/register",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<RegisterResponse>> registerUser(@RequestBody RegisterRequest request)
    {
        RegisterResponse register = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        CommonResponse.<RegisterResponse>builder()
                                .data(register)
                                .message("Successfully Registered an Account")
                                .statusCode(HttpStatus.CREATED.value())
                                .build()
                );
    }

}
