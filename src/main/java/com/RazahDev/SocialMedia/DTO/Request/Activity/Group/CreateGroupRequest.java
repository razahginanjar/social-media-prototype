package com.RazahDev.SocialMedia.DTO.Request.Activity.Group;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupRequest {
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String description;
}
