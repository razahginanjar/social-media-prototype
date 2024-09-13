package com.RazahDev.SocialMedia.Services.impl;

import com.RazahDev.SocialMedia.DTO.Response.Activity.FriendsResponse;
import com.RazahDev.SocialMedia.Entities.FriendList;
import com.RazahDev.SocialMedia.Entities.User;
import com.RazahDev.SocialMedia.Repository.FriendListRepository;
import com.RazahDev.SocialMedia.Services.FriendListservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendListServiceImpl implements FriendListservice {
    private final FriendListRepository friendListRepository;
    private final UserServiceImpl userService;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addFriend(String idFriend) {
        if(!userService.isAccountExist(idFriend))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account is not found!");
        }
        User byContext = userService.getByContext();
        List<FriendList> allByUser = friendListRepository.findAllByUser(byContext);
        for (FriendList friend : allByUser) {
            String id = friend.getFriend().getId();
            if(id.equals(idFriend))
            {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already Friend!");
            }
        }

        FriendList user = FriendList.builder()
                .user(byContext)
                .friend(userService.getUserByID(idFriend))
                .build();
        friendListRepository.saveAndFlush(user);

        FriendList friend = FriendList.builder()
                .user(userService.getUserByID(idFriend))
                .friend(byContext)
                .build();
        friendListRepository.saveAndFlush(friend);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeFriend(String idFriend) {
        if(!userService.isAccountExist(idFriend))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account is not found!");
        }

        User byContext = userService.getByContext();
        List<FriendList> allByUser = friendListRepository.findAllByUser(byContext);
        for (FriendList friend : allByUser) {
            String id = friend.getFriend().getId();
            if(id.equals(idFriend))
            {
                List<FriendList> allByUser1 = friendListRepository.findAllByUser(friend.getFriend());
                for (FriendList friendList : allByUser1) {
                    if(friendList.getFriend().getId().equals(byContext.getId()))
                    {
                        friendListRepository.delete(friendList);
                    }
                }
                friendListRepository.delete(friend);
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You're Not Friend with her/him");
    }
    @Transactional(readOnly = true)
    @Override
    public List<FriendsResponse> seeAllFriend() {
        User byContext = userService.getByContext();
        List<FriendList> allByUser = friendListRepository.findAllByUser(byContext);
        return allByUser.stream().map(
                friendList -> {
                    return FriendsResponse.builder()
                            .friendName(friendList.getFriend().getUsername())
                            .build();
                }
        ).toList();
    }
    @Transactional(readOnly = true)
    @Override
    public List<FriendList> getAllFriends(User user) {
        return friendListRepository.findAllByUser(user);
    }
}
