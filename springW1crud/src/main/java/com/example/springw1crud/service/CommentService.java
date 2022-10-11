package com.example.springw1crud.service;

import com.example.springw1crud.dto.CommentRequestDto;
import com.example.springw1crud.dto.ContentChangeDto;
import com.example.springw1crud.dto.ContentRequestDto;
import com.example.springw1crud.entity.Comment;
import com.example.springw1crud.entity.Content;
import com.example.springw1crud.repository.CommentRepository;
import com.example.springw1crud.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ContentRepository contentRepository;

    @Transactional
    public Comment addComment(Long id, String comment){
        Content content = contentRepository.findById(id).get();
        Comment comment1 = new Comment(comment, content, SecurityContextHolder.getContext().getAuthentication().getName());
        commentRepository.save(comment1);
        return comment1;
    }

    @Transactional
    public Map<String, Object> updateComment(Long id , CommentRequestDto updateComment) { //게시글 수정
        Comment comment = commentRepository.findById(id).get();
        Map<String, Object> map = new LinkedHashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(comment.getName().equals(authentication.getName())){
            comment.update(updateComment.getComment());
            map.put("success", true);
            map.put("data", comment.getComment());
            map.put("error",null);
        }else{
            map.put("success", false);
            map.put("data", commentRepository.findById(id).get().getComment());
            map.put("error","댓글 작성자만 수정할수있습니다.");
        }
        return map;
    }

    @Transactional
    public Long deleteComment(Long id) {   // 댓글 삭제
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Comment comment = commentRepository.getReferenceById(id);

        if(!comment.getName().equals(authentication.getName())){
            throw new IllegalArgumentException();
        }
        commentRepository.deleteById(id);
        return id;
    }
}
