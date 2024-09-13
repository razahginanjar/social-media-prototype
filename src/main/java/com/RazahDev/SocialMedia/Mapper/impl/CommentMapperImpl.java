package com.RazahDev.SocialMedia.Mapper.impl;

import com.RazahDev.SocialMedia.DTO.Response.Activity.ClearCommentResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.CommentGroupResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.CommentPageResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.CommentResponse;
import com.RazahDev.SocialMedia.Entities.CommentUser;
import com.RazahDev.SocialMedia.Entities.Group;
import com.RazahDev.SocialMedia.Entities.Page;
import com.RazahDev.SocialMedia.Mapper.CommentMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentMapperImpl implements CommentMapper {
    @Override
    public CommentResponse toResponse(CommentUser commentUser) {
        return CommentResponse.builder()
                .comment(commentUser.getComment())
                .name(commentUser.getUserComment().getUsername())
                .createdAt(commentUser.getCreatedAt())
                .build();
    }

    @Override
    public CommentPageResponse toResponse(CommentUser commentUser, Page page) {
        return CommentPageResponse.builder()
                .comment(commentUser.getComment())
                .page(page.getName())
                .createdAt(commentUser.getCreatedAt())
                .build();
    }

    @Override
    public CommentGroupResponse toResponse(CommentUser commentUser, Group group) {
        return CommentGroupResponse.builder()
                .comment(commentUser.getComment())
                .groupName(group.getName())
                .createdAt(commentUser.getCreatedAt())
                .build();
    }

    @Override
    public ClearCommentResponse toClearComment(CommentUser commentUser) {
        return ClearCommentResponse.builder()
                .comment(commentUser.getComment())
                .name(commentUser.getUserComment().getUsername())
                .updatedAt(commentUser.getUpdatedAt())
                .totalLikes((long) commentUser.getLikesCommentUsers().size())
                .build();
    }

    @Override
    public ClearCommentResponse toClearComment(CommentUser commentUser, Page page) {
        return ClearCommentResponse.builder()
                .comment(commentUser.getComment())
                .name(commentUser.getUserComment().getUsername())
                .pageName(page.getName())
                .updatedAt(commentUser.getUpdatedAt())
                .totalLikes((long) commentUser.getLikesCommentUsers().size())
                .build();
    }

    @Override
    public ClearCommentResponse toClearComment(CommentUser commentUser, Group group) {
        return ClearCommentResponse.builder()
                .comment(commentUser.getComment())
                .name(commentUser.getUserComment().getUsername())
                .groupName(group.getName())
                .updatedAt(commentUser.getUpdatedAt())
                .totalLikes((long) commentUser.getLikesCommentUsers().size())
                .build();
    }
}
