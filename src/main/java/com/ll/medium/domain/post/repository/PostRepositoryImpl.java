package com.ll.medium.domain.post.repository;

import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.domain.post.entity.QPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    public PostRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }
    @Override
    public Page<Post> getListIsPublished(Pageable pageable) {
        QPost qPost = QPost.post;;
        List<Post> list = jpaQueryFactory.selectFrom(qPost)
                .where(qPost.isPublished.eq(true))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory.selectFrom(qPost)
                .where(qPost.isPublished.eq(true))
                .fetchCount();
        return new PageImpl<>(list,pageable,total);
    }

    @Override
    public Page<Post> findByUserName(Pageable pageable, String username) {
        QPost qPost = QPost.post;
        List<Post> posts = jpaQueryFactory.selectFrom(qPost)
                .where(qPost.author.username.eq(username))
                .orderBy(qPost.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = jpaQueryFactory.selectFrom(qPost)
                .where(qPost.author.username.eq(username))
                .fetchCount();
        return new PageImpl<>(posts,pageable,total);
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

    @Override
    public List<Post> getHomeList() {
        QPost qPost = QPost.post;
        List<Post> list = jpaQueryFactory.selectFrom(qPost)
                .where(qPost.isPublished.eq(true))
                .orderBy(qPost.createDate.desc())
                .limit(30)
                .fetch();
        return list;
    }

    @Override
    public Page<Post> getMyList(Pageable pageable,String username) {
            QPost qPost = QPost.post;
            List<Post> list = jpaQueryFactory.selectFrom(qPost)
                    .where(qPost.author.username.eq(username))
                    .orderBy(qPost.createDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            long total = jpaQueryFactory.selectFrom(qPost)
                    .where(qPost.author.username.eq(username))
                    .fetchCount();

            return new PageImpl<>(list,pageable,total);

    }

    @Override
    public Boolean voteCheck(Long id, Member member) {
        QPost qPost = QPost.post;
        return jpaQueryFactory.selectFrom(qPost)
                .where(qPost.id.eq(id).and(qPost.voter.contains(member)))
                .fetch().isEmpty();

    }

    // title,body,author
    @Override
    public Page<Post> search(List<String> kwTypes, String kw, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        QPost qPost = QPost.post;

        if (!kw.isBlank()){
            List<BooleanExpression> conditions = new ArrayList<>();

            if(kwTypes.contains("title")){
                conditions.add(qPost.author.email.containsIgnoreCase(kw));
            }
            if(kwTypes.contains("body")){
                conditions.add(qPost.body.containsIgnoreCase(kw));
            }
            if(kwTypes.contains("title")){
                conditions.add(qPost.title.containsIgnoreCase(kw));
            }

            // 조건 리스트를 or 조건으로 결합합니다.
            BooleanExpression combinedCondition = conditions.stream()
                    .reduce(BooleanExpression::or)
                    .orElse(null);

            // 최종적으로 생성된 조건을 쿼리에 적용합니다.
            if (combinedCondition != null) {
                builder.and(combinedCondition);
            }




        }
        List<Post> list = jpaQueryFactory.selectDistinct(qPost)
                .from(qPost)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        long total = jpaQueryFactory.selectDistinct(qPost)
                .from(qPost)
                .where(builder)
                .fetchCount();

        return new PageImpl<>(list,pageable,total);

    }
}
