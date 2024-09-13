package com.RazahDev.SocialMedia.DTO.Response.Activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClearCommentResponse {
    private String pageName;
    private String groupName;
    private String name;
    private String comment;
    private Long totalLikes;
    private Instant updatedAt;
}
