package com.RazahDev.SocialMedia.DTO.Response.Activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupPostResponse {
    private String content;
    private Instant created;
    private String groupName;
    private String name;
}
