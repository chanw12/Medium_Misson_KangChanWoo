package com.ll.medium.domain.member.service;

import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void create(Member member){
         memberRepository.save(member);

    }

    public Boolean isEmpty(Member member){
        return memberRepository.findByUserName(member.getUsername()).isEmpty();
    }

}
