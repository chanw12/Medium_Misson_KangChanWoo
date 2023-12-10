package com.ll.medium.domain.post.form;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class PostWriteForm {

    private String title;

    private String body;

    private boolean isPublished;


}

