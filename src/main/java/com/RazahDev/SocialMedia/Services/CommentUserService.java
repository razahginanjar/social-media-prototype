package com.RazahDev.SocialMedia.Services;

import com.RazahDev.SocialMedia.DTO.Request.Activity.User.CommentRequest;
import com.RazahDev.SocialMedia.DTO.Response.Activity.ClearCommentResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.CommentResponse;
import com.RazahDev.SocialMedia.Entities.Post;

import java.util.List;

public interface CommentUserService {
    CommentResponse create(CommentRequest request,  Post post);

    List<ClearCommentResponse> getAllCommentFromPost(Post post);

    ClearCommentResponse updateComment(Post post, String idComments, CommentRequest commentRequest);

    void deleteComment(Post post, String idComments);
}
