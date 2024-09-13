package com.RazahDev.SocialMedia.Services;

import com.RazahDev.SocialMedia.DTO.Request.Activity.User.LikesPostRequest;
import com.RazahDev.SocialMedia.Entities.CommentUser;
import com.RazahDev.SocialMedia.Entities.Post;

public interface LikesUserService {
    void likingPost(Post post, LikesPostRequest postRequest);

    void removeLikePost(Post post, String idLikes);

    void likingComment(Post post, CommentUser anyCommentInPost, LikesPostRequest postRequest);

    void removeLikeComment(Post post, CommentUser anyCommentInPost, String idLikes);
}
