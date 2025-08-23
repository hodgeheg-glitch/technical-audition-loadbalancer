package com.technicalaudition.loadbalancer;

import com.technicalaudition.loadbalancer.balancer.LoadBalancer;
import com.technicalaudition.loadbalancer.handler.ClientSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class LoadbalancerApplication {

	private static final int SERVER_PORT = 8081;

	public static void main(String[] args) throws IOException {
			establishConnection(SERVER_PORT);
	}

	public static void establishConnection(int serverPort) throws IOException {

		ServerSocket serverSocket = new ServerSocket(serverPort);
		System.out.println("Load balancer running on port: " + serverPort);

		while (true) {
			//waits until a client makes a request to the load balancer. 3-way handshake occurs and connection is established.
			Socket client = serverSocket.accept();
			System.out.println(("TCP connection established with client: " + client.toString()));
			handleSocket(client);
		}
	}
	private static void handleSocket(Socket socket) {
		ClientSocketHandler clientSocketHandler = new ClientSocketHandler(socket);
		Thread clientSocketHandlerThread = new Thread(clientSocketHandler);
		clientSocketHandlerThread.start();
	}
}
