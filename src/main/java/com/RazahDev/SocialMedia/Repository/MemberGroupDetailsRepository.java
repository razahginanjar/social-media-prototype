package com.RazahDev.SocialMedia.Repository;

import com.RazahDev.SocialMedia.Constant.RoleGroup;
import com.RazahDev.SocialMedia.Entities.Group;
import com.RazahDev.SocialMedia.Entities.MemberGroupDetails;
import com.RazahDev.SocialMedia.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberGroupDetailsRepository extends JpaRepository<MemberGroupDetails, String> {
    Boolean existsByGroupAndRoleAndMember(Group group, RoleGroup role, User member);
    List<MemberGroupDetails> findAllByGroup(Group group);
    Optional<MemberGroupDetails> findByGroupAndMemberAndRole(Group group, User member, RoleGroup role);
    void deleteByGroupAndRoleAndMember(Group group, RoleGroup role, User member);
    void deleteByGroup(Group group);
    List<MemberGroupDetails> findAllByGroupAndMember(Group group, User member);
}
