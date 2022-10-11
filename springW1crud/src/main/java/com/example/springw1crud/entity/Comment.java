package com.example.springw1crud.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class Comment {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    Long id;

    @Column(nullable = false)
    String comment;

    @Column(nullable = false)
    String name;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "CONTENT_ID")
    Content content;

    public Comment(String comment, Content content, String name) {
        this.comment = comment;
        this.content = content;
        this.name = name;
    }

    public void update(String comment){
        this.comment = comment;
    }
}
