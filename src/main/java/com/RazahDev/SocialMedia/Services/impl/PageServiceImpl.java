package com.RazahDev.SocialMedia.Services.impl;

import com.RazahDev.SocialMedia.DTO.Request.Activity.Page.CommentPageRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Page.CreatePageRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Page.LikesPostRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Page.PostingPageRequest;
import com.RazahDev.SocialMedia.DTO.Response.Activity.ClearCommentResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.ClearPostResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.CommentPageResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.PostPageResponse;
import com.RazahDev.SocialMedia.Entities.CommentUser;
import com.RazahDev.SocialMedia.Entities.Page;
import com.RazahDev.SocialMedia.Entities.Post;
import com.RazahDev.SocialMedia.Entities.User;
import com.RazahDev.SocialMedia.Mapper.impl.CommentMapperImpl;
import com.RazahDev.SocialMedia.Repository.PageRepository;
import com.RazahDev.SocialMedia.Services.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PageServiceImpl implements PageService {
    private final UserServiceImpl userService;
    private final PostServiceImpl postService;
    private final CommentServiceImpl commentService;
    private final PageRepository pageRepository;
    private final FollowerPageServiceImpl followerPageService;
    private final CommentMapperImpl commentMapperImpl;
    private final LikesServiceImpl likesServiceImpl;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addFollower(String idPage) {
        User byContext = userService.getByContext();
        Page pageIsNotFound = pageRepository.findById(idPage).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Page is not found")
        );
        followerPageService.addFollower(byContext, pageIsNotFound);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeFollower(String idPage) {
        User byContext = userService.getByContext();
        Page pageIsNotFound = pageRepository.findById(idPage).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Page is not found")
        );
        followerPageService.removeFollower(byContext, pageIsNotFound);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void create(CreatePageRequest request) {
        User byContext = userService.getByContext();
        Page page = new Page();
        page.setCreatedAt(Instant.now());

        if(pageRepository.existsByName(request.getName()))
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Page is already exist!");
        }
        page.setName(request.getName());
        page.setDescription(request.getDescription());
        page.setUsers(byContext);

        pageRepository.saveAndFlush(page);
    }
    @Transactional(readOnly = true)
    public Page validationAuthoritize(String idPage)
    {
        User byContext = userService.getByContext();
        Page pageIsNotFound = pageRepository.findById(idPage).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Page is not found")
        );
        if(!pageIsNotFound.getUsers().getId().equals(byContext.getId()))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Page is not yours");
        }
        return pageIsNotFound;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public PostPageResponse post(String idPage, PostingPageRequest postingPageRequest) {
        Page page = validationAuthoritize(idPage);
        return postService.create(postingPageRequest, page);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ClearPostResponse updatePost(String idPage, String idPost, PostingPageRequest postingPageRequest) {
        Page page = validationAuthoritize(idPage);
        Post post = postService.getPost(idPost);
        if(post.getPagePost().getId().equals(idPage))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Post is not from the page");
        }
        post.setPost(postingPageRequest.getContent());
        return postService.update(idPost, page, postingPageRequest);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePost(String idPage, String idPost) {
        Page page = validationAuthoritize(idPage);
        Post post = postService.getPost(idPost);
        if(post.getPagePost().getId().equals(page.getId()))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Post is not from the page");
        }
        postService.removePost(idPost, page);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommentPageResponse comment(String idPage, String idPost, CommentPageRequest request) {
        Page page = validationAuthoritize(idPage);
        Post post = postService.getPost(idPost);
        if(!post.getPagePost().getId().equals(page.getId()))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Post is not from the page");
        }
        return commentService.create(request, post, page);
    }
    @Transactional(readOnly = true)
    public CommentUser validationRemoUpdaComment(String idPage, String idPost, String idComment)
    {
        Page page = validationAuthoritize(idPage);
        Post post = postService.getPost(idPost);
        if(!post.getPagePost().getId().equals(page.getId()))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Post is not from the page");
        }
        CommentUser anyCommentInPost = commentService.getAnyCommentInPost(post, idComment);
        if(!anyCommentInPost.getPageComment().getId().equals(page.getId()))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Comment is not from this page");
        }
        return anyCommentInPost;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ClearCommentResponse updateComment(String idPage, String idPost, String idComment, CommentPageRequest request) {
        CommentUser anyCommentInPost = validationRemoUpdaComment(idPage, idPost, idComment);
        anyCommentInPost.setUpdatedAt(Instant.now());
        anyCommentInPost.setComment(request.getComment());
        CommentUser commentUser = commentService.updateAnyCommentFromPost(anyCommentInPost);
        return commentMapperImpl.toClearComment(commentUser, pageRepository.findById(idPage).get());
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteComment(String idPage, String idPost, String idComment) {
        validationRemoUpdaComment(idPage, idPost, idComment);
        Post post = postService.getPost(idPost);
        commentService.deleteComment(post, idComment);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void likePost(String idPage, String idPost, LikesPostRequest likesPostRequest) {
        Page page = validationAuthoritize(idPage);
        Post post = postService.getPost(idPost);
        if(!post.getPagePost().getId().equals(page.getId()))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Post is not from the page");
        }
        likesServiceImpl.likingPost(post, likesPostRequest, page);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unlikePost(String idPage, String idPost, String idLikes) {
        Page page = validationAuthoritize(idPage);
        Post post = postService.getPost(idPost);
        if(!post.getPagePost().getId().equals(page.getId()))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Post is not from the page");
        }
        likesServiceImpl.removeLikePost(post, idLikes, page);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void likingComment(String idPage, String idPost, String idComment, LikesPostRequest likesPostRequest) {
        Page page = validationAuthoritize(idPage);
        Post post = postService.getPost(idPost);
        if(!post.getPagePost().getId().equals(page.getId()))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Post is not from the page");
        }
        CommentUser anyCommentInPost = commentService.getAnyCommentInPost(post, idComment);
        likesServiceImpl.likingComment(post, anyCommentInPost, likesPostRequest, page);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unlikingComment(String idPage, String idPost, String idComment, String idLikes) {
        Page page = validationAuthoritize(idPage);
        Post post = postService.getPost(idPost);
        if(!post.getPagePost().getId().equals(page.getId()))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Post is not from the page");
        }
        CommentUser anyCommentInPost = commentService.getAnyCommentInPost(post, idComment);
        likesServiceImpl.removeLikeComment(post, anyCommentInPost, idLikes, page);
    }
    @Transactional(readOnly = true)
    @Override
    public List<ClearPostResponse> getAllPosts(String idPage) {
        Page page1 = pageRepository.findById(idPage).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Page is not found")
        );
        return postService.getAllPostFromPage(page1);
    }


}
