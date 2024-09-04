package com.RazahDev.SocialMedia.Services;

import com.RazahDev.SocialMedia.Constant.Role;
import com.RazahDev.SocialMedia.Entities.UserRole;

public interface RoleService {
    UserRole getOrSave(Role role);
}
