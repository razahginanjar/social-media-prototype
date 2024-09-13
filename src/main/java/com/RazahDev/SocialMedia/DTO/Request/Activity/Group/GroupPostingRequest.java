package com.RazahDev.SocialMedia.DTO.Request.Activity.Group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupPostingRequest {
    private String content;
}
