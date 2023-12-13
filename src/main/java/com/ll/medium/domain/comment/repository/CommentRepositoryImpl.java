package com.ll.medium.domain.comment.repository;

import com.ll.medium.domain.comment.entity.Comment;
import com.ll.medium.domain.comment.entity.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }
    @Override
    public List<Comment> findByPostId(Long id) {
        QComment qComment = QComment.comment;
        List<Comment> list = jpaQueryFactory.selectFrom(qComment)
                .where(qComment.post.id.eq(id))
                .orderBy(qComment.createDate.desc())
                .fetch();
        return list;
    }
}
