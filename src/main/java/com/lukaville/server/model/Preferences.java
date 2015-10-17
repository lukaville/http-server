package com.lukaville.server.model;

import org.apache.commons.cli.*;

/**
 * Created by nickolay on 17.10.15.
 */
public class Preferences {
    public static final String ARG_ROOT_DIRECTORY = "r";
    public static final String ARG_CPU_NUMBER = "c";

    private static String host = "0.0.0.0";
    private static int port = 5555;
    private static String directory;
    private static int cpuNumber;

    private static Options options = new Options();
    static {
        options.addOption(new Option(ARG_ROOT_DIRECTORY, true, "Root directory"));
        options.addOption(new Option(ARG_CPU_NUMBER, true, "CPU number"));
    }

    public static void fromArguments(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();

        CommandLine commandLine = parser.parse(options, args);
        directory = commandLine.getOptionValue(ARG_ROOT_DIRECTORY);
        cpuNumber = Integer.parseInt(commandLine.getOptionValue(ARG_CPU_NUMBER));
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
