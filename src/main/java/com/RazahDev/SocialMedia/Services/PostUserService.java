package com.RazahDev.SocialMedia.Services;

import com.RazahDev.SocialMedia.DTO.Request.Activity.User.PostingRequest;
import com.RazahDev.SocialMedia.DTO.Response.Activity.ClearPostResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.PostResponse;
import com.RazahDev.SocialMedia.Entities.Page;

import java.util.List;

public interface PostUserService {
    PostResponse create(PostingRequest postingRequest);

    PostResponse update(String idPost, PostingRequest postingRequest);

    ClearPostResponse getSpesificPost(String idPost);

    void removePost(String idPost);

    List<ClearPostResponse> getAllPostFromFriends();
    List<ClearPostResponse> getAllPostFromPage(Page pageName);
}
