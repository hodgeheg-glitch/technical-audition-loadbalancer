package com.technicalaudition.loadbalancer.handler;

import com.technicalaudition.loadbalancer.servers.Server;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Component
public class ClientSocketHandler implements Runnable {

    private static final int END_OF_STREAM = -1;
    private static final int BACKEND_PORT = 8080;
    private Socket clientSocket;

    public ClientSocketHandler(final Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {

        try {
            InputStream clientToLoadBalancerInputStream = clientSocket.getInputStream();
            OutputStream loadBalancerToClientOutputStream = clientSocket.getOutputStream();

            String backendHost = Server.getHost();
            
            //TODO: replace with logger messages
            System.out.println("Host selected to handle request: " + backendHost);

            //create TCP connection with backend servers
            Socket backendSocket = new Socket(backendHost, BACKEND_PORT);

            //although not necessary, instantiation of objects here rather than inside method call increases clarity
            InputStream backendServerToLoadBalancerInputStream = backendSocket.getInputStream();
            OutputStream loadBalancerToBackendServerOutputStream = backendSocket.getOutputStream();

            clientToLoadBalancerInputStreamListenerThread(clientToLoadBalancerInputStream, loadBalancerToBackendServerOutputStream);

            backendToLoadBalancerListenerThread(backendServerToLoadBalancerInputStream, loadBalancerToClientOutputStream);

        } catch(IOException ioException) {
            ioException.printStackTrace();
            throw new RuntimeException(ioException);
        }
    }

    private void clientToLoadBalancerInputStreamListenerThread(InputStream clientToLoadBalancerInputStream,
                                                               OutputStream loadBalancerToBackendServerOutputStream) {
        Thread clientDataHandler = new Thread(() -> {
            try {
                int data;
                while ((data = clientToLoadBalancerInputStream.read()) != END_OF_STREAM) {
                    loadBalancerToBackendServerOutputStream.write(data);
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
            clientDataHandler.start();
    }


    private void backendToLoadBalancerListenerThread(InputStream backendServerToLoadBalancerInputStream,
                                             OutputStream loadBalancerToClientOutputStream) {
        Thread backendDataHandler = new Thread(() -> {
            try {
                int data;
                while ((data = backendServerToLoadBalancerInputStream.read()) != END_OF_STREAM) {
                    loadBalancerToClientOutputStream.write(data);
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        backendDataHandler.start();
    }

}
