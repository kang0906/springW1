package com.example.springw1crud.service;

import com.example.springw1crud.dto.ContentRequestDto;
import com.example.springw1crud.entity.Content;
import com.example.springw1crud.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
