package com.jongmin.sns.controller;

import com.jongmin.sns.dto.PostDto;
import com.jongmin.sns.dto.request.post.PostCommentRequest;
import com.jongmin.sns.dto.request.post.PostCreateRequest;
import com.jongmin.sns.dto.request.post.PostModifyRequest;
import com.jongmin.sns.dto.response.Response;
import com.jongmin.sns.dto.response.post.CommentResponse;
import com.jongmin.sns.dto.response.post.PostResponse;
import com.jongmin.sns.dto.security.UserPrincipal;
import com.jongmin.sns.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/v1/posts")
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<Void> create(@RequestBody PostCreateRequest request,
                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        postService.create(request.toDto(userPrincipal.toDto()));

        return Response.success();
    }

    @PutMapping("/{postId}")
    public Response<PostResponse> modify(@RequestBody PostModifyRequest request,
                                         @AuthenticationPrincipal UserPrincipal userPrincipal,
                                         @PathVariable Long postId) {
        PostDto dto = postService.modify(postId, request.toDto(userPrincipal.toDto()));
        return Response.success(PostResponse.fromDto(dto));
    }

    @DeleteMapping("/{postId}")
    public Response<Void> delete(@PathVariable Long postId,
                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        postService.delete(userPrincipal.getUsername(), postId);
        return Response.success();
    }

    @GetMapping
    public Response<Page<PostResponse>> list(Pageable pageable) {
        return Response.success(postService.list(pageable).map(PostResponse::fromDto));
    }

    @GetMapping("/my")
    public Response<Page<PostResponse>> myList(Pageable pageable,
                                               @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return Response.success(postService.myList(userPrincipal.getUsername(), pageable).map(PostResponse::fromDto));
    }

    @PostMapping("/{postId}/likes")
    public Response<Void> like(@PathVariable Long postId,
                               @AuthenticationPrincipal UserPrincipal userPrincipal) {
        postService.like(postId, userPrincipal.getUsername());
        return Response.success();
    }

    @GetMapping("/{postId}/likes")
    public Response<Long> likeCount(@PathVariable Long postId) {
        postService.likeCount(postId);
        return Response.success(postService.likeCount(postId));
    }

    @PostMapping("/{postId}/comments")
    public Response<Void> comment(@PathVariable Long postId,
                                  @RequestBody PostCommentRequest request,
                                  @AuthenticationPrincipal UserPrincipal userPrincipal) {
        postService.comment(postId, userPrincipal.getUsername(), request.getComment());
        return Response.success();
    }

    @GetMapping("/{postId}/comments")
    public Response<Page<CommentResponse>> comment(@PathVariable Long postId, Pageable pageable) {
        return Response.success(postService.getComments(postId, pageable).map(CommentResponse::fromDto));
    }

}
