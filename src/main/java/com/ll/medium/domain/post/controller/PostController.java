package com.ll.medium.domain.post.controller;

import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.service.MemberService;
import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.domain.post.form.PostWriteForm;
import com.ll.medium.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;
    private final MemberService memberService;


    @PostMapping("/api/post/write")
    public ResponseEntity<?> write(@Valid @RequestBody PostWriteForm postWriteForm){

        return ResponseEntity.ok(postService.write(postWriteForm));
    }

    //내글 리스트
    @GetMapping("/api/post/myList")
    public ResponseEntity<?> myList(@RequestParam(value = "page",defaultValue = "0")int page,Principal principal){
        return ResponseEntity.ok(postService.getMyList(page,principal.getName()));
    }


    //글 상세 내용 조회
    @GetMapping("/api/post/{id}")
    public ResponseEntity<?> detail(@PathVariable("id") Long id){
        return ResponseEntity.ok(postService.getPost(id));
    }

    //글삭제
    @DeleteMapping("/api/post/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        postService.delete(id);
        return ResponseEntity.ok().build();
    }
    //글 수정
    @PutMapping ("/api/post/{id}/modify")
    public ResponseEntity<?> modify(@PathVariable("id") Long id,@RequestBody PostWriteForm postWriteForm){
        postService.modify(id,postWriteForm);
        return ResponseEntity.ok().build();
    }
    //특정 유저가 쓴 글 모아보기
    @GetMapping("/api/b/{username}")
    public ResponseEntity<Page<Post>> getuserlist(@RequestParam(value = "page",defaultValue = "0")int page,@PathVariable("username") String username){
        return ResponseEntity.ok(postService.memberList(page,username));
    }

    //특정 유저가 쓴 글 상세보기
    @GetMapping("/api/b/{username}/{number}")
    public ResponseEntity<?> getuserlistnum(@PathVariable("username") String username
    ,@PathVariable("number") Long number){
        return ResponseEntity.ok(postService.memberListNum(username,number));
    }

    //홈화면에 최신글 30개 띄우기
    @GetMapping("/api/homelist")
    public ResponseEntity<List<Post>> gethomelist(){
        return ResponseEntity.ok(postService.getHomeList());
    }

    //전체 글 목록
    @GetMapping("/api/post/list")
    public ResponseEntity<Page<Post>> getlist(@RequestParam(value = "page",defaultValue = "0") int page){
        System.out.println("-0------------------");
        System.out.println(page);
        return ResponseEntity.ok(postService.getList(page));
    }

    @PostMapping("/api/post/{id}/like")
    public ResponseEntity<?> PostVote(@PathVariable("id") Long id){
        Member member = memberService.getMyUserWithAuthorities().get();
        postService.vote(member,id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/post/{id}/check-like")
    public ResponseEntity<?> check(@PathVariable("id")Long id){
        Member member = memberService.getMyUserWithAuthorities().get();
        Boolean checkvote = postService.checkvote(member, id);
        return ResponseEntity.ok().body(checkvote);

    }


    @DeleteMapping("/api/post/{id}/canCellike")
    public ResponseEntity<?> votecancle(@PathVariable("id")Long id){
        Member member = memberService.getMyUserWithAuthorities().get();

        postService.canclevote(id,member);
        return ResponseEntity.ok().build();

    }

//    @GetMapping("/search")
//    public ResponseEntity<List<Post>> search(@RequestParam List<String> keywords){
//        postService.search(keywords);
//    }



}
