package com.lukaville.server.http;

import com.lukaville.server.server.HttpServer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nickolay on 17.10.15.
 */
public class HttpHeader {
    public static final String HTTP_VERSION = "1.1";

    private int statusCode;
    private Map<String, String> headers = new HashMap<String, String>();

    public static Map<Integer, String> statusDescriptions = new HashMap<Integer, String>();

    // HTTP header names
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";
    public static final String HEADER_DATE = "Date";
    public static final String HEADER_SERVER = "SERVER";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_CONNECTION = "Connection";

    // HTTP statuses
    public static final int OK = 200;
    public static final int NOT_FOUND = 404;
    public static final int FOUND = 302;
    public static final int FORBIDDEN = 403;
    public static final int BAD_REQUEST = 400;

    static {
        statusDescriptions.put(OK, "OK");
        statusDescriptions.put(NOT_FOUND, "Not found");
        statusDescriptions.put(FOUND, "Found");
        statusDescriptions.put(FORBIDDEN, "Forbidden");
        statusDescriptions.put(BAD_REQUEST, "Bad request");
    }

    public HttpHeader(int statusCode) {
        this.statusCode = statusCode;
        addDefaultHeaders();
    }

    private void addDefaultHeaders() {
        addHeader(HEADER_DATE, (new Date()).toString());
        addHeader(HEADER_SERVER, HttpServer.SERVER_NAME);
        addHeader(HEADER_CONTENT_TYPE, ContentTypeDetector.DEFAULT_CONTENT_TYPE);
        addHeader(HEADER_CONNECTION, "close");
    }

    public String getHeaders() {
        final String mainHeader = String.format("HTTP/%s %d %s",
                HTTP_VERSION, statusCode, statusDescriptions.get(statusCode));

        StringBuilder sb = new StringBuilder();
        sb.append(mainHeader).append("\r\n");
        for(Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");;
        }

        return sb.toString();
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    @Override
    public String toString() {
        return getHeaders() + "\r\n";
    }
}
