package com.spring.mongo.demo.service;

import com.spring.mongo.demo.common.error.MessageCode;
import com.spring.mongo.demo.common.exception.DocStoreDataAccessException;
import com.spring.mongo.demo.common.exception.DocStoreException;
import com.spring.mongo.demo.dto.docstore.BookmarkDto;
import com.spring.mongo.demo.model.docstore.Bookmark;
import com.spring.mongo.demo.repo.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final ModelMapper modelMapper;

    public BookmarkDto createBookmark(BookmarkDto bookmarkDto) {
        log.info("BookmarkService::createBookmark()");
        // The combination of name and user must be unique
        Bookmark bookmark = this.bookmarkRepository.findByNameAndUser(bookmarkDto.getName(), bookmarkDto.getUser());
        if (bookmark == null) {
            bookmark = this.modelMapper.map(bookmarkDto, Bookmark.class);
            bookmark = this.bookmarkRepository.save(bookmark);
        }

        return this.modelMapper.map(bookmark, BookmarkDto.class);
    }

    public List<BookmarkDto> findBookmarksByUser(String user) {
        log.info("BookmarkService::findBookmarksByUser() - user[{}]", user);
        List<Bookmark> bookmarks = this.bookmarkRepository.findByUser(user);
        return bookmarks.stream()
                .map(bookmark -> modelMapper.map(bookmark, BookmarkDto.class))
                .collect(Collectors.toList());
    }

    public boolean deleteBookmarkById(String id) throws DocStoreDataAccessException {
        log.info("BookmarkService::deleteBookmarkById() - id[{}]", id);
        Optional<Bookmark> bookmark = this.bookmarkRepository.findById(id);
        if (bookmark.isPresent()) {
            this.bookmarkRepository.deleteById(id);
            return true;
        }

        DocStoreDataAccessException ex = new DocStoreDataAccessException();
        ex.setMessageCode(MessageCode.ERR_BOOKMARK_NOT_FOUND);
        throw ex;
    }
}
