package com.RazahDev.SocialMedia.Entities;

import com.RazahDev.SocialMedia.Constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = ConstantTable.GROUP)
@Getter
@Setter
@Builder
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String description;

    @Column(name = "created_at")
    private Instant createdAt;

    private String link;

}
