package com.RazahDev.SocialMedia.Services;

import com.RazahDev.SocialMedia.DTO.Request.Activity.Group.CommentGroupRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Group.CreateGroupRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Group.GaveRoleRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Group.GroupPostingRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.User.LikesPostRequest;
import com.RazahDev.SocialMedia.DTO.Response.Activity.*;

import java.util.List;

public interface GroupService {
    CreateGroupResponse create(CreateGroupRequest createGroupRequest);

    void deleteGroup(String idGroup);

    GroupPostResponse post(String idGroup, GroupPostingRequest request);

    ClearPostResponse updatePost(String idGroup, String idPost, GroupPostingRequest request);

    void deletePost(String idGroup, String idPost);

    CommentGroupResponse createComment(String idGroup, String idPost, CommentGroupRequest request);

    ClearCommentResponse updateComment(String idGroup, String idPost, String idComments, CommentGroupRequest request);

    void deleteComment(String idGroup, String idPost, String idComments);

    void likingPost(String idGroup, String idPost,  LikesPostRequest likesPostRequest);

    void unLikingPost(String idGroup, String idPost, String idLikes);

    void likingComment(String idGroup, String idPost, String idComment, LikesPostRequest likesPostRequest);

    void unlikingComment(String idGroup, String idPost, String idComment, String idLikes);

    void removeMember(String idGroup, String idUser);

    void gaveAnotherRole(String idGroup, GaveRoleRequest request);

    void joinGroup(String idGroup);

    void logout(String idGroup);

    List<ClearPostResponse> getPosts(String idGroup);
}
