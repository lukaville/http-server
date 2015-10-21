package com.lukaville.server.preferences;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * Created by nickolay on 17.10.15.
 */
public class Preferences {
    private static String host = "0.0.0.0";
    private static int port = 5555;
    private static String directory;
    private static String indexFile = "index.html";
    private static int cpuNumber;

    private static OptionParser parser = new OptionParser("r:n:");

    public static void fromArguments(String[] args) {
        OptionSet options = parser.parse(args);
        directory = (String) options.valueOf("r");
        cpuNumber = Integer.parseInt((String) options.valueOf("n"));
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        Preferences.host = host;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        Preferences.port = port;
    }

    public static String getDirectory() {
        return directory;
    }

    public static void setDirectory(String directory) {
        Preferences.directory = directory;
    }

    public static int getCpuNumber() {
        return cpuNumber;
    }

    public static void setCpuNumber(int cpuNumber) {
        Preferences.cpuNumber = cpuNumber;
    }

    public static String getIndexFile() {
        return indexFile;
    }
}
