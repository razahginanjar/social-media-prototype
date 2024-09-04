package com.RazahDev.SocialMedia.Services;

import com.RazahDev.SocialMedia.DTO.Request.Activity.Page.CommentPageRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Page.CreatePageRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Page.LikesPostRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Page.PostingPageRequest;
import com.RazahDev.SocialMedia.DTO.Response.Activity.ClearCommentResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.ClearPostResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.CommentPageResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.PostPageResponse;

import java.util.List;

public interface PageService {
    void addFollower(String idPage);

    void removeFollower(String idPage);

    void create(CreatePageRequest request);

    PostPageResponse post(String idPage, PostingPageRequest postingPageRequest);

    ClearPostResponse updatePost(String idPage, String idPost, PostingPageRequest postingPageRequest);

    void deletePost(String idPage, String idPost);

    CommentPageResponse comment(String idPage, String idPost, CommentPageRequest request);

    ClearCommentResponse updateComment(String idPage, String idPost, String idComment, CommentPageRequest request);

    void deleteComment(String idPage, String idPost, String idComment);

    void likePost(String idPage, String idPost, LikesPostRequest likesPostRequest);

    void unlikePost(String idPage, String idPost, String idLikes);

    void likingComment(String idPage, String idPost, String idComment, LikesPostRequest likesPostRequest);

    void unlikingComment(String idPage, String idPost, String idComment, String idLikes);

    List<ClearPostResponse> getAllPosts(String idPage);
}
