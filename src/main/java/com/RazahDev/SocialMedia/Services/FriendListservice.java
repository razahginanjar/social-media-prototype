package com.RazahDev.SocialMedia.Services;

import com.RazahDev.SocialMedia.DTO.Response.Activity.FriendsResponse;
import com.RazahDev.SocialMedia.Entities.FriendList;
import com.RazahDev.SocialMedia.Entities.User;

import java.util.List;

public interface FriendListservice {
    void addFriend(String idFriend);

    void removeFriend(String idFriend);

    List<FriendsResponse> seeAllFriend();

    List<FriendList> getAllFriends(User user);
}
