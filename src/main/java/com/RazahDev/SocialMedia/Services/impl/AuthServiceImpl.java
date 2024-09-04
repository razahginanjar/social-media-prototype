package com.RazahDev.SocialMedia.Services.impl;

import com.RazahDev.SocialMedia.Constant.Role;
import com.RazahDev.SocialMedia.DTO.Request.Auth.LoginRequest;
import com.RazahDev.SocialMedia.DTO.Request.Auth.RegisterRequest;
import com.RazahDev.SocialMedia.DTO.Response.Auth.LoginResponse;
import com.RazahDev.SocialMedia.DTO.Response.Auth.RegisterResponse;
import com.RazahDev.SocialMedia.Entities.User;
import com.RazahDev.SocialMedia.Entities.UserRole;
import com.RazahDev.SocialMedia.Mapper.impl.UserMapperImpl;
import com.RazahDev.SocialMedia.Repository.UserRepository;
import com.RazahDev.SocialMedia.Services.AuthService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userAccountRepository;
    private final UserMapperImpl userMapper;
    private final JwtServiceImpl jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleServiceImpl roleService;
    private final AuthenticationManager authenticationManager;

    @Value(value = "${social_media-project.SUPER_ADMIN.username}")
    private String superAdminUsername;

    @Value(value = "${social_media-project.SUPER_ADMIN.password}")
    private String superAdminPassword;


    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void initSuperAdmin()
    {
        userAccountRepository.findByUsername(superAdminUsername).orElseGet(
                () -> userAccountRepository.saveAndFlush(User.builder()
                        .username(superAdminUsername)
                        .password(passwordEncoder.encode(superAdminPassword))
                        .roles(List.of(
                                roleService.getOrSave(Role.ROLE_SUPER_ADMIN),
                                roleService.getOrSave(Role.ROLE_ADMIN),
                                roleService.getOrSave(Role.ROLE_USER)
                        ))
                        .isEnabled(true)
                        .build())
        );
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse register(RegisterRequest request) {
        UserRole role = roleService.getOrSave(Role.ROLE_USER);
        if(userAccountRepository.existsByEmail(request.getEmail()))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email Already Created!");
        }
        User user = new User();
        String encode = passwordEncoder.encode(request.getPassword());
        user.setUsername(request.getUsername());
        user.setEnabled(true);
        user.setPassword(encode);
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRoles(List.of(role));
        user.setCreatedAt(Instant.now());

        userAccountRepository.saveAndFlush(user);

        return userMapper.userToRegisterRes(user);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(authentication);
        User user = (User) authenticate.getPrincipal();
        String s = jwtService.generateToken(user);
        return LoginResponse.builder()
                .token(s).username(user.getUsername())
                .roles(user.getAuthorities().stream().map(
                        GrantedAuthority::getAuthority
                ).toList())
                .build();
    }
}
