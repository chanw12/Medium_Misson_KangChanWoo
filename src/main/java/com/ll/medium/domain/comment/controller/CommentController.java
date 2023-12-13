package com.ll.medium.domain.comment.controller;

import com.ll.medium.domain.comment.entity.Comment;
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

}
