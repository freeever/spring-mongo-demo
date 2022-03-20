package com.spring.mongo.demo.rest;

import com.spring.mongo.demo.common.exception.DocStoreException;
import com.spring.mongo.demo.dto.docstore.BookmarkDto;
import com.spring.mongo.demo.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @GetMapping("/{user}")
    public List<BookmarkDto> findBookmarksBYUser(@PathVariable @NotBlank String user) {
        return this.bookmarkService.findBookmarksByUser(user);
    }

    @PostMapping
    public BookmarkDto createBookmark(@Valid @RequestBody BookmarkDto bookmarkDto) {
        log.info("Create new bookmark [{} - {}]", bookmarkDto.getName(), bookmarkDto.getPath());
        return this.bookmarkService.createBookmark(bookmarkDto);
    }

    @DeleteMapping("/{id}")
    public boolean deleteBookmarkById(@PathVariable @NotBlank String id) throws DocStoreException {
        return this.bookmarkService.deleteBookmarkById(id);
    }
}
