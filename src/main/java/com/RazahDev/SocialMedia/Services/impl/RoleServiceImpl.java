package com.RazahDev.SocialMedia.Services.impl;

import com.RazahDev.SocialMedia.Constant.Role;
import com.RazahDev.SocialMedia.Entities.UserRole;
import com.RazahDev.SocialMedia.Repository.RoleRepository;
import com.RazahDev.SocialMedia.Services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserRole getOrSave(Role role) {
        return roleRepository.findUserRoleByRole(role).orElseGet(
                () -> roleRepository.saveAndFlush(UserRole.builder()
                        .role(role)
                        .build())
        );
    }
}
