package com.ll.medium.domain.post.controller;

import com.ll.medium.domain.post.form.PostWriteForm;
import com.ll.medium.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @PostMapping("/api/post/write")
    public ResponseEntity<?> write(@RequestBody PostWriteForm postWriteForm){

        return ResponseEntity.ok(postService.write(postWriteForm));
    }

    //내글 리스트
    @GetMapping("/api/post/myList")
    public ResponseEntity<?> myList(){
        return ResponseEntity.ok(postService.getMyList());
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
    @PostMapping("/api/post/{id}/modify")
    public ResponseEntity<?> modify(@PathVariable("id") Long id,@RequestBody PostWriteForm postWriteForm){
        postService.modify(id,postWriteForm);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/b/{username}")
    public ResponseEntity<?> getuserlist(@PathVariable("username") String username){
        return ResponseEntity.ok(postService.memberList(username));
    }

    @GetMapping("/api/b/{username}/{number}")
    public ResponseEntity<?> getuserlistnum(@PathVariable("username") String username
    ,@PathVariable("number") Long number){
        return ResponseEntity.ok(postService.memberListNum(username,number));
    }

    @GetMapping("/api/homelist")
    public ResponseEntity<?> gethomelist(){
        return ResponseEntity.ok(postService.getHomeList());
    }

    @GetMapping("/api/post/list")
    public ResponseEntity<?> getlist(){
        return ResponseEntity.ok(postService.getList());
    }

}
