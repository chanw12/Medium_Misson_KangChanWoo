package com.ll.medium.domain.comment.controller;

import com.ll.medium.domain.comment.entity.Comment;
import com.ll.medium.domain.comment.form.CommentModiForm;
import com.ll.medium.domain.comment.form.CommentWriteForm;
import com.ll.medium.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/api/comment/write")
    public ResponseEntity<?> write(@RequestBody CommentWriteForm commentForm){
        commentService.write(commentForm);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/comment/get/{postid}")
    public ResponseEntity<?> get(@PathVariable("postid") Long postid){
        List<Comment> comments = commentService.get(postid);
        return ResponseEntity.ok().body(comments);
    }

    @DeleteMapping("/api/comment/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        commentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/comment/modify/{id}")
    public ResponseEntity<?> modify(@PathVariable("id") Long id,@RequestBody CommentModiForm commentModiForm){
        Comment comment = commentService.modify(id, commentModiForm);
        return ResponseEntity.ok().body(comment);
    }

}
