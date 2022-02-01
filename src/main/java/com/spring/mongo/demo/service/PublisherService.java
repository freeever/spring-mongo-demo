package com.spring.mongo.demo.service;

import com.spring.mongo.demo.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PublisherService {
    private final BookRepository bookRepository;
}
