package com.RazahDev.SocialMedia.Mapper.impl;

import com.RazahDev.SocialMedia.DTO.Response.Activity.CreateGroupResponse;
import com.RazahDev.SocialMedia.Entities.Group;
import com.RazahDev.SocialMedia.Mapper.GroupMapper;
import org.springframework.stereotype.Service;

@Service
public class GroupMapperImpl implements GroupMapper {
    @Override
    public CreateGroupResponse toResponseCreate(Group group) {
        return CreateGroupResponse
                .builder()
                .name(group.getName())
                .description(group.getDescription())
                .link(group.getLink())
                .build();
    }
}
