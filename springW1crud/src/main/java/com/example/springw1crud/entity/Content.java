package com.example.springw1crud.entity;

import com.example.springw1crud.dto.ContentRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor // 기본생성자를 만듭니다.
@Getter
//@Data
@Entity // 테이블과 연계됨을 스프링에게 알려줍니다.
public class Content extends Timestamped{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String name;

//    @Column(nullable = false)
//    @JsonIgnore
////    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    String password;

    @Column(nullable = false)
    String content;

//    public Content(String title, String name, String content){
//
//    }

    public Content(String title, String name, String content) {
        this.title = title;
        this.name = name;
        this.content = content;
    }

    public Content(Long id,String title,String name,String content) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.content = content;
    }

    public Content(ContentRequestDto requestDto){
        this.content = requestDto.getContent();
        this.name = requestDto.getName();
        this.title = requestDto.getTitle();
//        this.password = requestDto.getPassword();
    }

    public void update(ContentRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.name = requestDto.getName();
//        this.password = requestDto.getPassword();
        this.content = requestDto.getContent();
    }
}
