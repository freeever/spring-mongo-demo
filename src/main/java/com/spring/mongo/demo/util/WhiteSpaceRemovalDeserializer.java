package com.spring.mongo.demo.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class WhiteSpaceRemovalDeserializer extends StringDeserializer {
    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String value = super.deserialize(jp, ctxt);
        return StringUtils.strip(value);
    }
}
