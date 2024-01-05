package com.ll.medium.domain.member.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinForm {
    @NotEmpty(message = "username은 필수항목입니다")
    private String username;
    @NotEmpty(message = "password는 필수항목입니다")
    private String password;
    @NotEmpty(message = "passwordconfirm은 필수항목입니다")
    private String passwordconfirm;
    @NotEmpty(message = "닉네임은 필수항복입니다")
    private String nickname;

}
