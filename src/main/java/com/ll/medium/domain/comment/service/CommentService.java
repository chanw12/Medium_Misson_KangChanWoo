package com.ll.medium.domain.comment.service;

import com.ll.medium.domain.comment.entity.Comment;
import com.ll.medium.domain.comment.form.CommentWriteForm;
import com.ll.medium.domain.comment.repository.CommentRepository;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.repository.MemberRepository;
import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    public void write(CommentWriteForm commentForm) {
        Post post = postRepository.findById(commentForm.getPost_id()).get();
        Member author = memberRepository.findByUserName(commentForm.getUsername()).get();
        Comment comment = Comment.builder()
                .body(commentForm.getBody())
                .post(post)
                .author(author).build();

        commentRepository.save(comment);
    }

    public List<Comment> get(Long postid) {
        return commentRepository.findByPostId(postid);
    }
}
