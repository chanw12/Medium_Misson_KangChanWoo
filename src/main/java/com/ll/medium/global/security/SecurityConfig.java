package com.ll.medium.global.security;

import com.ll.medium.global.jwt.JwtAccessDeniedHandler;
import com.ll.medium.global.jwt.JwtAuthenticationEntryPoint;
import com.ll.medium.global.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final JwtFilter jwtFilter;

    // PasswordEncoder는 BCryptPasswordEncoder를 사용




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        sessionManagement ->
                                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                // enable h2-console
                .headers(headers ->
                        headers
                                .frameOptions(frameOptions ->
                                        frameOptions
                                                .sameOrigin()
                                )
                )
                .oauth2Login(
                        oauth2Login -> oauth2Login.successHandler(customAuthenticationSuccessHandler)
                )
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/confirm","/member/socialLogin","/api/**","/login/oauth2/code/kakao","/api/logout","/api/login","/api/member/join","/favicon.ico","/api/homelist","/api/post/list","/api/upload")
                                .permitAll()
//                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig class 적용


        return httpSecurity.build();
    }

}
