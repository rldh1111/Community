package com.blog.commuity.domain.post.presentation;


import com.blog.commuity.domain.post.dto.PostReqDto;
import com.blog.commuity.domain.post.application.PostService;
import com.blog.commuity.domain.post.dto.PostResDto;
import com.blog.commuity.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "post 하나만 가져오기")
    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        PostResDto post = postService.getPost(id);
        return ResponseEntity.ok(post);
    }


    @Operation(summary = "post 리스트 가져오기")
    @GetMapping("/posts")
    public ResponseEntity<?> getPostList(@PageableDefault(direction = Sort.Direction.DESC, size = 10, sort = "id") Pageable page) {
        return ResponseEntity.ok(postService.getPostList(page));
    }


    @Operation(summary = "post 등록하기")
    @PostMapping("/post/create")
    public ResponseEntity<?> register(@RequestPart(value = "post") @Valid PostReqDto postReqDto, @RequestPart(value = "file") MultipartFile file, @AuthenticationPrincipal User user) throws IOException {
        PostResDto post = postService.register(postReqDto, file, user.getId());
        return ResponseEntity.ok(post);
    }


    @Operation(summary = "post 삭제하기")
    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> editPost(@PathVariable Long id, @AuthenticationPrincipal User user) {
        postService.remove(id, user.getId());
        return ResponseEntity.ok(id + "번을 삭제완료했습니다.");
    }


    @Operation(summary = "post 수정하기")
    @PutMapping("/post/{id}")
    public ResponseEntity<?> editPost(@PathVariable Long id, @RequestPart(value = "post") @Valid PostReqDto postReqDto, @RequestPart(value = "file") MultipartFile file, @AuthenticationPrincipal User user) {
        PostResDto edit = postService.edit(id, postReqDto, user.getId());
        return ResponseEntity.ok(edit);
    }
}
