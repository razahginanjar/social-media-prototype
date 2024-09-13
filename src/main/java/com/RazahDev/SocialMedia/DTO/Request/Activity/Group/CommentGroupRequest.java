package com.RazahDev.SocialMedia.DTO.Request.Activity.Group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentGroupRequest {
    private String comment;
}
