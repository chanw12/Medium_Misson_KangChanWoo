package com.ll.medium.domain.comment.service;

import com.ll.medium.domain.comment.entity.Comment;
import com.ll.medium.domain.comment.form.CommentModiForm;
import com.ll.medium.domain.comment.form.CommentWriteForm;
import com.ll.medium.domain.comment.repository.CommentRepository;
import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.repository.MemberRepository;
import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.domain.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    @Transactional
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

    @Transactional
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id).get();
        commentRepository.delete(comment);
    }
    @Transactional
    public Comment modify(Long id, CommentModiForm commentModiForm) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Comment를 찾을 수 없습니다")
        );
        comment.setBody(commentModiForm.getBody());
        System.out.println(comment.getBody());
        return comment;
    }
}
