package com.technicalaudition.loadbalancer.handler;

import com.technicalaudition.loadbalancer.servers.Servers;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSocketHandler implements Runnable {

    private Socket clientSocket;

    public ClientSocketHandler(final Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {

        try {
            //retrieves data from client
            InputStream clientToLoadBalancerInputStream = clientSocket.getInputStream();
            //sends data to client
            OutputStream loadBalancerToClientOutputStream = clientSocket.getOutputStream();

            String serverIps = Servers.getServerIps();
            System.out.println("Host selected to handle request: " + serverIps);

            //create TCP connection with backend servers
            Socket backendSocket = new Socket(serverIps, 8080);

            //retrieves data from client
            InputStream backendServerToLoadBalancerInputStream = backendSocket.getInputStream();
            //sends data to client
            OutputStream loadBalancerToBackendServerOutputStream = backendSocket.getOutputStream();

            Thread clientDataHandler = new Thread() {
                public void run() {
                    try {
                        int data;
                        while ((data = clientToLoadBalancerInputStream.read()) != -1) {
                            loadBalancerToBackendServerOutputStream.write(data);
                        }

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            };
            clientDataHandler.start();

            Thread backendDataHandler = new Thread() {
                public void run() {
                    try {
                        int data;
                        while ((data = backendServerToLoadBalancerInputStream.read()) != -1) {
                            loadBalancerToClientOutputStream.write(data);
                        }

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            };
            backendDataHandler.start();

        } catch(
            IOException ioException)
        {
        throw new RuntimeException(ioException);
        }

}
}
