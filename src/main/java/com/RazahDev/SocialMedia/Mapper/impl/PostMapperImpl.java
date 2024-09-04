package com.RazahDev.SocialMedia.Mapper.impl;

import com.RazahDev.SocialMedia.DTO.Response.Activity.ClearPostResponse;
import com.RazahDev.SocialMedia.Entities.Group;
import com.RazahDev.SocialMedia.Entities.Page;
import com.RazahDev.SocialMedia.Entities.Post;
import com.RazahDev.SocialMedia.Entities.User;
import com.RazahDev.SocialMedia.Mapper.PostMapper;
import org.springframework.stereotype.Service;

@Service
public class PostMapperImpl implements PostMapper {
    @Override
    public ClearPostResponse toClearReponse(Post post, User user) {
        return ClearPostResponse.builder()
                .content(post.getPost())
                .updatedAt(post.getUpdatedAt())
                .name(user.getUsername())
                .totalLikes((long) post.getLikesUsers().size())
                .totalComments(post.getCommentUsers().size())
                .build();
    }

    @Override
    public ClearPostResponse toClearReponse(Post post, User user, Page page) {
        return ClearPostResponse.builder()
                .content(post.getPost())
                .page(page.getName())
                .updatedAt(post.getUpdatedAt())
                .name(user.getUsername())
                .totalLikes((long) post.getLikesUsers().size())
                .totalComments(post.getCommentUsers().size())
                .build();
    }

    @Override
    public ClearPostResponse toClearReponse(Post post, User user, Group group) {
        return ClearPostResponse.builder()
                .content(post.getPost())
                .nameGroup(group.getName())
                .updatedAt(post.getUpdatedAt())
                .name(user.getUsername())
                .totalLikes((long) post.getLikesUsers().size())
                .totalComments(post.getCommentUsers().size())
                .build();
    }
}
