package com.RazahDev.SocialMedia.DTO.Request.Activity.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentPageRequest {
    String comment;
}
