package com.lukaville.server.http;

import com.sun.istack.internal.Nullable;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

/**
 * Created by nickolay on 21.10.15.
 */
public class HttpRequest {
    public static final String URL_CHARSET = "UTF-8";

    public enum Method {
        GET,
        HEAD
    }

    private String uri;
    private Method method;
    private boolean isValidRequest = true;

    public HttpRequest(boolean isValidRequest) {
        this.isValidRequest = isValidRequest;
    }

    public HttpRequest(Method method, String uri) {
        this.method = method;
        this.uri = uri;
    }

    public boolean isValid() {
        return isValidRequest;
    }

    @Nullable
    public String getPath() {
        return checkUri(uri);
    }

    public Method getMethod() {
        return method;
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

        if (uri.contains("/.") || uri.contains("./") || uri.charAt(uri.length() - 1) == '.') {
            return null;
        }

        return uri.replace('/', File.separatorChar);
    }

    @Nullable
    public static HttpRequest fromString(String request) throws Exception {
        Method method = parseMethod(request);
        if (method == null) {
            return new HttpRequest(false);
        }

        return new HttpRequest(method, parseUri(request));
    }

    @Nullable
    private static Method parseMethod(String request) {
        String method = request.substring(0, request.indexOf(" ")).toUpperCase();

        Method parsedMethod = null;
        for(Method m : Method.values()) {
            if (method.equals(m.name())) {
                parsedMethod = m;
            }
        }

        return parsedMethod;
    }

    @Nullable
    private static String parseUri(String request) throws UnsupportedEncodingException {
        int uriStart = request.indexOf(" ") + 1;
        String uri = request.substring(uriStart, request.indexOf(" ", uriStart));

        int queryStart = uri.indexOf('?');
        if (queryStart > -1) {
            uri = uri.substring(0, queryStart);
        }

        return URLDecoder.decode(uri, URL_CHARSET);
    }
}
