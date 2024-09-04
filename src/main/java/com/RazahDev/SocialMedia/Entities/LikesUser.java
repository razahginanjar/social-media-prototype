package com.RazahDev.SocialMedia.Entities;

import com.RazahDev.SocialMedia.Constant.ConstantTable;
import com.RazahDev.SocialMedia.Constant.LikesType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Table(name = ConstantTable.LIKES)
public class LikesUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "likes_type")
    private LikesType likesType;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentUser commentLikeUser;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postLikeUser;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userLikes;

    @ManyToOne
    @JoinColumn(name = "page_id")
    private Page pageLikes;

}
