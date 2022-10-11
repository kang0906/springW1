package com.example.springw1crud.controller;

import com.example.springw1crud.dto.CommentRequestDto;
import com.example.springw1crud.entity.Comment;
import com.example.springw1crud.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/content/{id}/comment")    // 댓글 등록
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Comment createComment(@RequestBody CommentRequestDto requestDto, @PathVariable Long id){
        return commentService.addComment(id, requestDto.getComment());
    }

    @PutMapping("/content/{id}/comment")    // 댓글 등록
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Map<String, Object> updateComment(@RequestBody CommentRequestDto updateComment, @PathVariable Long id){
        return commentService.updateComment(id, updateComment);
    }

    @DeleteMapping("/comment/{id}") //게시글 삭제
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Long deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return id;
    }
}
