package com.ll.medium.domain.member.repository;

import com.ll.medium.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepositoryCustom {
    public Optional<Member> findByUserName(String username);

}
