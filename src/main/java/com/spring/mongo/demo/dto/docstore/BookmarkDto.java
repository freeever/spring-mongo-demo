package com.spring.mongo.demo.dto.docstore;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class BookmarkDto implements Serializable {

    String id;

    @NotBlank
    @Size(max = 30, message = "{error.size.max}")
    @Pattern(regexp = "^\\w*([\\s-_]\\w*)*$", message = "{error.bookmark.name.invalid}")
    String name;

    @NotBlank
    @Size(max = 100, message = "{error.size.max}")
    String path;

    @NotBlank
    String user;
}
