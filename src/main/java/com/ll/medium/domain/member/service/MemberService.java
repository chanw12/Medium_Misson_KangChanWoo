package com.ll.medium.domain.member.service;

import com.ll.medium.domain.login.entity.Authority;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.form.MemberJoinForm;
import com.ll.medium.domain.member.repository.MemberRepository;
import com.ll.medium.domain.member.util.SecurityUtil;
import com.ll.medium.global.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member join(MemberJoinForm memberJoinForm){
        if(memberRepository.findByUserName(memberJoinForm.getUsername()).orElse(null)!=null){
            throw new UserAlreadyExistsException("이미 가입된 유저입니다");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        Member member = Member.builder()
                .username(memberJoinForm.getUsername())
                .password(passwordEncoder.encode(memberJoinForm.getPassword()))
                .authorities(Collections.singleton(authority))
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







}
