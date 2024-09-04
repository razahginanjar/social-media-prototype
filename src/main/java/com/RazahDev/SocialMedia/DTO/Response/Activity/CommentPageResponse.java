package com.RazahDev.SocialMedia.DTO.Response.Activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class CommentPageResponse {
    private String comment;
    private String page;
    private Instant createdAt;

}
