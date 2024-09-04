package com.RazahDev.SocialMedia.Controllers;

import com.RazahDev.SocialMedia.Constant.APIUrl;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Page.CommentPageRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Page.LikesPostRequest;
import com.RazahDev.SocialMedia.DTO.Request.Activity.Page.PostingPageRequest;
import com.RazahDev.SocialMedia.DTO.Response.Activity.ClearCommentResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.ClearPostResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.CommentPageResponse;
import com.RazahDev.SocialMedia.DTO.Response.Activity.PostPageResponse;
import com.RazahDev.SocialMedia.DTO.Response.CommonResponse;
import com.RazahDev.SocialMedia.Services.impl.PageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.PAGE_API)
public class PageController {
    private final PageServiceImpl pageService;

    @PostMapping(
            path = "/post",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<PostPageResponse>> posting(@PathVariable("id_page") String idPage,
                                                                    @RequestBody PostingPageRequest postingPageRequest)
    {
        PostPageResponse response =  pageService.post(idPage, postingPageRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                CommonResponse.<PostPageResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully post from page")
                        .data(response)
                        .build()
        );
    }
    @PatchMapping(
            path = "/post/{id_post}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<ClearPostResponse>> UpdatePosting(@PathVariable("id_page") String idPage,
                                           @PathVariable("id_post") String idPost,
                                           @RequestBody PostingPageRequest postingPageRequest)
    {
        ClearPostResponse response =  pageService.updatePost(idPage, idPost, postingPageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<ClearPostResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully update post from page")
                        .data(response)
                        .build()
        );
    }
    @DeleteMapping(
            path = "/post/{id_post}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> DeletePosting(@PathVariable("id_page") String idPage,
                                                                @PathVariable("id_post") String idPost)
    {
        pageService.deletePost(idPage, idPost);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully delete post from page")
                        .data("Success")
                        .build()
        );
    }


    @PostMapping(
            path = "/post/{id_post}/comment",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<CommentPageResponse>> createComment(@PathVariable("id_page") String idPage,
                                                                             @PathVariable("id_post") String idPost,
                                                                             @RequestBody CommentPageRequest request)
    {
        CommentPageResponse response = pageService.comment(idPage, idPost, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<CommentPageResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully comment from page")
                        .data(response)
                        .build()
        );
    }

    @PatchMapping(
            path = "/post/{id_post}/comment/{id_comments}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<ClearCommentResponse>> updateComment(@PathVariable("id_page") String idPage,
                                                                              @PathVariable("id_post") String idPost,
                                                                              @PathVariable("id_comments") String idComment,
                                                                              @RequestBody CommentPageRequest request)
    {
        ClearCommentResponse response = pageService.updateComment(idPage, idPost, idComment, request);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<ClearCommentResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully update post from page")
                        .data(response)
                        .build()
        );
    }
    @DeleteMapping(
            path = "/post/{id_post}/comment/{id_comments}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> deleteComment(@PathVariable("id_page") String idPage,
                                           @PathVariable("id_post") String idPost,
                                           @PathVariable("id_comments") String idComment)
    {
        pageService.deleteComment(idPage, idPost, idComment);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully delete comment from page")
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
            @PathVariable("id_page") String idPage,
            @PathVariable("id_post") String idPost,
            @RequestBody LikesPostRequest likesPostRequest)
    {
        pageService.likePost(idPage, idPost, likesPostRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully liking post from page")
                        .data("Success")
                        .build()
        );
    }
    @DeleteMapping(
            path = "/post/{id_post}/likes/{id_likes}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> unlikingPosting(
            @PathVariable("id_page") String idPage,
            @PathVariable("id_post") String idPost,
            @PathVariable("id_likes") String idLikes)
    {
        pageService.unlikePost(idPage, idPost, idLikes);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully unliking post from page")
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
            @PathVariable("id_page") String idPage,
            @PathVariable("id_post") String idPost,
            @PathVariable("id_comment") String idComment,
            @RequestBody LikesPostRequest likesPostRequest
    )
    {
        pageService.likingComment(idPage, idPost, idComment, likesPostRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully liking comment from page")
                        .data("Success")
                        .build()
        );
    }
    @DeleteMapping(
            path = "/post/{id_post}/comment/{id_comment}/likes/{id_likes}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<String>> unlikingComment(
            @PathVariable("id_page") String idPage,
            @PathVariable("id_post") String idPost,
            @PathVariable("id_comment") String idComment,
            @PathVariable("id_likes") String idLikes
    )
    {
        pageService.unlikingComment(idPage, idPost, idComment, idLikes);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully unliking comment from page")
                        .data("Success")
                        .build()
        );
    }


    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<List<ClearPostResponse>>> homePage(
            @PathVariable("id_page") String idPage
    )
    {
        List<ClearPostResponse> response = pageService.getAllPosts(idPage);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.<List<ClearPostResponse>>builder()
                        .data(response)
                        .message("Success")
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

}
