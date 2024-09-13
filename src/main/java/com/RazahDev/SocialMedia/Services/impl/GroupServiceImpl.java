package com.RazahDev.SocialMedia.Services.impl;

import com.RazahDev.SocialMedia.DTO.Request.Activity.Group.CommentGroupRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Group.CreateGroupRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Group.GaveRoleRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Group.GroupPostingRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.User.LikesPostRequest;
import com.RazahDev.SocialMedia.DTO.Response.Activity.*;
import com.RazahDev.SocialMedia.Entities.CommentUser;
import com.RazahDev.SocialMedia.Entities.Group;
import com.RazahDev.SocialMedia.Entities.Post;
import com.RazahDev.SocialMedia.Entities.User;
import com.RazahDev.SocialMedia.Mapper.impl.GroupMapperImpl;
import com.RazahDev.SocialMedia.Repository.GroupRepository;
import com.RazahDev.SocialMedia.Services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupMapperImpl groupMapper;
    private final UserServiceImpl userService;
    private final PostServiceImpl postService;
    private final CommentServiceImpl commentService;
    private final GroupRepository groupRepository;
    private final MemberDetailServiceImpl memberDetailService;
    private final LikesServiceImpl likesServiceImpl;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CreateGroupResponse create(CreateGroupRequest createGroupRequest) {
        User byContext = userService.getByContext();
        Group group = new Group();
        group.setName(createGroupRequest.getName());
        group.setDescription(createGroupRequest.getDescription());
        group.setCreatedAt(Instant.now());
        group.setLink(UUID.randomUUID().toString());

        Group group1 = groupRepository.saveAndFlush(group);
        memberDetailService.addCreator(byContext, group1);
        return groupMapper.toResponseCreate(group1);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteGroup(String idGroup) {
        User byContext = userService.getByContext();
        Group group = groupRepository.findById(idGroup).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group is not found")
        );
        if(!memberDetailService.isUserAnCreatorGroup(byContext, group))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You're not creator of this group");
        }
        postService.removePostByGroup(group);
        memberDetailService.deleteMemberByGroup(group);
        groupRepository.delete(group);
    }
    @Transactional(readOnly = true)
    public Group validateGroup(String idGroup)
    {
        User byContext = userService.getByContext();
        Group group = groupRepository.findById(idGroup).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group is not found")
        );
        if(!memberDetailService.isUserMemberGroup(byContext, group))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You're not belong to this group");
        }
        return group;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public GroupPostResponse post(String idGroup, GroupPostingRequest request) {
        Group group = validateGroup(idGroup);
        return postService.create(request, group);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ClearPostResponse updatePost(String idGroup, String idPost, GroupPostingRequest request) {
        Group group = validateGroup(idGroup);
        return postService.update(idPost, request, group);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePost(String idGroup, String idPost) {
        Group group = validateGroup(idGroup);
        postService.removePost(idPost, group);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommentGroupResponse createComment(String idGroup, String idPost, CommentGroupRequest request) {
        Group group = validateGroup(idGroup);
        Post post = postService.getPost(idPost);
        if(!post.getGroupPost().getId().equals(group.getId()))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post is not in group");
        }
        return commentService.create(request, post, group);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ClearCommentResponse updateComment(String idGroup, String idPost, String idComments, CommentGroupRequest request) {
        Group group = validateGroup(idGroup);
        Post post = postService.getPost(idPost);
        if(!post.getGroupPost().getId().equals(group.getId()))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post is not in group");
        }
        CommentUser anyCommentInPost = commentService.getAnyCommentInPost(post, idComments);
        return commentService.updateComment(post, anyCommentInPost.getId(), request, group);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteComment(String idGroup, String idPost, String idComments) {
        Group group = validateGroup(idGroup);
        Post post = postService.getPost(idPost);
        if(!post.getGroupPost().getId().equals(group.getId()))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post is not in group");
        }
        CommentUser anyCommentInPost = commentService.getAnyCommentInPost(post, idComments);
        commentService.deleteComment(post, anyCommentInPost.getId(), group);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void likingPost(String idGroup, String idPost, LikesPostRequest likesPostRequest) {
        Group group = validateGroup(idGroup);
        Post post = postService.getPost(idPost);
        if(!post.getGroupPost().getId().equals(group.getId()))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post is not in group");
        }
        likesServiceImpl.likingPost(post, likesPostRequest);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unLikingPost(String idGroup, String idPost, String idLikes) {
        Group group = validateGroup(idGroup);
        Post post = postService.getPost(idPost);
        if(!post.getGroupPost().getId().equals(group.getId()))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post is not in group");
        }
        likesServiceImpl.removeLikePost(post, idLikes);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void likingComment(String idGroup, String idPost, String idComment, LikesPostRequest likesPostRequest) {
        Group group = validateGroup(idGroup);
        Post post = postService.getPost(idPost);
        if(!post.getGroupPost().getId().equals(group.getId()))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post is not in group");
        }
        CommentUser anyCommentInPost = commentService.getAnyCommentInPost(post, idComment);
        likesServiceImpl.likingComment(post, anyCommentInPost, likesPostRequest);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unlikingComment(String idGroup, String idPost, String idComment, String idLikes) {
        Group group = validateGroup(idGroup);
        Post post = postService.getPost(idPost);
        if(!post.getGroupPost().getId().equals(group.getId()))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post is not in group");
        }
        CommentUser anyCommentInPost = commentService.getAnyCommentInPost(post, idComment);
        likesServiceImpl.removeLikeComment(post, anyCommentInPost, idLikes);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeMember(String idGroup, String idUser) {
        Group group = validateGroup(idGroup);
        User byContext = userService.getByContext();
        User userByID = userService.getUserByID(idUser);
        if(!memberDetailService.isUserMemberGroup(userByID, group))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user is not member this group");
        }
        if(!memberDetailService.isUserAnAdminGroup(byContext, group))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You're not an admin");
        }
        memberDetailService.removeMember(userByID, group);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void gaveAnotherRole(String idGroup, GaveRoleRequest request) {
        Group group = validateGroup(idGroup);
        User byContext = userService.getByContext();
        User userByID = userService.getUserByID(request.getIdMember());
        if(!memberDetailService.isUserMemberGroup(userByID, group))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user is not member this group");
        }
        if(!memberDetailService.isUserAnAdminGroup(byContext, group))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You're not an admin");
        }
        memberDetailService.editRoleMember(userByID, group, request.getRoleGroup());
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void joinGroup(String idGroup) {
        User byContext = userService.getByContext();
        Group group = groupRepository.findById(idGroup).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group is not found")
        );
        memberDetailService.addMember(byContext, group);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void logout(String idGroup) {
        Group group = validateGroup(idGroup);
        User byContext = userService.getByContext();
        memberDetailService.removeMember(byContext, group);
    }
    @Transactional(readOnly = true)
    @Override
    public List<ClearPostResponse> getPosts(String idGroup) {
        Group group = validateGroup(idGroup);
        return postService.getAllPostInGroup(group);
    }
}
