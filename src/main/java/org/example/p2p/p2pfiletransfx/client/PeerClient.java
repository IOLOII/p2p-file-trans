package org.example.p2p.p2pfiletransfx.client;

import org.example.p2p.p2pfiletransfx.Interfaces.Define;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PeerClient {
    private final String host;
    private final int port;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Thread receiveThread;
    private volatile boolean running = true; // 添加volatile关键字保证可见性

    public PeerClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect(Define.Promise prommise) {
//        try {
        int timeout = 5000; // 5 seconds


//            // 创建一个新的线程来处理连接
//            Thread connectionThread = new Thread(() -> {
//                try (Socket socket = new Socket()) {
//                    socket.connect(new InetSocketAddress(host, port), timeout);
//                    System.out.println("Connected to " + host + ":" + port);
//                    // 进一步处理连接
//                    out = new PrintWriter(socket.getOutputStream(), true);
//                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                } catch (IOException e) {
//                    System.err.println("Failed to connect to " + host + ":" + port);
//                    e.printStackTrace();
//                }
//            });
//            // 启动连接线程
//            connectionThread.start();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Define.Promise<Socket> callback = new Define.Promise<Socket>() {
            @Override
            public void onSuccess(Socket socket) {
                System.out.println("Connected to " + host + ":" + port);
                // 进一步处理连接
//                try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    prommise.onSuccess(socket);
                } catch (IOException e) {
                    e.printStackTrace();
//                } finally {
                }
            }

            @Override
            public void onFailure(Exception e) {
                System.err.println("Failed to connect to " + host + ":" + port);
                e.printStackTrace();
                prommise.onFailure(e);
            }
        };

        executor.submit(() -> {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(host, port), timeout);
                callback.onSuccess(socket);
            } catch (IOException e) {
                callback.onFailure(e);
            }
        });
//        return callback;


//        } catch (IOException e) {
////            throw new RuntimeException(e);
//            System.out.println("当前无启动服务");
//            throw e;
//        }
    }

    public void send(String message) {
        if (out != null) {
            System.out.println("客户端发送内容；" + message);
            out.println(message);
        } else {
            System.out.println("Not connected to server, cannot send message.");
        }
    }

    public void startReceiving(Runnable onMessageReceived) {
        receiveThread = new Thread(() -> {
            try {
                while (running) { // 修改循环条件
                    String message = in.readLine();
                    if (message == null || !running) {
                        break;
                    }
                    onMessageReceived.run();
                }
            } catch (IOException e) {
                // 处理IOException，可能是由于流被关闭引起的
                System.err.println("Error receiving message: " + e.getMessage());
            } finally {
                stopReceiving(); // 在finally块中停止接收线程
            }
        });
        receiveThread.start();
    }

    public synchronized void stopReceiving() {
        if (!running) return;
        running = false;
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
            if (receiveThread != null) {
                receiveThread.interrupt(); // 尝试中断接收线程
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        stopReceiving(); // 调用stopReceiving方法来关闭资源
    }

    public String receive() throws IOException {
        if (in != null) {
            return in.readLine();
        } else {
            System.out.println("Not connected to server, cannot receive message.");
            return null;
        }
    }

//    public static void main(String[] args) {
//        try {
//            PeerClient client = new PeerClient("localhost", 1234);
//            client.startReceiving(() -> {
//                try {
//                    String message = client.receive();
//                    System.out.println("Received from server: " + message);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//
//            // Example of sending a message
//            client.send("Hello, Server!");
//
//            // Close the client after some time or based on some condition
//            Thread.sleep(5000);
//            client.close();
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
