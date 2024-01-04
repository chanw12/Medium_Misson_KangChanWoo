package com.ll.medium.domain.member.controller;

import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.form.MemberJoinForm;
import com.ll.medium.domain.member.service.MemberService;
import com.ll.medium.global.exception.NotValidFormException;
import com.ll.medium.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final Rq rq;


    @PostMapping("/join")
    public ResponseEntity<Member> join(@Valid @RequestBody MemberJoinForm memberJoinForm, BindingResult bindingResult){
        if(!memberJoinForm.getPassword().equals(memberJoinForm.getPasswordconfirm())){
           ResponseEntity.badRequest();
        }
        if(bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("////");
            }
            throw new NotValidFormException(errorMessage.toString());
        }

        return ResponseEntity.ok(memberService.join(memberJoinForm));

    }


    @GetMapping(value = "/user", consumes = MimeTypeUtils.ALL_VALUE)
    public ResponseEntity<Member> getMyUserInfo() {
        return ResponseEntity.ok(rq.getMember());
    }



    @GetMapping("/user/{username}")
    public ResponseEntity<Member> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(memberService.getUserWithAuthorities(username).get());
    }










}
