package com.lukaville.server.http;

/**
 * Created by nickolay on 21.10.15.
 */
public class ErrorGenerator {
    public static final String TEMPLATE = "<!DOCTYPE html><html><head> <title>{{description}}</title> <link href='https://fonts.googleapis.com/css?family=Ropa+Sans' rel='stylesheet' type='text/css'> <style type=\"text/css\"> html{padding: 0; margin: 0;}body{padding: 0; margin: 0; background: #2196F3; color: white; font-family: 'Ropa Sans', sans-serif;}h1{text-align: center; font-size: 12em; margin-bottom: 0.2em;}h2{text-align: center; font-size: 2.4em;}</style></head><body> <h1>{code}</h1> <h2>{description}</h2></body></html>";

    public static String generateErrorPage(int code, String description) {
        return TEMPLATE.replace("{description}", description).replace("{code}", String.valueOf(code));
    }
}
