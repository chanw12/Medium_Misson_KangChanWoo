package com.ll.medium.domain.post.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter@Setter
public class PostWriteForm {

    @NotEmpty(message = "제목은 필수 항목입니다")
    private String title;

    @NotEmpty(message = "본문은 필수 항목입니다")
    private String body;

    private boolean published;

    private boolean paid;

    private MultipartFile file;


}

