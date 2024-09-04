package com.RazahDev.SocialMedia.Controllers;


import com.RazahDev.SocialMedia.Constant.APIUrl;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Group.CreateGroupRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Page.CreatePageRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.User.CommentRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.User.LikesPostRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.User.PostingRequest;
import com.RazahDev.SocialMedia.DTO.Response.Activity.*;
import com.RazahDev.SocialMedia.DTO.Response.CommonResponse;
import com.RazahDev.SocialMedia.Entities.CommentUser;
import com.RazahDev.SocialMedia.Entities.Post;
import com.RazahDev.SocialMedia.Services.impl.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.USER_API)
public class UserController {

    private final GroupServiceImpl groupService;
    private final PageServiceImpl pageService;
    private final CommentServiceImpl commentService;
    private final FriendListServiceImpl friendListService;
    private final PostServiceImpl postService;
    private final LikesServiceImpl likesServiceImpl;

    @PostMapping(
            path = "/{id_user}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> addFriend(@PathVariable("id_user") String idFriend)
    {
        friendListService.addFriend(idFriend);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully made a friend")
                        .build()
        );
    }

    @DeleteMapping(
            path = "/{id_user}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> removeFriend(@PathVariable("id_user") String idFriend)
    {
        friendListService.removeFriend(idFriend);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully remove a friend")
                        .build()
        );
    }

    @GetMapping(
            path = "/friend-list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<List<FriendsResponse>>> getALlFriend()
    {
        List<FriendsResponse> friendsResponses = friendListService.seeAllFriend();
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<List<FriendsResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully remove a friend")
                        .data(friendsResponses)
                        .build()
        );
    }



