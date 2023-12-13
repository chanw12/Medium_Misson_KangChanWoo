package com.ll.medium.domain.comment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@Getter
public class Comment extends BaseEntity {

    private String body;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Member author;

    public void setAuthor(Member author) {
        this.author = author;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
