package com.ll.medium.global.jwt;

//@RequiredArgsConstructor
//public class JwtFilter extends GenericFilterBean {
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
//    public static final String AUTHORIZATION_HEADER = "Authorization";
//    private final TokenProvider tokenProvider;
//    private final RedisTemplate<String,String> redisTemplate;
//
//    // 실제 필터릴 로직
//    // 토큰의 인증정보를 SecurityContext에 저장하는 역할 수행
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        if(!httpServletRequest.getRequestURI().startsWith("/api/")){
//            filterChain.doFilter(httpServletRequest,servletResponse);
//            return;
//        }
//        String jwt = resolveToken(httpServletRequest);
//        String requestURI = httpServletRequest.getRequestURI();
//        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
//
//            String key = "JWT_TOKEN:" + tokenProvider.getAuthentication(jwt).getName();
//            String storedToken = redisTemplate.opsForValue().get(key);
//            if(redisTemplate.hasKey(key)&&storedToken!=null){
//                Authentication authentication = tokenProvider.getAuthentication(jwt);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
//            }
//        } else {
//            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
//        }
//
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//
//    // Request Header 에서 토큰 정보를 꺼내오기 위한 메소드
//    private String resolveToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//
//        return null;
//    }
//}

import com.ll.medium.domain.member.service.MemberService;
import com.ll.medium.global.rq.Rq;
import com.ll.medium.global.rsData.RsData;
import com.ll.medium.global.security.SecurityUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final Rq rq;
    private final MemberService memberService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        if (!request.getRequestURI().startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (List.of("/api/login", "/api/member/join").contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null) {
            String tokensStr = bearerToken.substring("Bearer ".length());
            String[] tokens = tokensStr.split("#", 2);
            String refreshToken = tokens[0];
            String accessToken = tokens.length == 2 ? tokens[1] : "";

            if (!accessToken.isBlank()) {
                if (!memberService.validateToken(accessToken)) {
                    RsData<String> rs = memberService.refreshAccessToken(refreshToken);
                    accessToken = rs.getData();
                    rq.setHeader("Authorization", "Bearer " + refreshToken + "#" + accessToken);
                }

                SecurityUser securityUser = memberService.getUserFromAccessToken(accessToken);
                rq.setLogin(securityUser);
            }
        } else {
            String accessToken = rq.getCookieValue("accessToken", "");
            if (!accessToken.isBlank()) {
                if (!memberService.validateToken(accessToken)) {
                    String refreshToken = rq.getCookieValue("refreshToken", "");

                    RsData<String> rs = memberService.refreshAccessToken(refreshToken);
                    accessToken = rs.getData();
                    rq.setCrossDomainCookie("accessToken", accessToken);
                }

                SecurityUser securityUser = memberService.getUserFromAccessToken(accessToken);
                rq.setLogin(securityUser);

            }
        }

        filterChain.doFilter(request, response);
    }
}