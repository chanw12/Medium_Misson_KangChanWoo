package com.ll.medium.domain.member.dto;

import com.ll.medium.domain.member.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberDto {
    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;;
    private String email;
    private String username;
    private String password;
    private String nickname;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.createDate = member.getCreateDate();
        this.modifyDate = member.getModifyDate();
        this.email = member.getEmail();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
    }
}
