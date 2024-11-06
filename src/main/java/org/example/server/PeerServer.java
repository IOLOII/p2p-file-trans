package org.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PeerServer {

    private final ServerSocket server;
    public Socket socket;

    public PeerServer(int port) throws IOException{
        server = new ServerSocket(port);
        listen();
    }
    public void listen() {
        try {
            System.out.println("Server started on port " + server.getLocalPort());
            System.out.println("Waiting for connection...");

            // 阻塞等待客户端连接
            socket = server.accept();
            System.out.println("Client connected: " + socket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Thread listenThread = new Thread(() -> {
                try {
                    String receivedMessage;
                    while ((receivedMessage = in.readLine()) != null) {
                        System.out.println("Received: " + receivedMessage);
                        out.println("服务器收到");
                        send(receivedMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            listenThread.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String message) {
//        try {
//            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//            out.println(message);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }

    public static void main(String[] args) {
        int port = 6789;
        PeerServer server = null;
        try {
            server = new PeerServer(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.listen();

    }


    public void close() throws IOException {
        socket.close();
    }
}
