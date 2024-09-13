package com.RazahDev.SocialMedia.DTO.Request.Activity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommentRequest {
    private String comment;
}
