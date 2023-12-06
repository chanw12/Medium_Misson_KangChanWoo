package com.ll.medium.domain.member.entity;

import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@Getter
public class Member extends BaseEntity {



    private String username;
    private String password;
    private String email;
    private String nickname;

    @OneToMany(mappedBy = "author")
    private List<Post> postList;
}
