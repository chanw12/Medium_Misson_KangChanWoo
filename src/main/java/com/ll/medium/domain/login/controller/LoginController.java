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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberLoginForm.getUsername(), memberLoginForm.getPassword());

        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 해당 객체를 SecurityContextHolder에 저장하고
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        // response header에 jwt token에 넣어줌
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        redisTemplate.opsForValue().set("JWT_TOKEN:"+memberLoginForm.getUsername(),jwt);

        // tokenDto를 이용해 response body에도 넣어서 리턴
        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(Principal principal){
        loginService.logout();
        System.out.println(principal);
        return ResponseEntity.ok().build();
    }
}