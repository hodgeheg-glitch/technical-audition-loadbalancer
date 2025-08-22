package com.technicalaudition.loadbalancer.servers;

import java.util.ArrayList;

public class Servers {

    private static ArrayList<String> serverIps = new ArrayList<>();

    private static int numberOfServerIps = 0;

    static {
        serverIps.add("Server 1");
        serverIps.add("Server 2");
    }

    public static String getServerIps() {
        //TODO - this will not work for more than two servers.
        String ipAddress = serverIps.get(numberOfServerIps%serverIps.size());
        numberOfServerIps++;
        return ipAddress;

    }

}
