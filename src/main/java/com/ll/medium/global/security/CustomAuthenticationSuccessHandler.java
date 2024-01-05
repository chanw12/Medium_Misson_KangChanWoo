package com.ll.medium.global.security;

import com.ll.medium.global.app.AppConfig;
import com.ll.medium.global.jwt.TokenProvider;
import com.ll.medium.global.rq.Rq;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final Rq rq;
    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String redirectUrlAfterSocialLogin = rq.getCookieValue("redirectUrlAfterSocialLogin", "");
        if (rq.isFrontUrl(redirectUrlAfterSocialLogin)) {
            String accessToken = tokenProvider.genToken(rq.getMember(), AppConfig.getAccessTokenExpirationSec());

            rq.destroySession();
            rq.setCrossDomainCookie("accessToken", accessToken);
            rq.setCrossDomainCookie("RefreshToken",rq.getMember().getRefreshToken());
            rq.removeCookie("redirectUrlAfterSocialLogin");

            response.sendRedirect(redirectUrlAfterSocialLogin);
            return;
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}