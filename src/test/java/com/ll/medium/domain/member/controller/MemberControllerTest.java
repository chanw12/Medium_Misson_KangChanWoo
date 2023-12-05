package com.ll.medium.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.medium.domain.member.form.MemberJoinForm;
import com.ll.medium.domain.member.repository.MemberRepository;
import com.ll.medium.domain.member.service.MemberService;
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
class MemberControllerTest {

    @MockBean
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @Test
    void join() throws Exception {


        MemberJoinForm memberJoinForm = new MemberJoinForm();
        memberJoinForm.setUsername("testUser");
        memberJoinForm.setPassword("testPassword");
        memberJoinForm.setPasswordconfirm("testPassword");



        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberJoinForm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("200"))
                .andExpect(jsonPath("$.msg").value("회원가입이 완료되었습니다."));



    }
}