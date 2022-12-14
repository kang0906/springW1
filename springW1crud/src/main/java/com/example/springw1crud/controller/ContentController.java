package com.example.springw1crud.controller;

import com.example.springw1crud.dto.CheckPasswordDto;
import com.example.springw1crud.dto.ContentChangeDto;
import com.example.springw1crud.dto.ContentRequestDto;
import com.example.springw1crud.entity.Content;
import com.example.springw1crud.repository.ContentRepository;
import com.example.springw1crud.service.ContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ContentController {
    private final ContentRepository contentRepository;
    private final ContentService contentService;

//    @PostMapping("/content")    //게시글 작성
//    public Content createContent(@RequestBody ContentRequestDto requestDto){
//        Content content = new Content(requestDto);
//        return contentRepository.save(content);
//    }

//    @PostMapping("/content")    //게시글 작성
//    public Map<String, Object> createContent(@RequestBody ContentRequestDto requestDto){
//        Map<String, Object> map = new LinkedHashMap<>();
//        Content content = new Content(requestDto);
//        map.put("success",true);
//        map.put("data",contentRepository.save(content));
//        return map;
//    }

//    @PostMapping("/content")    //게시글 작성
//    public ModelAndView createContent(@RequestBody ContentRequestDto requestDto){
//        Content content = new Content(requestDto);
//        ModelAndView mv = new ModelAndView();
//        mv.addObject("data",contentRepository.save(content));
//        mv.addObject("success",true);
//        return mv;
//    }
    @PostMapping("/content")    //게시글 작성 controller service 분리
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Map<String, Object> createContent(@RequestBody ContentRequestDto requestDto){
        Map<String, Object> map = new LinkedHashMap<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("게시글등록 유저 : {}",authentication.getName());
        requestDto.setName(authentication.getName());
        map.put("success",true);
        map.put("data",contentService.createContent(requestDto));
        map.put("error",null);


        return map;
    }

//    @GetMapping("/content")   //전체 게시글 조회
//    public List<Content> getContents() {
//        return contentRepository.findAllByOrderByModifiedAtDesc();
//    }

//    @GetMapping("/content")   //전체 게시글 조회
//    public Map<String, Object> getContents() {
//        Map<String, Object> map = new LinkedHashMap<>();
//        map.put("success",true);
//        map.put("data",contentRepository.findAllByOrderByModifiedAtDesc());
//        map.put("error",false);
//        return map;
//    }

    @GetMapping("/contents")   //전체 게시글 조회
    public Map<String, Object> getContents() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("success",true);
        map.put("data",contentService.getContents());
        map.put("error",false);
        return map;
    }

//    @GetMapping("/content/{id}")   //게시글 조회
//    public Content getContent(@PathVariable Long id) {
//        Content content = contentRepository.findById(id).get();
//        log.info("password : {}",content.getPassword());
//        return contentRepository.findById(id).get();
//    }

    @GetMapping("/content/{id}")   //게시글 조회
    public Content getContent(@PathVariable Long id) {
//        Content content = contentRepository.findById(id).get();
        Content con = contentService.getContent(id);
//        log.info("content 객체 = {}",con);
        return contentService.getContent(id);
    }

//    @DeleteMapping("/content/{id}") //게시글 삭제
//    public Long deleteMemo(@PathVariable Long id) {
//        contentRepository.deleteById(id);
//        return id;
//    }

    @DeleteMapping("/content/{id}") //게시글 삭제
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Long deleteMemo(@PathVariable Long id) {
        contentService.deleteMemo(id);
        return id;
    }

//    @PutMapping("/content/{id}")    //게시글 수정
//    public Map<String, Object> updateMemo(@PathVariable Long id, @RequestBody ContentChangeDto changeDto) {
//        Content content = contentRepository.findById(id).get();
//        Map<String, Object> map = new LinkedHashMap<>();
//
//        if(content.getPassword().equals(changeDto.getRequestPassword())){
//            ContentRequestDto requestDto = new ContentRequestDto(changeDto);
//            contentService.update(id, requestDto);
//            map.put("success", true);
//            map.put("data", contentRepository.findById(id).get());
//            map.put("error",null);
//        }else{
//            map.put("success", false);
//            map.put("data", contentRepository.findById(id).get());
//            map.put("error","비밀번호가 다릅니다.");
//        }
//        return map;
//    }

    @PutMapping("/content/{id}")    //게시글 수정
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Map<String, Object> updateContent(@PathVariable Long id, @RequestBody ContentChangeDto changeDto) {
        return contentService.updateContent(id, changeDto);
    }

//    @PostMapping("/password")   // 비밀번호 확인
//    public Map<String, Object> checkPassword(@RequestBody CheckPasswordDto passwordDto){
//        Content content = contentRepository.findById(passwordDto.getId()).get();
//        Map<String, Object> map = new LinkedHashMap<>();
//        log.info("save : {} / input : {}",content.getPassword(),passwordDto.getPassword());
//        if(content.getPassword().equals(passwordDto.getPassword())){
//            map.put("success",true);
//            map.put("data",true);
//            map.put("error",null);
//            return map;
//        }else{
//            map.put("success",true);
//            map.put("data",false);
//            map.put("error",null);
//            return map;
//        }
//    }
//    @PostMapping("/password")   // 비밀번호 확인
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
//    public Map<String, Object> checkPassword(@RequestBody CheckPasswordDto passwordDto){
//        Map<String, Object> map = new LinkedHashMap<>();
//        if(contentService.checkPassword(passwordDto)){
//            map.put("success",true);
//            map.put("data",true);
//            map.put("error",null);
//            return map;
//        }else{
//            map.put("success",true);
//            map.put("data",false);
//            map.put("error",null);
//            return map;
//        }
//    }
}
