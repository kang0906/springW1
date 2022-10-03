package com.example.springw1crud.repository;

import com.example.springw1crud.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long>{
    List<Content> findAllByOrderByModifiedAtDesc();
}
