package com.ll.medium.domain.comment.repository;

import com.ll.medium.domain.comment.entity.Comment;
import com.ll.medium.domain.member.repository.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long>, MemberRepositoryCustom {

}
