package com.RazahDev.SocialMedia.Entities;

import com.RazahDev.SocialMedia.Constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table (name = ConstantTable.POST)
@Getter
@Setter
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "content")
    private String post;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userPost;

    @ManyToOne
    @JoinColumn(name = "page_id")
    private Page pagePost;

    @OneToMany(mappedBy = "postComment")
    private List<CommentUser> commentUsers;

    @OneToMany(mappedBy = "postLikeUser")
    private List<LikesUser> likesUsers;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group groupPost;

}
