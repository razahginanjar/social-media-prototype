package com.RazahDev.SocialMedia.Services;

import com.RazahDev.SocialMedia.Entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User getByContext();

    User getUserByID(String userID);

    Boolean isAccountExist(String userId);
}
