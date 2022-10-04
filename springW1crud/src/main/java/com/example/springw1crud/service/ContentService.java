package com.example.springw1crud.service;

import com.example.springw1crud.dto.CheckPasswordDto;
import com.example.springw1crud.dto.ContentChangeDto;
import com.example.springw1crud.dto.ContentRequestDto;
import com.example.springw1crud.entity.Content;
import com.example.springw1crud.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
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
        return contentRepository.save(content);
    }

    public List<Content> getContents() {    // 전체 게시글 조회
        return contentRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public Content getContent(Long id) {
        return contentRepository.findById(id).get();
    }

    @Transactional
    public Long deleteMemo(Long id) {   // 게시글 삭제
        contentRepository.deleteById(id);
        return id;
    }


    @Transactional
    public Map<String, Object> updateMemo(Long id, ContentChangeDto changeDto) {
        Content content = contentRepository.findById(id).get();
        Map<String, Object> map = new LinkedHashMap<>();

        if(content.getPassword().equals(changeDto.getRequestPassword())){
            ContentRequestDto requestDto = new ContentRequestDto(changeDto);
            this.update(id, requestDto);
            map.put("success", true);
            map.put("data", contentRepository.findById(id).get());
            map.put("error",null);
        }else{
            map.put("success", false);
            map.put("data", contentRepository.findById(id).get());
            map.put("error","비밀번호가 다릅니다.");
        }
        return map;
    }

    public boolean checkPassword(CheckPasswordDto passwordDto){
        Content content = contentRepository.findById(passwordDto.getId()).get();
//        log.info("save : {} / input : {}",content.getPassword(),passwordDto.getPassword());
        return content.getPassword().equals(passwordDto.getPassword());
    }
}
