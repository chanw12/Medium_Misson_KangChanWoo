package com.ll.medium.domain.login.controller;

import com.ll.medium.domain.login.service.LoginService;
import com.ll.medium.domain.member.form.MemberLoginForm;
import com.ll.medium.global.jwt.JwtFilter;
import com.ll.medium.global.jwt.TokenDto;
import com.ll.medium.global.jwt.TokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisTemplate<String,String> redisTemplate;
    private final LoginService loginService;
    @PostMapping("/login")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody MemberLoginForm memberLoginForm) {
        String jwt = loginService.login(memberLoginForm);
        HttpHeaders httpHeaders = new HttpHeaders();
        // response header에 jwt token에 넣어줌
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        // tokenDto를 이용해 response body에도 넣어서 리턴
        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(Principal principal){
        loginService.logout();
        return ResponseEntity.ok().build();
    }
}