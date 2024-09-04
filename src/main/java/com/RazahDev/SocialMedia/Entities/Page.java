package com.RazahDev.SocialMedia.Entities;


import com.RazahDev.SocialMedia.Constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = ConstantTable.PAGE)
@Builder
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "page_name", length = 100, unique = true)
    private String name;

    @Column(name = "description", length = 150)
    private String description;

    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "pagePost")
    private List<Post> postUsers;

    @OneToMany(mappedBy = "pageComment")
    private List<CommentUser> commentUsers;

    @OneToMany(mappedBy = "pageLikes")
    private List<LikesUser> likesUsers;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;

}
