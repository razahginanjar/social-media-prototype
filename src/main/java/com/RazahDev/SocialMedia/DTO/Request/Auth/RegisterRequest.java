package com.RazahDev.SocialMedia.DTO.Request.Auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String password;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @Pattern(regexp = "^08\\d{9,11}$", message = "Nomor telepon hasus valid dan diawali dengan '08' diikuti oleh " +
            "9 hingga 11 angka.")
    private String phone;
}
