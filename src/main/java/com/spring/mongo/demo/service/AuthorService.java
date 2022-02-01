package com.spring.mongo.demo.service;

import com.spring.mongo.demo.repo.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorService {

    private final PublisherRepository publisherRepository;
}
