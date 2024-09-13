package com.RazahDev.SocialMedia.Repository;

import com.RazahDev.SocialMedia.Entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesUserRepository extends JpaRepository<LikesUser, String> {
    void deleteAllByPostLikeUserAndCommentLikeUserIsNull(Post postLikeUser);
    void deleteAllByCommentLikeUser(CommentUser commentLikeUser);
    Boolean existsByCommentLikeUserAndUserLikesAndPageLikesIsNull(CommentUser commentLikeUser, User userLikes);
    Boolean existsByPostLikeUserAndUserLikesAndPageLikesIsNull(Post postLikeUser, User userLikes);
    Boolean existsByCommentLikeUserAndUserLikesAndPageLikes(CommentUser commentLikeUser, User userLikes, Page pageLikes);
    Boolean existsByPostLikeUserAndUserLikesAndPageLikes(Post postLikeUser, User userLikes, Page pageLikes);
}
