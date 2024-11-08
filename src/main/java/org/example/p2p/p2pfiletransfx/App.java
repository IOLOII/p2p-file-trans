package org.example.p2p.p2pfiletransfx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.p2p.p2pfiletransfx.Interfaces.Define;
import org.example.p2p.p2pfiletransfx.client.PeerClient;
import org.example.p2p.p2pfiletransfx.controller.AppController;
import org.example.p2p.p2pfiletransfx.server.PeerServer;
import org.example.p2p.p2pfiletransfx.utils.IPUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    private final AppController controller;
    private final Label welcomeText;
    private final TextField currentipText;
    private final Stage stage;
    private PeerServer p2pServer;
    private final String serverhost = "1234";
    private final int serverport = 1234;
    private PeerClient p2pClient;


    public App(AppController controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;
        this.welcomeText = controller.getWelcomeText();
        this.currentipText = controller.getCurrenTipText();
    }

    private void changeAppFlag(String flag) {
//        appFlag = flag;
        switch (flag) {
            case "client":
//                openServerbtn.setVisible(false);
//                closeServerBtn.setVisible(false);
//                connectServerBtn.setVisible(true);
//                disconnectServerBtn.setVisible(true);
//                sendToServerBtn.setVisible(true);
//                sendToClientBtn.setVisible(false);
                break;
            case "server":
//                openServerbtn.setVisible(true);
//                closeServerBtn.setVisible(true);
//                connectServerBtn.setVisible(false);
//                disconnectServerBtn.setVisible(false);
//                sendToServerBtn.setVisible(false);
//                sendToClientBtn.setVisible(true);
                break;
            default:
//                openServerbtn.setVisible(true);
//                closeServerBtn.setVisible(true);
//                connectServerBtn.setVisible(true);
//                disconnectServerBtn.setVisible(true);
//                sendToServerBtn.setVisible(true);
//                sendToClientBtn.setVisible(true);
                break;

        }
    }

    /**
     * 开启服务
     *
     * @param serverport
     * @throws IOException
     */
    public void createServer(int serverport) throws IOException {
        p2pServer = new PeerServer(serverport);
        p2pServer.listen();
        welcomeText.setText("当前为服务端" + serverhost);
        //        p2pServer.
        System.out.println("create server");
    }

    /**
     * 关闭服务
     *
     * @param status
     * @throws IOException
     */
    public void createServer(String status) throws IOException {
        if (Objects.equals(status, "close") && !Objects.equals(p2pServer, null)) {
            p2pServer.close();
            System.out.println("closed server");
        } else {
            System.out.println("当前服务未启动");
        }
    }

    public void connectServer(String status) throws IOException {
        if (Objects.equals(status, "open")) {
            p2pClient = new PeerClient(serverhost, serverport);
            welcomeText.setText("当前为客户端");
            System.out.println("connect to server");

        } else {
            if (Objects.equals(p2pClient, null)) {
                System.out.println("当前未连接任何服务端");
                return;
            }
            p2pClient.close();
            System.out.println("closed connect");
        }
    }

    public void connectServer(String status, String serverhost, int serverport) throws IOException {
        if (Objects.equals(status, "open")) {
            p2pClient = new PeerClient(serverhost, serverport);

            Define.Promise<Socket> promise = new Define.Promise<Socket>(){

                @Override
                public void onSuccess(Socket s) {
                    System.out.println("1");
                }

                @Override
                public void onFailure(Exception e) {
                    System.out.println("123");
                }
            };
            p2pClient.connect(promise);

//            callback.onSuccess(new Socket());
//            callback.onSuccess(() -> {
//                System.out.println("Connected to server");
//                // 进一步处理连接成功的逻辑
//            });

//            if ("open".equals(status)) {
//                p2pClient = new PeerClient(serverhost, serverport);
//                Define.Promise<Socket> promise = p2pClient.connect2();
//
//                promise.then(socket -> {
//                    System.out.println("Connected to server");
//                    // 进一步处理连接成功的逻辑
//                }).catchError(exception -> {
//                    System.out.println("Failed to connect to server");
////                    exception.printStackTrace();
//                    // 处理连接失败的逻辑
//                });
//            } else {
//                if (p2pClient == null) {
//                    System.out.println("当前未连接任何服务端");
//                    return;
//                }
//
//                p2pClient.close();
//                System.out.println("Closed connection");
//            }

//            callback.onFailure((Exception exception) -> {
//                System.out.println("Connection failed");
//                exception.printStackTrace();
//                // 处理连接失败的逻辑
//            });

//            Define.Promise<Socket> callback = new Define.Promise<Socket>(){
//
//                @Override
//                public void onSuccess(Socket s) {
//
//                    welcomeText.setText("当前为客户端");
//                    System.out.println("connect to server");
//                }
//
//                @Override
//                public void onFailure(Exception e) {
//                    welcomeText.setText("");
//                    System.out.println("connect to server : fail");
//                }
//            };
//
//            ExecutorService executor = Executors.newSingleThreadExecutor();
//            executor.submit(()->{

//            });
        } else {
            if (Objects.equals(p2pClient, null)) {
                System.out.println("当前未连接任何服务端");
                return;
            }
            p2pClient.close();
            System.out.println("closed connect");
        }
    }

    private void validateIPv6Address() {
        String ipv6Address = currentipText.getText();
        boolean isValid = IPUtil.isIPv6(ipv6Address);
//        resultLabel.setText(isValid ? "Valid IPv6 Address" : "Invalid IPv6 Address");
    }

    public void stopProgress(ActionEvent actionEvent) {
//        timeline.stop();
//        selectedFiles.clear();
//        fileDetails.clear();
//        progress.set(0);
        changeBtns(true);
    }


    /**
     * @param bf true 开启状态，默认状态｜ false 操作上传的中间状态
     */
    public void changeBtns(Boolean bf) {
        if (bf) {
//            uploadButton.setDisable(false);
//            stopButton.setVisible(false);
//            startButton.setVisible(true);
//            stopButton.setManaged(false);
//            startButton.setManaged(true);
        } else {
//            uploadButton.setDisable(true);
//            startButton.setVisible(false);
//            stopButton.setVisible(true);
//            stopButton.setManaged(true);
//            startButton.setManaged(false);
        }
    }

    public void startProgress(ActionEvent event) {
        changeBtns(false);
        // 重置进度
//        progress.set(0);
//
//        // 创建时间线动画
//        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
//            double currentValue = progress.get();
//            if (currentValue < 1) {
//                progress.set(currentValue + 0.1);
//            } else {
//                timeline.stop();
//                System.out.println("完成");
//                changeBtns(true);
//            }
//        }));
//        timeline.setCycleCount(Timeline.INDEFINITE);
//
//        timeline.play();
    }

    public boolean sendMessage(String message, String appFlag) throws Exception {
        if (appFlag.equals("client")) {
            p2pClient.send(message);
            return true;
        } else if (appFlag.equals("server")) {
            p2pServer.send(message);
            return true;
        } else {
            throw new Error("当前会话未建立");
        }
    }
}
