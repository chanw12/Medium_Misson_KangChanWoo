package com.ll.medium.domain.login.service;

import com.ll.medium.domain.member.form.MemberLoginForm;
import com.ll.medium.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final RedisTemplate<String,String> redisTemplate;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    public String login(MemberLoginForm memberLoginForm){

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(memberLoginForm.getUsername(), memberLoginForm.getPassword());

        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 해당 객체를 SecurityContextHolder에 저장하고
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
        String jwt = tokenProvider.createToken(authentication);
        redisTemplate.opsForValue().set("JWT_TOKEN:"+memberLoginForm.getUsername(),jwt);
        return jwt;
    }
    public void logout(){
        Object username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(redisTemplate.opsForValue().get("JWT_TOKEN:"+username)!=null){
            redisTemplate.delete("JWT_TOKEN:"+username);
        }

    }
}
