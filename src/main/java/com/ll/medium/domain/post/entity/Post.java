package com.ll.medium.domain.post.entity;

import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@Getter
@Setter
public class Post extends BaseEntity {
    @Column
    private String title;
    @ManyToOne
    private Member author;

    @Column(columnDefinition = "LONGTEXT")
    private String body;

    @Column(name = "isPublished")
    private boolean isPublished;

//    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = {CascadeType.ALL})
//    @Builder.Default
//    @ToString.Exclude
//    private Set<PostTag> postTags = new HashSet<>();

}
