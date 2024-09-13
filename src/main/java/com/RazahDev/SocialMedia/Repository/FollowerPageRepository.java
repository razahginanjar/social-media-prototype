package com.RazahDev.SocialMedia.Repository;

import com.RazahDev.SocialMedia.Entities.FollowerPage;
import com.RazahDev.SocialMedia.Entities.Page;
import com.RazahDev.SocialMedia.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerPageRepository extends JpaRepository<FollowerPage, String> {
    void deleteByFollowerAndPage(User follower, Page page);
    Boolean existsByFollowerAndPage(User follower, Page page);
    List<FollowerPage> findAllByFollower(User follower);
}
