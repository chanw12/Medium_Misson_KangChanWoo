package com.ll.medium.domain.post.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.medium.domain.comment.entity.Comment;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

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


    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @ManyToMany
    Set<Member> voter;


//    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = {CascadeType.ALL})
//    @Builder.Default
//    @ToString.Exclude
//    private Set<PostTag> postTags = new HashSet<>();

}
