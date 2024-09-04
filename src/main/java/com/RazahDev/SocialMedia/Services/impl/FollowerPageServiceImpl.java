package com.RazahDev.SocialMedia.Services.impl;

import com.RazahDev.SocialMedia.Entities.FollowerPage;
import com.RazahDev.SocialMedia.Entities.Page;
import com.RazahDev.SocialMedia.Entities.User;
import com.RazahDev.SocialMedia.Repository.FollowerPageRepository;
import com.RazahDev.SocialMedia.Services.FollowerPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowerPageServiceImpl implements FollowerPageService {
    private final FollowerPageRepository followerPageRepository;
    @Transactional(rollbackFor = Exception.class)
    public void addFollower(User user, Page page)
    {
        if(followerPageRepository.existsByFollowerAndPage(user, page))
        {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Already Follow this page");
        }

        FollowerPage followerPage = new FollowerPage();
        followerPage.setPage(page);
        followerPage.setFollower(user);
        followerPageRepository.saveAndFlush(followerPage);
    }
    @Transactional(rollbackFor = Exception.class)
    public void removeFollower(User user, Page page)
    {
        if(!followerPageRepository.existsByFollowerAndPage(user, page))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You did not follow this page");
        }
        followerPageRepository.deleteByFollowerAndPage(user, page);
    }
    @Transactional(readOnly = true)
    public List<FollowerPage> getAllFollowPage(User user)
    {
        return followerPageRepository.findAllByFollower(user);
    }
}
