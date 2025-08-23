package com.technicalaudition.loadbalancer.servers;

import com.technicalaudition.loadbalancer.handler.ClientSocketHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

@Component
public class Server {

    private static ArrayList<String> serverNames = new ArrayList<>();
    private static final String SERVER_1_NAME = "Server 1";
    private static final String SERVER_2_NAME = "Server 2";
    private static int numberOfServers = 0;

    static {
        serverNames.add(SERVER_1_NAME);
        serverNames.add(SERVER_2_NAME);
    }




    public static String getHost() {
        //round robin approach (first iteration)
        //TODO - this will not work for more than two servers.
        String ipAddress = serverNames.get(numberOfServers % serverNames.size());
        numberOfServers++;
        return ipAddress;
    }


}
