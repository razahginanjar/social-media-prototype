package com.RazahDev.SocialMedia.DTO.Response.Activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClearPostResponse {
    private String content;
    private Instant updatedAt;
    private String nameGroup;
    private String name;
    private String page;
    private Long totalLikes;
    private Integer totalComments;
}
