package com.spring.mongo.demo.service;

import com.spring.mongo.demo.repo.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookService {
    private final AuthorRepository authorRepository;
}
