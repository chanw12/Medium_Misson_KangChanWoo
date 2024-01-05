package com.ll.medium.domain.login.controller;

import com.ll.medium.domain.login.service.LoginServiceV2;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.form.MemberLoginForm;
import com.ll.medium.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final LoginServiceV2 loginServiceV2;
    private final Rq rq;
    @PostMapping("/login")
    public ResponseEntity<Member> authorize(@Valid @RequestBody MemberLoginForm memberLoginForm) {
        LoginServiceV2.AuthAndMakeTokensResponseBody Tokens = loginServiceV2.login(memberLoginForm);
        // tokenDto를 이용해 response body에도 넣어서 리턴
        rq.setCrossDomainCookie("RefreshToken", Tokens.getRefreshToken());
        rq.setCrossDomainCookie("accessToken", Tokens.getAccessToken());

        return new ResponseEntity<>(Tokens.getMember(),HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(Principal principal){
        rq.setLogout();
        return ResponseEntity.ok().build();
    }



}