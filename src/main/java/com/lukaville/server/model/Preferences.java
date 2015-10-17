package com.lukaville.server.model;

import org.apache.commons.cli.*;

/**
 * Created by nickolay on 17.10.15.
 */
public class Preferences {
    public static final String ARG_ROOT_DIRECTORY = "r";
    public static final String ARG_CPU_NUMBER = "c";
    public static final String ARG_HOST = "h";
    public static final String ARG_PORT = "p";

    private static String host;
    private static int port;
    private static String directory;
    private static int cpuNumber;

    private static Options options = new Options();

    static {
        options.addOption(new Option(ARG_ROOT_DIRECTORY, true, "Root directory"));
        options.addOption(new Option(ARG_CPU_NUMBER, true, "CPU number"));
        options.addOption(new Option(ARG_HOST, true, "Host"));
        options.addOption(new Option(ARG_PORT, true, "Port"));
    }

    public static void fromArguments(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();

        CommandLine commandLine = parser.parse(options, args);
        directory = commandLine.getOptionValue(ARG_ROOT_DIRECTORY, "www");
        cpuNumber = Integer.parseInt(commandLine.getOptionValue(ARG_CPU_NUMBER, "8"));
        host = commandLine.getOptionValue(ARG_HOST, "0.0.0.0");
        port = Integer.parseInt(commandLine.getOptionValue(ARG_PORT, "5555"));
    }

    public static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("httpd.jar", options);
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
}
