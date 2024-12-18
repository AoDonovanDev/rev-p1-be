package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.repository.CommentRepository;

public class CommentService {
    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    
}
