package com.RazahDev.SocialMedia.Services;

import com.RazahDev.SocialMedia.Constant.RoleGroup;
import com.RazahDev.SocialMedia.Entities.Group;
import com.RazahDev.SocialMedia.Entities.MemberGroupDetails;
import com.RazahDev.SocialMedia.Entities.User;

import java.util.List;

public interface MemberDetailService {
    void addCreator(User creator, Group group);
    void addAdmin(User admin, Group group);
    void addMember(User member, Group group);
    List<MemberGroupDetails> memberGroups(User admin, Group group);
    Boolean isUserMemberGroup(User user, Group group);
    void deleteBulk(List<MemberGroupDetails> members);
    void removeMember(User member, Group group);
    Boolean isUserAnAdminGroup(User user, Group group);
    Boolean isUserAnCreatorGroup(User user, Group group);
    void editRoleMember(User user, Group group, RoleGroup roleGroup);
    void deleteMemberByGroup(Group group);
}
