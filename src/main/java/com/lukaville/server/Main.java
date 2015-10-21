package com.lukaville.server;

import com.lukaville.server.model.Preferences;
import io.netty.util.ResourceLeakDetector;

public class Main {

    public static final String STARTING_SERVER_MESSAGE = "Starting server at %s:%d...";

    public static void main(String[] args) throws Exception {
        try {
            Preferences.fromArguments(args);
        } catch (Exception e) {
            System.out.println("Usage: -r: root directory, -c: number of cores.");
        }

        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);

        System.out.printf(STARTING_SERVER_MESSAGE, Preferences.getHost(), Preferences.getPort());

        HttpServer server = new HttpServer(
                Preferences.getHost(),
                Preferences.getPort(),
                Preferences.getDirectory(),
                Preferences.getCpuNumber()
        );
        server.run();
    }
}