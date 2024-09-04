package com.RazahDev.SocialMedia.Services;

import com.RazahDev.SocialMedia.Entities.FollowerPage;
import com.RazahDev.SocialMedia.Entities.Page;
import com.RazahDev.SocialMedia.Entities.User;

import java.util.List;

public interface FollowerPageService {
    void addFollower(User user, Page page);
    void removeFollower(User user, Page page);
    List<FollowerPage> getAllFollowPage(User user);
}
