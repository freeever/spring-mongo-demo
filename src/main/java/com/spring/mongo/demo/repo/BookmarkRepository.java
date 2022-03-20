package com.spring.mongo.demo.repo;

import com.spring.mongo.demo.model.docstore.Bookmark;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends MongoRepository<Bookmark, String> {

    Bookmark findByNameAndUser(String name, String user);

    List<Bookmark> findByUser(String user);
}
