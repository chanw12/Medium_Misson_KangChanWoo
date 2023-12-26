package com.ll.medium.domain.post.repository;

import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {
    Page<Post> getListIsPublished(Pageable pageable);

    Page<Post> findByUserName(Pageable pageable,String username);

    Post findByUserNameNumber(String username,Long number);

    List<Post> getHomeList();
    Page<Post> getMyList(Pageable pageable,String username);

    Boolean voteCheck(Long id, Member member);

    Page<Post> search(List<String> kwTypes, String kw, Pageable pageable);
}
