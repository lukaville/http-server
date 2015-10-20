package com.lukaville.server.http;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nickolay on 17.10.15.
 */
public class HttpResponse {
    public static final String HTTP_VERSION = "1.1";
    private int statusCode;
    private Map<String, String> headers = new HashMap<String, String>();
    private String body;

    private static Map<Integer, String> statusDescriptions = new HashMap<Integer, String>();
    static {
        statusDescriptions.put(200, "OK");
        statusDescriptions.put(404, "Not found");
    }

    public HttpResponse(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getHeaders() {
        String mainHeader = String.format("HTTP/%s %d %s",
                HTTP_VERSION, statusCode, statusDescriptions.get(statusCode));

        StringBuilder sb = new StringBuilder();
        sb.append(mainHeader).append("\n");
        for(Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");;
        }

        return sb.toString();
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    @Override
    public String toString() {
        return getHeaders() + "\n" + body;
    }
}
