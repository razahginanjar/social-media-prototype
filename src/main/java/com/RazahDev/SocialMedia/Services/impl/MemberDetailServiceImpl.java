package com.RazahDev.SocialMedia.Services.impl;

import com.RazahDev.SocialMedia.Constant.RoleGroup;
import com.RazahDev.SocialMedia.Entities.Group;
import com.RazahDev.SocialMedia.Entities.MemberGroupDetails;
import com.RazahDev.SocialMedia.Entities.User;
import com.RazahDev.SocialMedia.Repository.MemberGroupDetailsRepository;
import com.RazahDev.SocialMedia.Services.MemberDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberDetailServiceImpl implements MemberDetailService {
    private final MemberGroupDetailsRepository memberGroupDetailsRepository;
    @Transactional(rollbackFor = Exception.class)
    public void addCreator(User creator, Group group)
    {
        MemberGroupDetails memberGroupDetails = new MemberGroupDetails();
        memberGroupDetails.setGroup(group);
        memberGroupDetails.setMember(creator);
        memberGroupDetails.setRole(RoleGroup.CREATOR);
        if(memberGroupDetailsRepository.existsByGroupAndRoleAndMember(group, RoleGroup.CREATOR, creator))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You're an already creator");
        }
        memberGroupDetailsRepository.saveAndFlush(memberGroupDetails);
        addAdmin(creator, group);
    }
    @Transactional(rollbackFor = Exception.class)
    public void addAdmin(User admin, Group group)
    {
        MemberGroupDetails memberGroupDetails = new MemberGroupDetails();
        memberGroupDetails.setGroup(group);
        memberGroupDetails.setMember(admin);
        memberGroupDetails.setRole(RoleGroup.ADMIN);
        if(memberGroupDetailsRepository.existsByGroupAndRoleAndMember(group, RoleGroup.ADMIN, admin))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already an Admin");
        }
        memberGroupDetailsRepository.saveAndFlush(memberGroupDetails);
        addMember(admin, group);
    }
    @Transactional(rollbackFor = Exception.class)
    public void addMember(User member, Group group)
    {
        MemberGroupDetails memberGroupDetails = new MemberGroupDetails();
        memberGroupDetails.setGroup(group);
        memberGroupDetails.setMember(member);
        memberGroupDetails.setRole(RoleGroup.MEMBER);
        if(memberGroupDetailsRepository.existsByGroupAndRoleAndMember(group, RoleGroup.MEMBER, member))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already an become Member");
        }
        memberGroupDetailsRepository.saveAndFlush(memberGroupDetails);
    }
    @Transactional(readOnly = true)
    public List<MemberGroupDetails> memberGroups(User admin, Group group)
    {
        List<MemberGroupDetails> members = memberGroupDetailsRepository.findAllByGroup(group);
        for (MemberGroupDetails member : members) {
            if(member.getMember().getId().equals(admin.getId()) && member.getRole() == RoleGroup.ADMIN)
            {
                return members;
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You do not have permission to do this!");
    }
    @Transactional(readOnly = true)
    public Boolean isUserMemberGroup(User user, Group group)
    {
        return memberGroupDetailsRepository.existsByGroupAndRoleAndMember(group, RoleGroup.MEMBER, user);
    }
    @Transactional(rollbackFor = Exception.class)
    public void deleteBulk(List<MemberGroupDetails> members)
    {
        memberGroupDetailsRepository.deleteAll(members);
    }
    @Transactional(rollbackFor = Exception.class)
    public void removeMember(User member, Group group)
    {
        List<MemberGroupDetails> allByGroupAndMember = memberGroupDetailsRepository.findAllByGroupAndMember(group, member);
        memberGroupDetailsRepository.deleteAll(allByGroupAndMember);
    }
    @Transactional(readOnly = true)
    public Boolean isUserAnAdminGroup(User user, Group group)
    {
        if(isUserMemberGroup(user, group))
        {
            return memberGroupDetailsRepository.existsByGroupAndRoleAndMember(group, RoleGroup.ADMIN, user);
        }
        return false;
    }
    @Transactional(readOnly = true)
    public Boolean isUserAnCreatorGroup(User user, Group group)
    {
        if(isUserMemberGroup(user, group))
        {
            return memberGroupDetailsRepository.existsByGroupAndRoleAndMember(group, RoleGroup.CREATOR, user);
        }
        return false;
    }
    @Transactional(rollbackFor = Exception.class)
    public void editRoleMember(User user, Group group, RoleGroup roleGroup)
    {
        if (roleGroup == RoleGroup.MEMBER)
        {
            if(memberGroupDetailsRepository.existsByGroupAndRoleAndMember(group, RoleGroup.ADMIN, user))
            {
                memberGroupDetailsRepository.deleteByGroupAndRoleAndMember(group, RoleGroup.ADMIN, user);
            }
        } else if (roleGroup == RoleGroup.ADMIN) {
            if(memberGroupDetailsRepository.existsByGroupAndRoleAndMember(group, RoleGroup.MEMBER, user))
            {
                MemberGroupDetails memberGroupDetails = new MemberGroupDetails();
                memberGroupDetails.setGroup(group);
                memberGroupDetails.setMember(user);
                memberGroupDetails.setRole(RoleGroup.ADMIN);
                if(memberGroupDetailsRepository.existsByGroupAndRoleAndMember(group, RoleGroup.ADMIN, user))
                {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already an Admin");
                }
                memberGroupDetailsRepository.saveAndFlush(memberGroupDetails);
            }
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public void deleteMemberByGroup(Group group)
    {
        memberGroupDetailsRepository.deleteByGroup(group);
    }
}
