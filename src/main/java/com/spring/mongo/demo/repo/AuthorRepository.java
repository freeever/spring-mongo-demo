package com.spring.mongo.demo.repo;

import com.spring.mongo.demo.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String>  {
    
}
