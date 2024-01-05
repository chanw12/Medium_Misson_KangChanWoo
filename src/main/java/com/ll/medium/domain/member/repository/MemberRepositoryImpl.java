package com.ll.medium.domain.member.repository;

import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.Optional;


public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Member> findByUserName(String username) {

        QMember qMember = QMember.member;
        Optional<Member> member =
                jpaQueryFactory.selectFrom(qMember).where(qMember.username.eq(username)).stream().findFirst();
        return member;
    }

    @Override
    public Optional<Member> findByRefreshToken(String refreshToken) {
        QMember qMember = QMember.member;
        Optional<Member> member = jpaQueryFactory.selectFrom(qMember).where(qMember.refreshToken.eq(refreshToken))
                .stream().findFirst();
        return member;
    }
}
