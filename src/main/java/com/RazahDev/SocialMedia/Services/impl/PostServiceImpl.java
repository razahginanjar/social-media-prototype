package com.RazahDev.SocialMedia.Services.impl;

import com.RazahDev.SocialMedia.DTO.Request.Activity.Group.GroupPostingRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Page.PostingPageRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.User.PostingRequest;
import com.RazahDev.SocialMedia.DTO.Response.Activity.ClearPostResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.GroupPostResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.PostPageResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.PostResponse;
import com.RazahDev.SocialMedia.Entities.*;
import com.RazahDev.SocialMedia.Mapper.impl.PostMapperImpl;
import com.RazahDev.SocialMedia.Repository.PostUserRepository;
import com.RazahDev.SocialMedia.Services.PostUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.random.RandomGenerator;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostUserService {
    private final UserServiceImpl userService;
    private final PostUserRepository postUserRepository;
    private final FollowerPageServiceImpl followerPageService;
    private final FriendListServiceImpl friendListService;
    private final PostMapperImpl postMapper;
    private final LikesServiceImpl likesServiceImpl;
    private final CommentServiceImpl commentServiceImpl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PostResponse create(PostingRequest postingRequest) {
        User byContext = userService.getByContext();
        Post post = Post.builder()
                .post(postingRequest.getContent())
                .updatedAt(Instant.now())
                .createdAt(Instant.now())
                .userPost(byContext)
                .build();
        postUserRepository.saveAndFlush(post);
        return PostResponse.builder()
                .content(post.getPost())
                .created(post.getCreatedAt())
                .name(byContext.getUsername())
                .build();
    }
    @Transactional(rollbackFor = Exception.class)
    public PostPageResponse create(PostingPageRequest postingPageRequest, Page page)
    {
        User byContext = userService.getByContext();
        Post post = Post.builder()
                .post(postingPageRequest.getContent())
                .updatedAt(Instant.now())
                .createdAt(Instant.now())
                .userPost(byContext)
                .pagePost(page)
                .build();
        postUserRepository.saveAndFlush(post);
        return PostPageResponse.builder()
                .created(post.getCreatedAt())
                .pageName(page.getName())
                .content(post.getPost())
                .build();
    }
    @Transactional(rollbackFor = Exception.class)
    public GroupPostResponse create(GroupPostingRequest postingRequest, Group group) {
        User byContext = userService.getByContext();
        Post post = Post.builder()
                .post(postingRequest.getContent())
                .updatedAt(Instant.now())
                .createdAt(Instant.now())
                .groupPost(group)
                .userPost(byContext)
                .build();
        Post post1 = postUserRepository.saveAndFlush(post);
        return GroupPostResponse.builder()
                .created(post1.getCreatedAt())
                .content(post.getPost())
                .name(byContext.getUsername())
                .groupName(group.getName())
                .build();
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public PostResponse update(String idPost, PostingRequest postingRequest) {
        User byContext = userService.getByContext();
        Post post = postUserRepository.findById(idPost).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post do not exist!")
        );
        if(!post.getUserPost().getId().equals(byContext.getId()))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This Post is not yours!");
        }
        post.setPost(postingRequest.getContent());
        post.setUpdatedAt(Instant.now());
        Post post1 = postUserRepository.saveAndFlush(post);
        return PostResponse.builder()
                .name(byContext.getUsername())
                .created(post1.getCreatedAt())
                .content(post1.getPost())
                .build();
    }
    @Transactional(rollbackFor = Exception.class)
    public ClearPostResponse update(String idPost, GroupPostingRequest postingRequest, Group group) {
        User byContext = userService.getByContext();
        Post post = postUserRepository.findById(idPost).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post do not exist!")
        );
        if(!post.getUserPost().getId().equals(byContext.getId()))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This Post is not yours!");
        }
        post.setPost(postingRequest.getContent());
        post.setUpdatedAt(Instant.now());
        Post post1 = postUserRepository.saveAndFlush(post);
        return postMapper.toClearReponse(post1, byContext, group);
    }
    @Transactional(rollbackFor = Exception.class)
    public ClearPostResponse update(String idPost, Page page, PostingPageRequest postingPageRequest) {
        Post post = postUserRepository.findById(idPost).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post do not exist!")
        );
        post.setPost(postingPageRequest.getContent());
        post.setUpdatedAt(Instant.now());
        Post post1 = postUserRepository.saveAndFlush(post);
        return postMapper.toClearReponse(post1, page.getUsers(), page);
    }
    @Transactional(readOnly = true)
    @Override
    public ClearPostResponse getSpesificPost(String idPost) {
        User byContext = userService.getByContext();
        Post post = postUserRepository.findById(idPost).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post do not exist!")
        );
        List<FollowerPage> allFollowPage = followerPageService.getAllFollowPage(byContext);
        for (FollowerPage followerPage : allFollowPage) {
            if(post.getPagePost().getId().equals(followerPage.getPage().getId()))
            {
                return postMapper.toClearReponse(post, followerPage.getPage().getUsers(), followerPage.getPage());
            }
        }
        List<FriendList> allFriends = friendListService.getAllFriends(byContext);
        for (FriendList friend : allFriends) {
            if(post.getUserPost().getId().equals(friend.getId()) && post.getGroupPost() == null)
            {
                return postMapper.toClearReponse(post, friend.getFriend());
            }
        }

        if(post.getUserPost().getId().equals(byContext.getId()))
        {
            return postMapper.toClearReponse(post, byContext);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, but you can't see the post!");
    }
    @Transactional
    public Post getPost(String idPost) {
        User byContext = userService.getByContext();
        Post post = postUserRepository.findById(idPost).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post do not exist!")
        );
        List<FollowerPage> allFollowPage = followerPageService.getAllFollowPage(byContext);
        for (FollowerPage followerPage : allFollowPage) {
            if(post.getPagePost().getId().equals(followerPage.getPage().getId()))
            {
                return post;
            }
        }
        List<FriendList> allFriends = friendListService.getAllFriends(byContext);
        for (FriendList friend : allFriends) {
            if(post.getUserPost().getId().equals(friend.getId()))
            {
                return post;
            }
        }
        if(post.getUserPost().getId().equals(byContext.getId()))
        {
            return post;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, but you can't see the post!");
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removePost(String idPost) {
        User byContext = userService.getByContext();
        Post post = postUserRepository.findById(idPost).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post do not exist!")
        );
        if(post.getUserPost().getId().equals(byContext.getId()) && post.getGroupPost() == null && post.getPagePost() == null)
        {
            likesServiceImpl.removeAllLikePostPage(post);
            commentServiceImpl.deleteAllCommentFromPost(post);
            postUserRepository.delete(post);
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This Post is not yours!");
    }
    @Transactional(rollbackFor = Exception.class)
    public void removePost(String idPost, Page page) {
        User byContext = userService.getByContext();
        Post post = postUserRepository.findById(idPost).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post do not exist!")
        );
        if(post.getUserPost().getId().equals(byContext.getId()) && post.getPagePost().getId().equals(page.getId()))
        {
            likesServiceImpl.removeAllLikePostPage(post);
            commentServiceImpl.deleteAllCommentFromPost(post);
            postUserRepository.delete(post);
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This Post is not yours!");

    }
    @Transactional(rollbackFor = Exception.class)
    public void removePost(String idPost, Group group) {
        User byContext = userService.getByContext();
        Post post = postUserRepository.findById(idPost).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post do not exist!")
        );
        if(post.getUserPost().getId().equals(byContext.getId()) && post.getGroupPost().getId().equals(group.getId()))
        {
            likesServiceImpl.removeAllLikePostPage(post);
            commentServiceImpl.deleteAllCommentFromPost(post);
            postUserRepository.delete(post);
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This Post is not yours!");

    }
    @Transactional(rollbackFor = Exception.class)
    public void removePostByGroup(Group group)
    {
        List<Post> allByGroupPost = postUserRepository.findAllByGroupPost(group);
        for (Post post : allByGroupPost) {
            removePost(post.getId(), group);
        }
    }
    @Transactional(readOnly = true)
    @Override
    public List<ClearPostResponse> getAllPostFromFriends() {
        User byContext = userService.getByContext();

        List<Post> allByUserPost = postUserRepository.findAllByUserPostAndPagePostIsNull(byContext);
        List<ClearPostResponse> list = allByUserPost.stream().map(
                post -> postMapper.toClearReponse(post, byContext)
        ).toList();
        List<ClearPostResponse> result = new ArrayList<>(list);
        List<FriendList> allFriends = friendListService.getAllFriends(byContext);
        for (FriendList friend : allFriends) {
            List<Post> postByFriends = postUserRepository.findAllByUserPostAndPagePostIsNull(friend.getFriend());
            List<ClearPostResponse> list1 = postByFriends.stream().map(
                    post -> postMapper.toClearReponse(post, friend.getFriend())
            ).toList();
            result.addAll(list1);
        }
        List<FollowerPage> allFollowPage = followerPageService.getAllFollowPage(byContext);
        for (FollowerPage followerPage : allFollowPage) {
            List<Post> postUsers = followerPage.getPage().getPostUsers();
            List<ClearPostResponse> list1 = postUsers.stream().map(
                    post -> postMapper.toClearReponse(post, followerPage.getPage().getUsers(), followerPage.getPage())
            ).toList();
            result.addAll(list1);
        }
        Collections.shuffle(result, RandomGenerator.getDefault());
        return result;
    }
    @Transactional(readOnly = true)
    @Override
    public List<ClearPostResponse> getAllPostFromPage(Page pageName) {
        List<Post> allByPagePost1 = postUserRepository.findAllByPagePost(pageName);
        List<ClearPostResponse> list = allByPagePost1.stream().map(
                post -> postMapper.toClearReponse(post, pageName.getUsers(), pageName)
        ).toList();
        return list;
    }
    @Transactional(readOnly = true)
    public List<ClearPostResponse> getAllPostInGroup(Group group)
    {
        List<Post> allByGroupPost = postUserRepository.findAllByGroupPost(group);
        User byContext = userService.getByContext();
        List<ClearPostResponse> list = allByGroupPost.stream().map(
                post -> postMapper.toClearReponse(post, byContext, group)
        ).toList();
        return list;
    }
}
