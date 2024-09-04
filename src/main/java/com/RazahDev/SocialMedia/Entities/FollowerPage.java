package com.RazahDev.SocialMedia.Entities;

import com.RazahDev.SocialMedia.Constant.ConstantTable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = ConstantTable.FOLLOWER_PAGE)
public class FollowerPage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User follower;

    @ManyToOne
    @JoinColumn(name = "page_id")
    Page page;
}
