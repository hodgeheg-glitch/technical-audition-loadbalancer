package com.technicalaudition.loadbalancer;

import ch.qos.logback.core.net.server.Client;
import com.technicalaudition.loadbalancer.handler.ClientSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
@SpringBootApplication
public class LoadbalancerApplication {

	//TODO: replace sout lines with logger messages

	private static final int SERVER_PORT = 8090;

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
		System.out.println("Load balancer running on port: " + SERVER_PORT);

		while(true) {
			//waits until a client makes a request to the load balancer. 3-way handshake occurs and connection is established.
			Socket socket = serverSocket.accept();
			System.out.println(("TCP connection established with client: " + socket.toString()));
			handleSocket(socket);

		}
	}

	private static void handleSocket(Socket socket) {
		ClientSocketHandler clientSocketHandler = new ClientSocketHandler(socket);
		Thread clientSocketHandlerThread = new Thread(clientSocketHandler);
		clientSocketHandlerThread.start();
	}

}
