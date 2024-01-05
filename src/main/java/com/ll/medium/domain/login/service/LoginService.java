package com.ll.medium.domain.login.service;

//@Service
//@RequiredArgsConstructor
//public class LoginService {
//    private final TokenProvider tokenProvider;
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//    private final MemberRepository memberRepository;
//    private final Rq rq;
//    public String login(MemberLoginForm memberLoginForm){
//
//
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(memberLoginForm.getUsername(), memberLoginForm.getPassword());
//
//        // authenticate 메소드가 실행이 될 때 CustomUserDetailsService class의 loadUserByUsername 메소드가 실행
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        // 해당 객체를 SecurityContextHolder에 저장하고
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        Member member = memberRepository.findByUserName(memberLoginForm.getUsername()).get();
//
//        // authentication 객체를 createToken 메소드를 통해서 JWT Token을 생성
//        String jwt = tokenProvider.createToken(authentication);
//        return jwt;
//    }
//    public void logout(){
//        rq.setLogout();
//    }
//}
