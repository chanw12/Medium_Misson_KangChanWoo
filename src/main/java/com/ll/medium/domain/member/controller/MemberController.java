package com.ll.medium.domain.member.controller;

import com.ll.medium.domain.member.dto.MemberDto;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.form.MemberJoinForm;
import com.ll.medium.domain.member.service.MemberService;
import com.ll.medium.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/join")
    public ResponseEntity<RsData> join(@RequestBody MemberJoinForm memberJoinForm){
        if(!memberJoinForm.getPassword().equals(memberJoinForm.getPasswordconfirm())){
           return ResponseEntity.badRequest().body(RsData.of("400","비밀번호와 비밀번호 확인이 일치하지 않습니다.",null));
        }

        Member member = Member.builder().username(memberJoinForm.getUsername()).password(passwordEncoder.encode(memberJoinForm.getPassword())).build();
        if(memberService.isEmpty(member)){
            memberService.create(member);
        }else{
            return ResponseEntity.badRequest().body(RsData.of("400","이미 존재하는 아이디 입니다.",null));
        }
        return ResponseEntity.ok().body(RsData.of("200","회원가입이 완료되었습니다.",new MemberDto(member)));
    }






}
