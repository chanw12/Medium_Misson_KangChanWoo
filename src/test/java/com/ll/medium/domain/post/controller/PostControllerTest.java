package com.ll.medium.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.medium.domain.member.form.MemberJoinForm;
import com.ll.medium.domain.post.form.PostWriteForm;
import com.ll.medium.domain.post.repository.PostRepository;
import com.ll.medium.domain.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class PostControllerTest {


    @MockBean
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("/api/member/join")
    void join() throws Exception {



        PostWriteForm postWriteForm = new PostWriteForm();
        postWriteForm.setTitle("testTitle");
        postWriteForm.setBody("testBody");
        postWriteForm.setPublished(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/post/write")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers()
                        .content(objectMapper.writeValueAsString(memberJoinForm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("200"))
                .andExpect(jsonPath("$.msg").value("회원가입이 완료되었습니다."));



    }
}