package com.example.springw1crud.dto;

import lombok.Data;

@Data
public class ContentChangeDto {
    private String title;
    private String name;
    private String password;
    private String content;
    private String requestPassword;
}
