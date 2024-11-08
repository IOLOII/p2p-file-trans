package org.example.p2p.p2pfiletransfx.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class PeerServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Thread listenThread;
    private volatile boolean running = true;

    public PeerServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
    }

    public void listen() {
        listenThread = new Thread(() -> {
            try {
                clientSocket = serverSocket.accept();

                String clientIp = String.valueOf(clientSocket.getInetAddress());
                if (clientIp.startsWith("/")) {
                    clientIp = clientIp.substring(1);
                }
                System.out.println("Client connected: " + clientIp);

                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Start a loop to read messages from the client
                String message;
                while (running) {
                    message = in.readLine();
                    if (!Objects.equals(message, null)) {
                        System.out.println("Received from client: " + message);
                    }
                    if ("STOP".equals(message)) {
                        close();
                    }
                }
            } catch (IOException e) {
                System.out.println("服务端关闭");
//                e.printStackTrace();
//            } finally {
//                stopListening();
            }
        });
        listenThread.start();
    }

    public void send(String message) {
        if (out != null) {
            System.out.println("服务端发送内容；" + message);
            out.println(message);
        } else {
            System.out.println("No client connected, cannot send message.");
        }
    }

    public synchronized void stopListening() throws IOException {
        if (!running) return;
        running = false;
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
            if (serverSocket != null) {
                serverSocket.close();
            }
            if (listenThread != null) {
                listenThread.interrupt(); // 尝试中断监听线程
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void close() throws IOException {
        stopListening();
    }

    public static void main(String[] args) {
        try {
            PeerServer server = new PeerServer(1234);
            server.listen();

            // 保持主线程运行，以便服务器可以继续监听
            while (true) {
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
