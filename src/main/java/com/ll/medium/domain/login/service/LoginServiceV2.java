package com.ll.medium.domain.login.service;

import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.form.MemberLoginForm;
import com.ll.medium.domain.member.repository.MemberRepository;
import com.ll.medium.global.app.AppConfig;
import com.ll.medium.global.exception.GlobalException;
import com.ll.medium.global.jwt.TokenProvider;
import com.ll.medium.global.rq.Rq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceV2 {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final Rq rq;


    @AllArgsConstructor
    @Getter
    public static class AuthAndMakeTokensResponseBody {
        private Member member;
        private String accessToken;
        private String refreshToken;
    }

    public AuthAndMakeTokensResponseBody login(MemberLoginForm memberLoginForm){

        Member member = memberRepository.findByUserName(memberLoginForm.getUsername())
                .orElseThrow(()->new GlobalException("400-1","해당 유저가 존재하지 않습니다"));

        if(!passwordMatches(member,memberLoginForm.getPassword()))
            throw new GlobalException("400-2","비밀번호가 일치하지 않습니다");


        String refreshToken = member.getRefreshToken();
        String accessToken = tokenProvider.genToken(member, AppConfig.getAccessTokenExpirationSec());;

        return new AuthAndMakeTokensResponseBody(member,accessToken,refreshToken);
    }


    public boolean passwordMatches(Member member, String password) {
        return passwordEncoder.matches(password, member.getPassword());
    }

}
