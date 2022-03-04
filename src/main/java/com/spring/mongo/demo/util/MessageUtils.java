package com.spring.mongo.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageUtils {

    private static Logger logger = LoggerFactory.getLogger(MessageUtils.class);

    private final static String DEFAULT_BUNDLE = "messages";

    private MessageUtils() {
    }

    public static String getMessageByKey(String msgKey, Object... params) {
        return getMessage(DEFAULT_BUNDLE, msgKey, Locale.ENGLISH, params);
    }

    public static String getMessage(String bundleName, String msgKey, Object... params) {

        ResourceBundle messages = ResourceBundle.getBundle(bundleName, Locale.ENGLISH);

        if (messages == null) {
            return "???" + bundleName + "??? bundle is not inited";
        }

        String msg = messages.getString(msgKey);

        if (msg == null) {
            logger.error("Missing resource key " + msgKey);
            return "???" + msgKey + "??? Not Found";
        }

        if (params != null && params.length > 0) {
            return format(msg, params);
        }

        return msg;
    }

    /**
     * Build message text with arguments
     * @param pattern
     * @param arguments
     * @return
     */
    private static String format(String pattern, Object ... arguments) {

        if (pattern == null) {
            return null;
        }
        return MessageFormat.format(pattern.replaceAll("'", "''"), arguments);
    }
}
