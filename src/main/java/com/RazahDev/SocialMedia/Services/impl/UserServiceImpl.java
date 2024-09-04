package com.RazahDev.SocialMedia.Services.impl;

import com.RazahDev.SocialMedia.Entities.User;
import com.RazahDev.SocialMedia.Repository.UserRepository;
import com.RazahDev.SocialMedia.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Transactional(readOnly = true)
    @Override
    public User getByContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> byUsername = userRepository.findByUsername(authentication.getPrincipal().toString());
        return byUsername.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Users not found")
        );
    }
    @Transactional(readOnly = true)
    @Override
    public User getUserByID(String userID) {
        return userRepository.findById(userID).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Do Not Exist")
        );
    }
    @Transactional(readOnly = true)
    @Override
    public Boolean isAccountExist(String userId) {
        return userRepository.existsById(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("cannot find accoun from the username")
        );
    }
}
