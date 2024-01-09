package com.ll.medium.domain.initData;

import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.form.MemberJoinForm;
import com.ll.medium.domain.member.service.MemberService;
import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.domain.post.repository.PostRepository;
import com.ll.medium.domain.post.service.PostService;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

//@Configuration
//@Profile("!prod")
@Slf4j
@RequiredArgsConstructor
public class NotProd {
    private final MemberService memberService;
    private final PostService postService;
    private final PostRepository postRepository;

    @Bean
    @Order(3)
    public ApplicationRunner initNotProd() {
        return new ApplicationRunner() {
            @Transactional
            @Override
            public void run(ApplicationArguments args) throws Exception {
                // 유료 회원 생성
                for (int i = 0; i < 100; i++) {
                    MemberJoinForm memberJoinForm = new MemberJoinForm();
                    memberJoinForm.setUsername("user" + i);
                    memberJoinForm.setPassword("password" + i);
                    memberJoinForm.setPasswordconfirm("password" + i);
                    memberJoinForm.setNickname("nickname" + i);

                    Member member = memberService.join(memberJoinForm);
                    member.setPaid(true); // paid 속성 설정
                    // 유료 글 생성
                    Post post = Post.builder()
                            .title("Title " + i)
                            .body("Body content " + i)
                            .author(member)
                            .isPublished(true)
                            .isPaid(true)
                            .build();
                    postRepository.save(post);




                }

            }
        };
    }

    @PreDestroy
    public void cleanUp() {
        log.info("Cleaning up data before shutting down...");
        postService.deleteAll();
        memberService.deleteAll();
    }
}
