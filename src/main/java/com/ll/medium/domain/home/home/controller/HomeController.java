package com.ll.medium.domain.home.home.controller;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final AmazonS3 amazonS3;

    @GetMapping("/")
    public String showMain(Principal principal) {
        System.out.println(principal.getName());
        return "domain/home/home/main";
    }



}
