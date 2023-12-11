package com.ll.medium.domain.post.repository;

import com.ll.medium.domain.post.entity.Post;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getListIsPublished();

    List<Post> findByUserName(String username);

    Post findByUserNameNumber(String username,Long number);

    List<Post> getHomeList();
}
