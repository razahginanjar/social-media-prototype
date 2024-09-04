package com.RazahDev.SocialMedia.Mapper;

import com.RazahDev.SocialMedia.DTO.Response.Activity.ClearCommentResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.CommentGroupResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.CommentPageResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.CommentResponse;
import com.RazahDev.SocialMedia.Entities.CommentUser;
import com.RazahDev.SocialMedia.Entities.Group;
import com.RazahDev.SocialMedia.Entities.Page;

public interface CommentMapper {
    CommentResponse toResponse(CommentUser commentUser);
    CommentPageResponse toResponse(CommentUser commentUser, Page page);
    CommentGroupResponse toResponse(CommentUser commentUser, Group group);
    ClearCommentResponse toClearComment(CommentUser commentUser);
    ClearCommentResponse toClearComment(CommentUser commentUser, Page page);
    ClearCommentResponse toClearComment(CommentUser commentUser, Group group);
}
