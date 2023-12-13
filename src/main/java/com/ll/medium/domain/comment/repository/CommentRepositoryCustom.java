package com.ll.medium.domain.comment.repository;

import com.ll.medium.domain.comment.entity.Comment;

import java.util.List;

public interface CommentRepositoryCustom {

    List<Comment> findByPostId(Long id);
}
