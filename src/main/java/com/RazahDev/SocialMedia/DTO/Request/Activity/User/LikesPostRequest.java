package com.RazahDev.SocialMedia.DTO.Request.Activity.User;

import com.RazahDev.SocialMedia.Constant.LikesType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LikesPostRequest {
    private LikesType likesType;
}
