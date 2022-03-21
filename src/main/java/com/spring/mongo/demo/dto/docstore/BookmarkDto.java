package com.spring.mongo.demo.dto.docstore;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.spring.mongo.demo.util.WhiteSpaceRemovalDeserializer;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class BookmarkDto implements Serializable {

    String id;

    @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
    @NotEmpty
    @Size(max = 30, message = "{error.size.max}")
    @Pattern(regexp = "^\\w*([\\s-_]\\w*)*$", message = "{error.bookmark.name.invalid}")
    String name;

    @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
    @NotEmpty
    @Size(max = 100, message = "{error.size.max}")
    String path;

    @JsonDeserialize(using = WhiteSpaceRemovalDeserializer.class)
    @NotEmpty
    String user;
}
