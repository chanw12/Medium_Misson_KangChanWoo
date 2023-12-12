package com.ll.medium.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.medium.domain.login.entity.Authority;
import com.ll.medium.domain.post.entity.Post;
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
public class Member extends BaseEntity {



    private String username;
    private String password;
    private String email;
    private String nickname;

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Post> postList;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
}
