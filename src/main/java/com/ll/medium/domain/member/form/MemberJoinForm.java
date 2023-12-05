package com.ll.medium.domain.member.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinForm {
    private String username;
    private String password;
    private String passwordconfirm;
}
