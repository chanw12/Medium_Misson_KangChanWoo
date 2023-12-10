package com.ll.medium.domain.post.repository;

import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.domain.post.entity.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }
    @Override
    public List<Post> getListIsPublished() {
        QPost qPost = QPost.post;
        List<Post> list = jpaQueryFactory.selectFrom(qPost)
                .where(qPost.isPublished.eq(true))
                .orderBy(qPost.createDate.desc()).fetch();
        return list;
    }

    @Override
    public List<Post> findByUserName(String username) {
        QPost qPost = QPost.post;
        return jpaQueryFactory.selectFrom(qPost)
                .where(qPost.author.username.eq(username))
                .orderBy(qPost.createDate.desc())
                .fetch();
    }

    @Override
    public Post findByUserNameNumber(String username, Long number) {
        QPost qPost = QPost.post;
        List<Post> posts = jpaQueryFactory.selectFrom(qPost)
                .where(qPost.author.username.eq(username))
                .orderBy(qPost.createDate.desc())
                .fetch();
        if (number >= 1 && number <= posts.size()) {
            return posts.get((int) (number - 1));
        } else {
            return null;// 해당하는 항목이 없으면 빈 리스트 반환
        }


    }
}
