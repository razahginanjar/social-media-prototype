package com.RazahDev.SocialMedia.DTO.Request.Activity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostingRequest {
    private String content;
}
