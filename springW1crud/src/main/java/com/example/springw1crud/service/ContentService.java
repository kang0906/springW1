package com.example.springw1crud.service;

import com.example.springw1crud.dto.CheckPasswordDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ContentService {
    private final ContentRepository contentRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long update(Long id, ContentRequestDto requestDto) {
        Content content = contentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        content.update(requestDto);
        return content.getId();
    }

    @Transactional
    public Content createContent(ContentRequestDto requestDto){ // 게시글 작성
        Content content = new Content(requestDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        requestDto.setName(authentication.getName());
        return contentRepository.save(content);
    }

    public List<Content> getContents() {    // 전체 게시글 조회
        return contentRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public Content getContent(Long id) {    // 게시글 조회
        return contentRepository.findById(id).get();
    }

    @Transactional
    public Long deleteMemo(Long id) {   // 게시글 삭제
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Content content = contentRepository.getReferenceById(id);

        if(!content.getName().equals(authentication.getName())){
            throw new IllegalArgumentException();
        }

        List<Comment> comments = content.getComments();
        for (Comment comment : comments) {  // ** 글과 댓글이 함께 삭제되게 하기 ** -
            commentRepository.deleteById(comment.getId());
        }

        contentRepository.deleteById(id);
        return id;
    }


    @Transactional
    public Map<String, Object> updateContent(Long id, ContentChangeDto changeDto) { //게시글 수정
        Content content = contentRepository.findById(id).get();
        Map<String, Object> map = new LinkedHashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        changeDto.setName(authentication.getName());

        if(content.getName().equals(authentication.getName())){
            ContentRequestDto requestDto = new ContentRequestDto(changeDto);
            this.update(id, requestDto);
            map.put("success", true);
            map.put("data", contentRepository.findById(id).get());
            map.put("error",null);
        }else{
            map.put("success", false);
            map.put("data", contentRepository.findById(id).get());
            map.put("error","게시글 작성자만 수정할수있습니다.");
        }
        return map;
    }

    @Transactional
    public boolean createComment(){
        return true;
    }

//    public boolean checkPassword(CheckPasswordDto passwordDto){
//        Content content = contentRepository.findById(passwordDto.getId()).get();
////        log.info("save : {} / input : {}",content.getPassword(),passwordDto.getPassword());
//        return content.getPassword().equals(passwordDto.getPassword());
//    }
}
