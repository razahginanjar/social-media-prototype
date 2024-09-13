package com.RazahDev.SocialMedia.Repository;


import com.RazahDev.SocialMedia.Entities.Group;
import com.RazahDev.SocialMedia.Entities.Page;
import com.RazahDev.SocialMedia.Entities.Post;
import com.RazahDev.SocialMedia.Entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostUserRepository extends JpaRepository<Post, String> {

    List<Post> findAllByUserPostAndPagePostIsNull(User userPost);

    List<Post> findAllByPagePost(Page pagePost);

    @Query(value = "select p from Post p where p.pagePost != null ")
    List<Post> findAllByPagePostIsNotNull(Pageable pageable);

    void deleteByGroupPost(Group groupPost);

    List<Post> findAllByGroupPost(Group groupPost);
}
