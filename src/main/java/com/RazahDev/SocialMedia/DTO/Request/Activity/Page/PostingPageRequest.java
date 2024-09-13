package com.RazahDev.SocialMedia.DTO.Request.Activity.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostingPageRequest {
    private String content;
}
