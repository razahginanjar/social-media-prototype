package com.RazahDev.SocialMedia.Entities;

import com.RazahDev.SocialMedia.Constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = ConstantTable.COMMENT_USER)
@Getter
@Setter
@Builder
public class CommentUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "comment", nullable = false, updatable = true)
    private String comment;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userComment;

    @ManyToOne
    @JoinColumn(name = "id_post")
    private Post postComment;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group groupComment;

    @ManyToOne
    @JoinColumn(name = "page_id")
    private Page pageComment;

    @OneToMany(mappedBy = "commentLikeUser")
    private List<LikesUser> likesCommentUsers;


    //list likes
    //id_post
    //id_user /
}
