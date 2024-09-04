package com.RazahDev.SocialMedia.Entities;

import com.RazahDev.SocialMedia.Constant.ConstantTable;
import com.RazahDev.SocialMedia.Constant.RoleGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = ConstantTable.MEMBER_GROUP_DETAIL)
public class MemberGroupDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User member;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "role_group")
    @Enumerated(EnumType.STRING)
    private RoleGroup role;
}
