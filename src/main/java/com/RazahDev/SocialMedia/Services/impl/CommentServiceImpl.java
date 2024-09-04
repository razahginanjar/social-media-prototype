package com.RazahDev.SocialMedia.Services.impl;

import com.RazahDev.SocialMedia.DTO.Request.Activity.Group.CommentGroupRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Page.CommentPageRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.User.CommentRequest;
import com.RazahDev.SocialMedia.DTO.Response.Activity.ClearCommentResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.CommentGroupResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.CommentPageResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.CommentResponse;
import com.RazahDev.SocialMedia.Entities.*;
import com.RazahDev.SocialMedia.Mapper.impl.CommentMapperImpl;
import com.RazahDev.SocialMedia.Repository.CommentUserRepository;
import com.RazahDev.SocialMedia.Services.CommentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentUserService {
    private final UserServiceImpl userService;
    private final CommentUserRepository commentUserRepository;
    private final CommentMapperImpl commentMapper;
    private final LikesServiceImpl likesServiceImpl;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommentResponse create(CommentRequest request, Post post) {
        User byContext = userService.getByContext();
        CommentUser build = CommentUser.builder()
                .comment(request.getComment())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .userComment(byContext)
                .postComment(post)
                .build();

        CommentUser commentUser = commentUserRepository.saveAndFlush(build);

        return commentMapper.toResponse(commentUser);
    }
    @Transactional(rollbackFor = Exception.class)
    public CommentPageResponse create(CommentPageRequest request, Post post, Page page) {
        CommentUser build = CommentUser.builder()
                .comment(request.getComment())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .pageComment(page)
                .userComment(page.getUsers())
                .postComment(post)
                .build();

        CommentUser commentUser = commentUserRepository.saveAndFlush(build);

        return commentMapper.toResponse(commentUser, page);
    }
    @Transactional(rollbackFor = Exception.class)
    public CommentGroupResponse create(CommentGroupRequest request, Post post, Group group) {
        User byContext = userService.getByContext();
        CommentUser build = CommentUser.builder()
                .groupComment(group)
                .comment(request.getComment())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .userComment(byContext)
                .postComment(post)
                .build();

        CommentUser commentUser = commentUserRepository.saveAndFlush(build);

        return commentMapper.toResponse(commentUser, group);
    }
    @Transactional(readOnly = true)
    @Override
    public List<ClearCommentResponse> getAllCommentFromPost(Post post) {
        List<CommentUser> allByPost = commentUserRepository.findAllByPostComment(post);
        List<ClearCommentResponse> result = new ArrayList<>();
        for (CommentUser commentUser : allByPost) {
            ClearCommentResponse clearComment = commentMapper.toClearComment(commentUser);
            result.add(clearComment);
        }
        return result;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ClearCommentResponse updateComment( Post post, String idComments, CommentRequest commentRequest) {
        User byContext = userService.getByContext();
        List<CommentUser> allByPost = commentUserRepository.findAllByPostComment(post);
        for (CommentUser commentUser : allByPost) {
            if(commentUser.getId().equals(idComments))
            {
                CommentUser commentUser1 = commentUserRepository.findById(idComments).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "comment is not found")
                );
                if(!commentUser.getUserComment().getId().equals(byContext.getId()))
                {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Comment is not yours");
                }
                commentUser1.setComment(commentRequest.getComment());
                commentUser1.setUpdatedAt(Instant.now());
                CommentUser commentUser2 = commentUserRepository.saveAndFlush(commentUser1);
                return commentMapper.toClearComment(commentUser2);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comment is not found");
    }
    @Transactional(rollbackFor = Exception.class)
    public ClearCommentResponse updateComment( Post post, String idComments, CommentGroupRequest commentRequest, Group group) {
        User byContext = userService.getByContext();
        List<CommentUser> allByPost = commentUserRepository.findAllByPostComment(post);
        for (CommentUser commentUser : allByPost) {
            if(commentUser.getId().equals(idComments))
            {
                CommentUser commentUser1 = commentUserRepository.findById(idComments).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "comment is not found")
                );
                if(!commentUser.getUserComment().getId().equals(byContext.getId()))
                {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Comment is not yours");
                }
                commentUser1.setComment(commentRequest.getComment());
                commentUser1.setUpdatedAt(Instant.now());
                CommentUser commentUser2 = commentUserRepository.saveAndFlush(commentUser1);
                return commentMapper.toClearComment(commentUser2, group);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comment is not found");
    }
    @Transactional(rollbackFor = Exception.class)
    public CommentUser updateAnyCommentFromPost(CommentUser commentUser)
    {
        return commentUserRepository.saveAndFlush(commentUser);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteComment(Post post, String idComments) {
        User byContext = userService.getByContext();
        List<CommentUser> allByPost = commentUserRepository.findAllByPostComment(post);
        for (CommentUser commentUser : allByPost) {
            if(commentUser.getId().equals(idComments))
            {
                CommentUser commentUser1 = commentUserRepository.findById(idComments).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "comment is not found")
                );
                if(!commentUser.getUserComment().getId().equals(byContext.getId()))
                {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Comment is not yours");
                }
                if(commentUser1.getGroupComment()!= null)
                {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't delete from this path");
                }
                likesServiceImpl.removeAllLikesFromComment(commentUser1);
                commentUserRepository.delete(commentUser1);
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comment is not found");
    }
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Post post, String idComments, Group group) {
        User byContext = userService.getByContext();
        List<CommentUser> allByPost = commentUserRepository.findAllByPostComment(post);
        for (CommentUser commentUser : allByPost) {
            if(commentUser.getId().equals(idComments))
            {
                CommentUser commentUser1 = commentUserRepository.findById(idComments).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "comment is not found")
                );
                if(!commentUser.getUserComment().getId().equals(byContext.getId()))
                {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The Comment is not yours");
                }
                if(commentUser1.getGroupComment().getId().equals(group.getId()))
                {
                    likesServiceImpl.removeAllLikesFromComment(commentUser1);
                    commentUserRepository.delete(commentUser1);
                    return;
                }
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't delete from this path");
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comment is not found");
    }
    @Transactional(readOnly = true)
    public CommentUser getAnyCommentInPost( Post post, String idComments)
    {
        List<CommentUser> allByPost = commentUserRepository.findAllByPostComment(post);
        for (CommentUser commentUser : allByPost) {
            if(commentUser.getId().equals(idComments))
            {
                return commentUserRepository.findById(idComments).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "comment is not found")
                );
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comment is not found");
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteAllCommentFromPost(Post post)
    {
        List<CommentUser> allByPost = commentUserRepository.findAllByPostComment(post);
        for (CommentUser commentUser : allByPost) {
            likesServiceImpl.removeAllLikesFromComment(commentUser);
        }
        deleteCommentBulk(allByPost);
    }
    @Transactional(rollbackFor = Exception.class)
    public void deleteCommentBulk(List<CommentUser> commentUsers)
    {
        commentUserRepository.deleteAll(commentUsers);
    }
}
