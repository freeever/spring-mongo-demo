package com.spring.mongo.demo.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QueryUtils {

    /**
     * Pre-process the term used for a MongoDB full text search
     * to prevent unexpected result
     * @param term
     * @return
     */
    public static String parseTextSearchTerm(String term) {
        if (StringUtils.isBlank(term)) {
            return null;
        }

        List<String> words = splitText(term);

        return StringUtils.join(words, " ");
    }

    /**
     * Pre-process the term used for a MongoDB full text search
     * to prevent unexpected result
     * @param text
     * @return
     */
    public static List<String> splitText(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }

        List<String> words = Arrays.asList(StringUtils.split(text));
        if (words.size() > 1) {
            words = words.stream()
                    .filter(v -> v.length() != 1)
                    .collect(Collectors.toList());
        }

        return words;
    }

    /**
     * Process the special characters in the search term, AND
     * split it to multiple terms for regular expression match
     * @param term
     * @return
     */
    public static String parseTextSearchRegex(String term) {
        if (StringUtils.isBlank(term)) {
            return null;
        }

        return parseTextSearchTerm(term)
                .replaceAll("[\\W]", "\\\\$0")
                .replaceAll("\\\\ ", "|");
    }
}
