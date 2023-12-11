package com.ll.medium.domain.post.service;


import com.ll.medium.domain.member.service.MemberService;
import com.ll.medium.domain.post.entity.Post;
import com.ll.medium.domain.post.form.PostWriteForm;
import com.ll.medium.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
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
    public List<Post> getList(){
        return postRepository.getListIsPublished();
    }

    public Post getPost(Long id){
        return postRepository.findById(id).get();
    }
    public void delete(Long id){
        Post post = postRepository.findById(id).orElseThrow(NoSuchElementException::new);
        postRepository.delete(post);
    }
    public List<Post> memberList(String username){
        return postRepository.findByUserName(username);
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

    public Object getMyList(String username) {
        return postRepository.getMyList(username);
    }
}
