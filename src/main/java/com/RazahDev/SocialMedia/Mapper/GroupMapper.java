package com.RazahDev.SocialMedia.Mapper;

import com.RazahDev.SocialMedia.DTO.Response.Activity.CreateGroupResponse;
import com.RazahDev.SocialMedia.Entities.Group;

public interface GroupMapper {
    CreateGroupResponse toResponseCreate(Group group);
}
