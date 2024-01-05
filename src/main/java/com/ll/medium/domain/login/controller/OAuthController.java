package com.ll.medium.domain.login.controller;

import com.ll.medium.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class OAuthController {
    private final Rq rq;


    @GetMapping("/member/socialLogin")
    public String socialLogin(@RequestParam(required = false) String redirectUrl) throws IOException {
        if (rq.isFrontUrl(redirectUrl)) {
            rq.setCookie("redirectUrlAfterSocialLogin", redirectUrl, 60 * 10);
        }

        return "redirect:/oauth2/authorization/kakao";
    }

}
