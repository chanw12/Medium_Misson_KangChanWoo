package com.ll.medium.domain.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final RedisTemplate<String,String> redisTemplate;
    public void logout(){
        Object username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(redisTemplate.opsForValue().get("JWT_TOKEN:"+username)!=null){
            redisTemplate.delete("JWT_TOKEN:"+username);
        }

    }
}
