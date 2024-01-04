package com.ll.medium.domain.member.service;

import com.ll.medium.domain.login.entity.Authority;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.form.MemberJoinForm;
import com.ll.medium.domain.member.repository.MemberRepository;
import com.ll.medium.domain.member.util.SecurityUtil;
import com.ll.medium.global.exception.GlobalException;
import com.ll.medium.global.exception.UserAlreadyExistsException;
import com.ll.medium.global.jwt.TokenProvider;
import com.ll.medium.global.rsData.RsData;
import com.ll.medium.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public Member join(MemberJoinForm memberJoinForm){
        if(memberRepository.findByUserName(memberJoinForm.getUsername()).orElse(null)!=null){
            throw new UserAlreadyExistsException("이미 가입된 유저입니다");
        }
        List<Authority> authorities = new ArrayList<>();
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        authorities.add(authority);
        Member member = Member.builder()
                .username(memberJoinForm.getUsername())
                .password(passwordEncoder.encode(memberJoinForm.getPassword()))
                .nickname(memberJoinForm.getNickname())
                .refreshToken(tokenProvider.genRefreshToken())
                .authorities(authorities)
                .build();

        return memberRepository.save(member);
    }

    public Boolean isEmpty(Member member){
        return memberRepository.findByUserName(member.getUsername()).isEmpty();
    }


    // 유저,권한 정보를 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<Member> getUserWithAuthorities(String username) {
        return memberRepository.findByUserName(username);
    }

    // 현재 securityContext에 저장된 username의 정보만 가져오는 메소드
    @Transactional(readOnly = true)
    public Optional<Member> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername()
                .flatMap(memberRepository::findByUserName);
    }


    @Transactional
    public Member whenSocialLogin(String providerTypeCode, String username, String nickname, String profileImgUrl) {
        Optional<Member> opMember = getUserWithAuthorities(username);
        if (opMember.isPresent()) return opMember.get();
        MemberJoinForm memberJoinForm = new MemberJoinForm();
        memberJoinForm.setUsername(username);
        memberJoinForm.setPassword("");
        memberJoinForm.setPasswordconfirm("");
        memberJoinForm.setNickname(nickname);
        return join(memberJoinForm);
    }

    public boolean validateToken(String token) {
        return tokenProvider.validateToken(token);
    }


    public RsData<String> refreshAccessToken(String refreshToken) {
        Member member = memberRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new GlobalException("400-1", "존재하지 않는 리프레시 토큰입니다."));

        String accessToken = tokenProvider.genAccessToken(member);

        return RsData.of("200-1", "토큰 갱신 성공", accessToken);
    }


    @Transactional
    public SecurityUser getUserFromAccessToken(String accessToken) {
        Map<String, Object> payloadBody = tokenProvider.getDataFrom(accessToken);

        long id = (int) payloadBody.get("id");
        String username = (String) payloadBody.get("username");
        Member member = memberRepository.findByUserName(username).get();
        return new SecurityUser(
                id,
                username,
                "",
                member.getAuthorities()
        );
    }


    public void deleteAll() {
        memberRepository.deleteAll();
    }
}

