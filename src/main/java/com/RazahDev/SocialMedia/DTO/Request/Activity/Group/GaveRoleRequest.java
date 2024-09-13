package com.RazahDev.SocialMedia.DTO.Request.Activity.Group;

import com.RazahDev.SocialMedia.Constant.RoleGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GaveRoleRequest {
    private RoleGroup roleGroup;
    private String idMember;
}
