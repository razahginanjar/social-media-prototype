package com.RazahDev.SocialMedia.Repository;

import com.RazahDev.SocialMedia.Entities.FriendList;
import com.RazahDev.SocialMedia.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendListRepository extends JpaRepository<FriendList, String> {
    List<FriendList> findAllByUser(User user);
}
