package com.lukaville.server.http;

import com.sun.istack.internal.Nullable;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by nickolay on 21.10.15.
 */
public class HttpRequest {
    private String uri;

    public HttpRequest(String uri) {
        this.uri = uri;
    }

    @Nullable
    public String getPath() {
        return checkUri(uri);
    }

    @Nullable
    private static String checkUri(String uri) {
        try {
            uri = URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new Error(e);
        }

        if (uri.isEmpty() || uri.charAt(0) != '/') {
            return null;
        }

        return uri.replace('/', File.separatorChar);
    }
}
