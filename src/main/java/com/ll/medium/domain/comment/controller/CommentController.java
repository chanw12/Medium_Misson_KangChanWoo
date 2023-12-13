package com.ll.medium.domain.comment.controller;

import com.ll.medium.domain.comment.form.CommentWriteForm;
import com.ll.medium.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/api/comment/write")
    public ResponseEntity<?> write(@RequestBody CommentWriteForm commentForm){
        commentService.write(commentForm);
        return ResponseEntity.ok().build();
    }
}
