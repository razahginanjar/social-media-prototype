package com.RazahDev.SocialMedia.Mapper;

import com.RazahDev.SocialMedia.DTO.Response.Activity.ClearPostResponse;
import com.RazahDev.SocialMedia.Entities.Group;
import com.RazahDev.SocialMedia.Entities.Page;
import com.RazahDev.SocialMedia.Entities.Post;
import com.RazahDev.SocialMedia.Entities.User;

public interface PostMapper {
    ClearPostResponse toClearReponse(Post post, User user);
    ClearPostResponse toClearReponse(Post post, User user, Page page);
    ClearPostResponse toClearReponse(Post post, User user, Group group);
}
