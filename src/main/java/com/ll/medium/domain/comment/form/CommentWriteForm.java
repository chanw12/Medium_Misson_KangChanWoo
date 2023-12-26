package com.ll.medium.domain.comment.form;

import lombok.Getter;

@Getter
public class CommentWriteForm {
    private String body;
    private String username;
    private Long post_id;
}
