package com.ll.medium.domain.post.service;


import com.ll.medium.domain.member.entity.Member;
import com.ll.medium.domain.member.service.MemberService;
import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.domain.post.form.PostWriteForm;
import com.ll.medium.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final MemberService memberService;
    private final PostRepository postRepository;

    public Optional<Post> write(PostWriteForm postWriteForm){
        Post post = Post.builder()
                .title(postWriteForm.getTitle())
                .body(postWriteForm.getBody())
                .author(memberService.getMyUserWithAuthorities().get())
                .isPublished(postWriteForm.isPublished())
                .build();

        return Optional.of(postRepository.save(post));
    }
    public Page<Post> getList(int page,List<Sort.Order> sorts){

        Pageable pageable = PageRequest.of(page,10,Sort.by(sorts));
        return postRepository.getListIsPublished(pageable);
    }

    public Post getPost(Long id){
        return postRepository.findById(id).get();
    }
    public void delete(Long id){
        Post post = postRepository.findById(id).orElseThrow(NoSuchElementException::new);
        postRepository.delete(post);
    }
    public Page<Post> memberList(int page,String username){
        Pageable pageable = PageRequest.of(page+1, 10);
        return postRepository.findByUserName(pageable,username);
    }

    public Post memberListNum(String username, Long number) {
        return postRepository.findByUserNameNumber(username,number);
    }

    public List<Post> getHomeList() {
        return postRepository.getHomeList();
    }

    public void modify(Long id,PostWriteForm postWriteForm) {
        Post post = postRepository.findById(id).orElseThrow(NoSuchElementException::new);
        post.setBody(postWriteForm.getBody());
        post.setTitle(postWriteForm.getTitle());
        post.setPublished(postWriteForm.isPublished());
    }

    public Object getMyList(int page,String username) {
        Pageable pageable = PageRequest.of(page-1,10);
        return postRepository.getMyList(pageable,username);
    }

    public void vote(Member member, Long id) {
        Post post = postRepository.findById(id).get();
        post.getVoter().add(member);
    }

    public Boolean checkvote(Member member, Long id) {
        return postRepository.voteCheck(id,member);

    }

    public void canclevote(Long id,Member member) {
        Post post = postRepository.findById(id).get();
        post.getVoter().remove(member);
    }

    public Page<Post> search(List<String> kwTypes, String kw, int page, List<Sort.Order> sorts) {
        Pageable pageable = PageRequest.of(page,10,Sort.by(sorts));
        return postRepository.search(kwTypes,kw,pageable);
    }
}
