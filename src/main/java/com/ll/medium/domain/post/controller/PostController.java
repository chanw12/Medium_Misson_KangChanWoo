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
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> detail(@PathVariable("id") Long id) throws AccessDeniedException {
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

    //전체 글 목록  //hit : 조회수 create: 생성일 likeCount:추천순
    @GetMapping("/api/post/list")
    public ResponseEntity<Page<Post>> getlist(@RequestParam(value = "kwType",defaultValue = "title,body") List<String> kwTypes,
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "sortCode",defaultValue = "createDesc") String sortCode,
            @RequestParam(value = "kw",defaultValue = "") String kw
    ){
        List<Sort.Order> sorts = extracted(sortCode);
        Map<String, Boolean> kwTypesMap = kwTypes
                .stream()
                .collect(Collectors.toMap(
                        kwType -> kwType,
                        kwType -> true
                ));
        System.out.println("----------------------");
        System.out.println(sorts.get(0));
        if(kw.isEmpty()){
            System.out.println("first");
            return ResponseEntity.ok(postService.getList(page,sorts));
        }else{
            System.out.println("second");
            return ResponseEntity.ok(postService.search(kwTypes,kw,page,sorts));

        }
    }

    private static List<Sort.Order> extracted(String sortCode) {
        List<Sort.Order> sorts = new ArrayList<>();
        switch (sortCode) {
            case "createDesc" -> sorts.add(Sort.Order.desc("createDate"));
            case "createAsc" -> sorts.add(Sort.Order.asc("createDate"));
            case "likeCountDesc" -> sorts.add(Sort.Order.desc("voter.size"));
            case "likeCountAsc" -> sorts.add(Sort.Order.asc("voter.size"));
        }
        return sorts;
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
    



}
