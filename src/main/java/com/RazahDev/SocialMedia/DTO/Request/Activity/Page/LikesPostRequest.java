package com.RazahDev.SocialMedia.DTO.Request.Activity.Page;

import com.RazahDev.SocialMedia.Constant.LikesType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesPostRequest {
    private LikesType type;
}
