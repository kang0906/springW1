package com.example.springw1crud.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContentRequestDto {
    private String title;
    private String name;
    private String password;
    private String content;

    public ContentRequestDto(ContentChangeDto changeDto) {
        this.title = changeDto.getTitle();
        this.name = changeDto.getName();
        this.password = changeDto.getPassword();
        this.content = changeDto.getContent();
    }
}
