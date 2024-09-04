package com.RazahDev.SocialMedia.Services.impl;

import com.RazahDev.SocialMedia.DTO.Request.Activity.User.LikesPostRequest;
import com.RazahDev.SocialMedia.Entities.*;
import com.RazahDev.SocialMedia.Repository.LikesUserRepository;
import com.RazahDev.SocialMedia.Services.LikesUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesUserService {
    private final UserServiceImpl userService;
    private final LikesUserRepository likesUserRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void likingPost(Post post, LikesPostRequest postRequest) {
        User byContext = userService.getByContext();
        if(likesUserRepository.existsByPostLikeUserAndUserLikesAndPageLikesIsNull(post, byContext))
        {
            return;
        }
        LikesUser build = LikesUser.builder()
                .postLikeUser(post)
                .userLikes(byContext)
                .likesType(postRequest.getLikesType())
                .build();
        likesUserRepository.saveAndFlush(build);
    }
    @Transactional(rollbackFor = Exception.class)
    public void likingPost(Post post, com.RazahDev.SocialMedia.DTO.Request.Activity.Page.LikesPostRequest request, Page page) {
        User byContext = userService.getByContext();
        if(likesUserRepository.existsByPostLikeUserAndUserLikesAndPageLikes(post, byContext, page))
        {
            return;
        }
        LikesUser build = LikesUser.builder()
                .postLikeUser(post)
                .pageLikes(page)
                .userLikes(byContext)
                .likesType(request.getType())
                .build();
        likesUserRepository.saveAndFlush(build);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeLikePost(Post post, String idLikes) {
        LikesUser likeIsNotFound = validationRemoveLikes(idLikes);
        for (LikesUser likesUser : post.getLikesUsers()) {
            if(likesUser.getId().equals(idLikes) && likeIsNotFound.getCommentLikeUser() == null && likeIsNotFound.getPageLikes() == null)
            {
                likesUserRepository.delete(likeIsNotFound);
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "could not deleted like");
    }
    @Transactional(rollbackFor = Exception.class)
    public void removeLikePost(Post post, String idLikes, Page page) {
        LikesUser likeIsNotFound = validationRemoveLikes(idLikes);
        for (LikesUser likesUser : post.getLikesUsers()) {
            if(likesUser.getId().equals(idLikes) &&
                    likeIsNotFound.getCommentLikeUser() == null
                    && likeIsNotFound.getPageLikes().getId().equals(page.getId()))
            {
                likesUserRepository.delete(likeIsNotFound);
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "could not deleted like");
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void likingComment(Post post, CommentUser anyCommentInPost, LikesPostRequest postRequest) {
        User byContext = userService.getByContext();
        if(likesUserRepository.existsByCommentLikeUserAndUserLikesAndPageLikesIsNull(anyCommentInPost, byContext))
        {
            return;
        }
        LikesUser build = LikesUser.builder()
                .commentLikeUser(anyCommentInPost)
                .postLikeUser(post)
                .userLikes(byContext)
                .likesType(postRequest.getLikesType())
                .build();
        likesUserRepository.saveAndFlush(build);
    }
    @Transactional(rollbackFor = Exception.class)
    public void likingComment(Post post, CommentUser anyCommentInPost,
                              com.RazahDev.SocialMedia.DTO.Request.Activity.Page.LikesPostRequest postRequest, Page page) {
        User byContext = userService.getByContext();
        if(likesUserRepository.existsByCommentLikeUserAndUserLikesAndPageLikes(anyCommentInPost, byContext, page))
        {
            return;
        }
        LikesUser build = LikesUser.builder()
                .commentLikeUser(anyCommentInPost)
                .pageLikes(page)
                .postLikeUser(post)
                .userLikes(byContext)
                .likesType(postRequest.getType())
                .build();
        likesUserRepository.saveAndFlush(build);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeLikeComment(Post post, CommentUser anyCommentInPost, String idLikes) {
        LikesUser likeIsNotFound = validationRemoveLikes(idLikes);
        for (LikesUser likesUser : post.getLikesUsers()) {
            if(likesUser.getId().equals(idLikes)
                    && likeIsNotFound.getCommentLikeUser().getId().equals(anyCommentInPost.getId())
                    && likeIsNotFound.getPageLikes() == null)
            {
                likesUserRepository.delete(likeIsNotFound);
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "like is not found");
    }
    @Transactional(rollbackFor = Exception.class)
    public void removeLikeComment(Post post,CommentUser anyCommentInPost, String idLikes, Page page) {
        LikesUser likeIsNotFound = validationRemoveLikes(idLikes);
        for (LikesUser likesUser : post.getLikesUsers()) {
            if(likesUser.getId().equals(idLikes) &&
                    likeIsNotFound.getCommentLikeUser().getId().equals(anyCommentInPost.getId())
                    && likeIsNotFound.getPageLikes().getId().equals(page.getId()))
            {
                likesUserRepository.delete(likeIsNotFound);
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "like is not found");
    }
    @Transactional(rollbackFor = Exception.class)
    public void removeAllLikePostPage(Post post)
    {
        likesUserRepository.deleteAllByPostLikeUserAndCommentLikeUserIsNull(post);
    }
    @Transactional(rollbackFor = Exception.class)
    public void removeAllLikesFromComment(CommentUser commentUser)
    {
        likesUserRepository.deleteAllByCommentLikeUser(commentUser);
    }
    @Transactional(readOnly = true)
    public LikesUser validationRemoveLikes(String idLikes)
    {
        User byContext = userService.getByContext();
        LikesUser likeIsNotFound = likesUserRepository.findById(idLikes).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "like is not found")
        );

        if(!likeIsNotFound.getUserLikes().getId().equals(byContext.getId()))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "like is not yours");
        }
        return likeIsNotFound;
    }
}
