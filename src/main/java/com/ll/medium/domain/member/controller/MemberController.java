package com.ll.medium.domain.member.controller;

import com.ll.medium.domain.member.dto.MemberDto;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.form.MemberJoinForm;
import com.ll.medium.domain.member.service.MemberService;
import com.ll.medium.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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



    @PostMapping("/join")
    public ResponseEntity<RsData> join(@RequestBody MemberJoinForm memberJoinForm){
        Member member = Member.builder().username(memberJoinForm.getUsername()).password(memberJoinForm.getPassword()).build();
        memberService.create(member);
        return ResponseEntity.ok().body(RsData.of("200","회원가입이 완료되었습니다.",new MemberDto(member)));

    }



}
