package org.example.client;

import java.io.*;
import java.net.Socket;

public class PeerClient {
    private final Socket socket;
//    private BufferedReader in;
    private PrintWriter out;
    private static BufferedReader stdIn;

    public PeerClient(String host, int port) throws IOException {
        socket = new Socket(host,port);
        startClient();
    }



    public PeerClient(String host, int port, BufferedReader stdIn) throws IOException {
        socket = new Socket(host, port);
//        out = new PrintWriter(socket.getOutputStream(), true);
        if(stdIn == null) {
            this.stdIn = new BufferedReader(new InputStreamReader(System.in));
        } else {
            this.stdIn = stdIn;
        }
    }

    public void sendMessage(String message) {
        try {
            // 获取 socket 的输出流
            OutputStream outputStream = socket.getOutputStream();
            // 创建 PrintWriter 对象，自动刷新输出
            PrintWriter out = new PrintWriter(outputStream, true);
            // 发送消息
            out.println(message);
        } catch (IOException e) {
            /**
             *  throw new RuntimeException(e); 主要用于将一个检查型异常转换为运行时异常，并保持异常的堆栈信息以便于调试和追踪。
             *  e.printStackTrace(); 主要用于在控制台上打印异常信息，通常用于调试或简单的错误记录。
             */
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

//    public void receiveMessage() {
//        try {
//            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            String receivedMessage;
//            while ((receivedMessage = in.readLine()) != null) {
//                System.out.println("received: " + receivedMessage);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    public void receiveMessage() throws IOException {
//        return stdIn.readLine();
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 6789;
        PeerClient client = null;
        try {
            client = new PeerClient(host,port);
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            client.startClient();
//            System.out.println("Connect to server. Typed 'exit' to quit.");
//            String userInput;
//            while (!(userInput = stdIn.readLine()).equals("exit")){
//                client.sendMessage(userInput);
////                System.out.println("Server responded: " + client.receiveMessage());
//                client.receiveMessage();
//            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (client != null) {
                    client.close();
//                    client.socket.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        client.sendMessage("Hello from client");
//        client.receiveMessage();

    }

    public void close() throws IOException {
            if (stdIn != null) {
                stdIn.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
    }

    public void startClient() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Start a separate thread to handle receiving messages
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        String response = in.readLine();
                        if (response == null) break; // Server closed the connection
                        System.out.println("Server responded: " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            // Main loop to handle user input
            System.out.println("Connected to server. Type 'exit' to quit.");
            String userInput;
//            while (!(userInput = stdIn.readLine()).equals("exit")) {
//                sendMessage(userInput);
//            }

            // Wait for the receive thread to finish
            receiveThread.interrupt();
            receiveThread.join();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}