    @PostMapping(
            path = "/post",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<PostResponse>> posting(@RequestBody PostingRequest postingRequest)
    {
        PostResponse response = postService.create(postingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.<PostResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully post something!")
                        .data(response)
                        .build()
        );
    }

    @PatchMapping(
            path = "/post/{id_post}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<PostResponse>> UpdatePosting(@PathVariable("id_post") String idPost,
                                                                      @RequestBody PostingRequest postingRequest)
    {
        PostResponse response = postService.update(idPost, postingRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<PostResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully updated post!")
                        .data(response)
                        .build()
        );
    }

    @GetMapping(
            path = "/post/{id_post}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<ClearPostResponse>> getSpecificPost(@PathVariable("id_post") String idPost)
    {
        ClearPostResponse postResponse = postService.getSpesificPost(idPost);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<ClearPostResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get post!")
                        .data(postResponse)
                        .build()
        );
    }

    @DeleteMapping(
            path = "/post/{id_post}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> DeletePosting(@PathVariable("id_post") String idPost
                                                                )
    {
        postService.removePost(idPost);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully remove a post")
                        .build()
        );
    }


    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<List<ClearPostResponse>>> getAllPostingFromFriends()
    {
        List<ClearPostResponse> allPostFromFriends = postService.getAllPostFromFriends();
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<List<ClearPostResponse>>builder()
                        .data(allPostFromFriends)
                        .message("Successfully get posts!")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @PostMapping(
            path = "/post/{id_post}/comment",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<CommentResponse>> createComment(
            @PathVariable("id_post") String idPost, @RequestBody CommentRequest request
            )
    {
        Post post = postService.getPost(idPost);
        CommentResponse response = commentService.create(request, post);
        return ResponseEntity.status(HttpStatus.CREATED).body(
          CommonResponse.<CommentResponse>builder()
                  .statusCode(HttpStatus.CREATED.value())
                  .data(response)
                  .message("Successfully commented in a post")
                  .build()
        );
    }

    @GetMapping(
            path = "/post/{id_post}/comment",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<List<ClearCommentResponse>>> getAllComment(@PathVariable("id_post")String idPost) {
        Post post = postService.getPost(idPost);
        List<ClearCommentResponse> responses = commentService.getAllCommentFromPost(post);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<List<ClearCommentResponse>>builder()
                        .message("Successfully get all comments from a post")
                        .data(responses)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PatchMapping(
            path = "/post/{id_post}/comment/{id_comments}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<ClearCommentResponse>> updateComment(
            @PathVariable("id_post")String idPost,
            @PathVariable("id_comments")String idComments,
            @RequestBody CommentRequest commentRequest
    ) {
        Post post = postService.getPost(idPost);
        ClearCommentResponse commentResponse = commentService.updateComment(post, idComments, commentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<ClearCommentResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .data(commentResponse)
                        .message("Successfully updated comments")
                        .build()
        );
    }

    @DeleteMapping(
            path = "/post/{id_post}/comment/{id_comments}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> deleteComment(@PathVariable("id_post")String idPost,
                                           @PathVariable("id_comments")String idComments)
    {
        Post post = postService.getPost(idPost);
        commentService.deleteComment(post, idComments);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.<String>builder()
                                .statusCode(HttpStatus.OK.value())
                                .data("Successfully removed comment")
                                .message("successful")
                                .build()
                );
    }


    @PostMapping(
            path = "/post/{id_post}/likes",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> likingPosting(@PathVariable("id_post")String idPost,
                                                                @RequestBody LikesPostRequest postRequest)
    {
        Post post = postService.getPost(idPost);
        likesServiceImpl.likingPost(post, postRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .message("Success")
                        .data("Successfully like a post")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
    @DeleteMapping(
            path = "/post/{id_post}/likes/{id_likes}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> unlikingPosting(@PathVariable("id_post")String idPost,
                                             @PathVariable("id_likes")String idLikes
    )
    {
        Post post = postService.getPost(idPost);
        likesServiceImpl.removeLikePost(post, idLikes);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .message("Success")
                        .data("Successfully unlike a post")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping(
            path = "/post/{id_post}/comment/{id_comment}/likes",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> likingComment(
            @PathVariable("id_post")String idPost,
            @PathVariable("id_comment")String idComment,
            @RequestBody LikesPostRequest postRequest
    ) {
        Post post = postService.getPost(idPost);
        CommentUser anyCommentInPost = commentService.getAnyCommentInPost(post, idComment);
        likesServiceImpl.likingComment(post, anyCommentInPost, postRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .message("Success")
                        .data("Successfully like a comment")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
    @DeleteMapping(
            path = "/post/{id_post}/comment/{id_comment}/likes/{id_likes}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> unlikingComment(
            @PathVariable("id_post")String idPost,
            @PathVariable("id_comment")String idComment,
            @PathVariable("id_likes")String idLikes

    )
    {
        Post post = postService.getPost(idPost);
        CommentUser anyCommentInPost = commentService.getAnyCommentInPost(post, idComment);
        likesServiceImpl.removeLikeComment(post, anyCommentInPost, idLikes);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .message("Success")
                        .data("Successfully like a post")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }


    @PostMapping(
            path = "/{id_page}/follow",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> followingPage(
            @PathVariable("id_page")String idPage
    ) {
        pageService.addFollower(idPage);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .data("Followed!")
                        .message("Successfully Follow Page!")
                        .build()
        );
    }
    @DeleteMapping(
            path = "/{id_page}/unfollow",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> unfollowPage(
            @PathVariable("id_page")String idPage
    )
    {
        pageService.removeFollower(idPage);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .data("Unfollowed!")
                        .message("Successfully Unfollow Page!")
                        .build()
        );
    }

    @PostMapping(
            path = "/group",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<CreateGroupResponse>> createGroup(
            @RequestBody CreateGroupRequest createGroupRequest)
    {
        CreateGroupResponse response = groupService.create(createGroupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.<CreateGroupResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .data(response)
                        .message("Successfully created group!")
                        .build()
        );
    }

    @DeleteMapping(
            path = "/group/{id_group}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> deleteGroup(@PathVariable("id_group")String idGroup)
    {
        groupService.deleteGroup(idGroup);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .data("Success")
                        .message("Successfully deleted group")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @DeleteMapping(
            path = "/group/{id_group}/logout",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> logoutGroup(@PathVariable("id_group")String idGroup)
    {
        groupService.logout(idGroup);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .data("Success")
                        .message("Successfully deleted group")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping(
            path = "/group/{id_group}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> joinGroup(@PathVariable("id_group")String idGroup)
    {
        groupService.joinGroup(idGroup);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .data("Success")
                        .message("Successfully joined group")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping(
            path = "/page",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> registerPage(@RequestBody CreatePageRequest request)
    {
        pageService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.<String>builder()
                        .data("Success")
                        .message("Successfully Created page")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

}
