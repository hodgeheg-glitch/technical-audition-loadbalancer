package com.technicalaudition.loadbalancer.balancer;

import com.technicalaudition.loadbalancer.handler.ClientSocketHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class LoadBalancer {

//    public static void establishConnection(int serverPort) throws IOException {
//
//        ServerSocket serverSocket = new ServerSocket(serverPort);
//        System.out.println("Load balancer running on port: " + serverPort);
//
//        while (true) {
//            //waits until a client makes a request to the load balancer. 3-way handshake occurs and connection is established.
//            Socket client = serverSocket.accept();
//            System.out.println(("TCP connection established with client: " + client.toString()));
//            handleSocket(client);
//        }
//    }
//    private static void handleSocket(Socket socket) {
//        ClientSocketHandler clientSocketHandler = new ClientSocketHandler(socket);
//        Thread clientSocketHandlerThread = new Thread(clientSocketHandler);
//        clientSocketHandlerThread.start();
//    }
}
