package com.RazahDev.SocialMedia.Controllers;


import com.RazahDev.SocialMedia.Constant.APIUrl;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Group.CommentGroupRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Group.GaveRoleRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Group.GroupPostingRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.User.LikesPostRequest;
import com.RazahDev.SocialMedia.DTO.Response.Activity.ClearCommentResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.ClearPostResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.CommentGroupResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.GroupPostResponse;
import com.RazahDev.SocialMedia.DTO.Response.CommonResponse;
import com.RazahDev.SocialMedia.Services.impl.GroupServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = APIUrl.GROUP_API)
@RequiredArgsConstructor
public class GroupController {
    private final GroupServiceImpl groupService;

    @PostMapping(
            path = "/post",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<GroupPostResponse>> postingGroup(
            @PathVariable("id_group")String idGroup,
            @RequestBody GroupPostingRequest request
            )
    {
        GroupPostResponse post = groupService.post(idGroup, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.<GroupPostResponse>builder()
                        .data(post)
                        .message("Successfully post in a group")
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }
    @PatchMapping(
            path = "/post/{id_post}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<ClearPostResponse>> UpdatePosting(
            @PathVariable("id_group")String idGroup,
            @PathVariable("id_post")String idPost,
            @RequestBody GroupPostingRequest request
    )
    {
        ClearPostResponse response = groupService.updatePost(idGroup, idPost, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.<ClearPostResponse>builder()
                        .data(response)
                        .message("Successfully update post in a group")
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }
    @DeleteMapping(
            path = "/post/{id_post}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> DeletePosting(
            @PathVariable("id_group")String idGroup,
            @PathVariable("id_post")String idPost)
    {
        groupService.deletePost(idGroup, idPost);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully deleted post")
                        .data("Success")
                        .build()
        );
    }

    @PostMapping(
            path = "/post/{id_post}/comment",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<CommentGroupResponse>> createComment(
            @PathVariable("id_group")String idGroup,
            @PathVariable("id_post")String idPost,
            @RequestBody CommentGroupRequest request
            )
    {
        CommentGroupResponse response = groupService.createComment(idGroup, idPost, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.<CommentGroupResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully comment in a post")
                        .data(response)
                        .build()
        );
    }
    @PatchMapping(
            path = "/post/{id_post}/comment/{id_comments}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<ClearCommentResponse>> updateComment(
            @PathVariable("id_group")String idGroup,
            @PathVariable("id_post")String idPost,
            @PathVariable("id_comments")String idComments,
            @RequestBody CommentGroupRequest request
    )
    {
        ClearCommentResponse response = groupService.updateComment(idGroup, idPost, idComments, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<ClearCommentResponse >builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully deleted post")
                        .data(response)
                        .build()
        );
    }
    @DeleteMapping(
            path = "/post/{id_post}/comment/{id_comments}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> deleteComment(
            @PathVariable("id_group")String idGroup,
            @PathVariable("id_post")String idPost,
            @PathVariable("id_comments")String idComments
    )
    {
        groupService.deleteComment(idGroup, idPost, idComments);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully deleted post")
                        .data("Success")
                        .build()
        );
    }


    @PostMapping(
            path = "/post/{id_post}/likes",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> likingPosting(
            @PathVariable("id_group")String idGroup,
            @PathVariable("id_post")String idPost,
            @RequestBody LikesPostRequest likesPostRequest
    )
    {
        groupService.likingPost(idGroup, idPost, likesPostRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully liking post")
                        .data("Success")
                        .build()
        );
    }
    @DeleteMapping(
            path = "/post/{id_post}/likes/{id_likes}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> unlikingPosting(
            @PathVariable("id_group")String idGroup,
            @PathVariable("id_post")String idPost,
            @PathVariable("id_likes")String idLikes
    )
    {
        groupService.unLikingPost(idGroup, idPost, idLikes);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully unliking post")
                        .data("Success")
                        .build()
        );
    }

    @PostMapping(
            path = "/post/{id_post}/comment/{id_comment}/likes",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> likingComment(
            @PathVariable("id_group")String idGroup,
            @PathVariable("id_post")String idPost,
            @PathVariable("id_comment")String idComment,
            @RequestBody LikesPostRequest likesPostRequest
    )
    {
        groupService.likingComment(idGroup, idPost, idComment, likesPostRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully liking comment")
                        .data("Success")
                        .build()
        );
    }
    @DeleteMapping(
            path = "/post/{id_post}/comment/{id_comment}/likes/{id_likes}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> unlikingComment(
            @PathVariable("id_group")String idGroup,
            @PathVariable("id_post")String idPost,
            @PathVariable("id_comment")String idComment,
            @PathVariable("id_likes")String idLikes
    )
    {
        groupService.unlikingComment(idGroup, idPost, idComment, idLikes);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully liking comment")
                        .data("Success")
                        .build()
        );
    }

    @DeleteMapping(
            path = "/member/{id_user}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> removeMember(
            @PathVariable("id_group")String idGroup,
            @PathVariable("id_user") String idUser
            )
    {
        groupService.removeMember(idGroup, idUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully liking comment")
                        .data("Success")
                        .build()
        );
    }

    @PatchMapping(
            path = "/role",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> gaveAnotherRole(
            @PathVariable("id_group")String idGroup,
            @RequestBody GaveRoleRequest request
            )
    {
        groupService.gaveAnotherRole(idGroup, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully liking comment")
                        .data("Success")
                        .build()
        );
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> homePage(
            @PathVariable("id_group")String idGroup)
    {
        List<ClearPostResponse> response = groupService.getPosts(idGroup);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<List<ClearPostResponse>>builder()
                        .data(response)
                        .message("Success")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
}
