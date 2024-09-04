package com.RazahDev.SocialMedia.Repository;

import com.RazahDev.SocialMedia.Entities.CommentUser;
import com.RazahDev.SocialMedia.Entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentUserRepository extends JpaRepository<CommentUser, String> {
    List<CommentUser> findAllByPostComment(Post postComment);
}
