package com.lukaville.server.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nickolay on 21.10.15.
 */
public class ContentTypeDetector {
    public static final String DEFAULT_CONTENT_TYPE = "text/html";

    private static Map<String, String> contentTypes;

    static {
        contentTypes = new HashMap<String, String>();
        contentTypes.put("css", "text/css");
        contentTypes.put("html", "text/html");
        contentTypes.put("txt", "text/txt");
        contentTypes.put("js", "text/javascript");
        contentTypes.put("xml", "text/xml");
        contentTypes.put("gif", "image/gif");
        contentTypes.put("jpg", "image/jpeg");
        contentTypes.put("jpeg", "image/jpeg");
        contentTypes.put("png", "image/png");
        contentTypes.put("swf", "application/x-shockwave-flash");
    }

    public static String getContentType(String extension) {
        return contentTypes.getOrDefault(extension, DEFAULT_CONTENT_TYPE);
    }
}
