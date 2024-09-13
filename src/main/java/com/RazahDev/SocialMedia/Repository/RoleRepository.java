package com.RazahDev.SocialMedia.Repository;

import com.RazahDev.SocialMedia.Constant.Role;
import com.RazahDev.SocialMedia.Entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, String> {
    Optional<UserRole> findUserRoleByRole(Role role);
}
