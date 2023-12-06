package com.ll.medium.domain.member.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginForm {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
