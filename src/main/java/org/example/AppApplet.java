package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.client.PeerClient;
import org.example.server.PeerServer;
import org.example.utils.IPUtil;
import org.example.utils.Time;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AppApplet extends Application {
    private SimpleDoubleProperty progress = new SimpleDoubleProperty(0);
    private Timeline timeline;
    private Button startButton;
    private Button uploadButton;
    private Button stopButton;
    private List<File> selectedFiles;
    private TextField ipv6Field;
    private Label resultLabel;
    private Button validateButton;
    private TextField portField;
    private TextArea fileDetails;
    private PeerServer p2pServer;
    private static int serverport;
    private PeerClient p2pConnect;
    private static String serverhost;
    private TextField messageFiled;
    private Label flagLabel;
    private TextArea chatArea;
    private String appFlag; // server client
    private Button connectServerBtn;
    private Button disconnectServerBtn;
    private Button openServerbtn;
    private Button closeServerBtn;
    private Button sendToServerBtn;
    private Button sendToClientBtn;

    @Override
    public void start(Stage primaryStage) throws Exception {
        int a = 4;
        if (a == 1) {

            Button btn = new Button("Click");
            btn.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Notice");
                alert.setHeaderText(null);
                alert.setContentText("Button Clicked!");
                alert.showAndWait();
            });

//        Cell<Object> cell = new Cell<>();

            CheckBox checkbox = new CheckBox("123");

            StackPane root = new StackPane();
            root.getChildren().add(btn);
//        root.getChildren().add(cell);
            root.getChildren().add(checkbox);
            Scene scene = new Scene(root, 255, 255);

            StackPane root2 = new StackPane();
            root2.getChildren().add(checkbox);
            Scene scene2 = new Scene(root2, 255, 255);

            primaryStage.setTitle("Application");
            primaryStage.setScene(scene);
            primaryStage.setScene(scene2);

            primaryStage.show();

        } else if (a == 2) {


            // 创建GridPane布局
            GridPane grid = new GridPane();
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.setVgap(8);
            grid.setHgap(10);

            // 创建并添加Label和TextField
            Label nameLabel = new Label("姓名:");
            GridPane.setConstraints(nameLabel, 0, 0); // 第一列，第一行
            TextField nameField = new TextField();
            GridPane.setConstraints(nameField, 1, 0); // 第二列，第一行

            // 创建并添加Label和ChoiceBox
            Label countryLabel = new Label("国家:");
            GridPane.setConstraints(countryLabel, 0, 1); // 第一列，第二行
            ChoiceBox<String> countryBox = new ChoiceBox<>();
            countryBox.getItems().addAll(
                    "中国", "美国", "英国", "法国", "德国"
            );
            countryBox.setValue("中国");
            GridPane.setConstraints(countryBox, 1, 1); // 第二列，第二行

            // 创建并添加Button
            Button submitButton = new Button("提交");
            GridPane.setConstraints(submitButton, 1, 2); // 第二列，第三行
            GridPane.setHalignment(submitButton, HPos.RIGHT);

            // 将所有控件添加到GridPane中
            grid.getChildren().addAll(nameLabel, nameField, countryLabel, countryBox, submitButton);

            // 设置场景
            Scene scene = new Scene(grid, 300, 200);
            primaryStage.setTitle("JavaFX 表单示例");
            primaryStage.setScene(scene);
            primaryStage.show();

            // 按钮点击事件处理
            submitButton.setOnAction(e -> {
                String name = nameField.getText();
                String country = countryBox.getValue();
                System.out.println("姓名: " + name + ", 国家: " + country);
            });
        } else if (a == 3) {
            // 创建VBox布局
            VBox vbox = new VBox(10); // 间距为10
            vbox.setPadding(new Insets(10, 10, 10, 10));

            // 获取本地 IPv6 地址
            String localIPv6Address = IPUtil.getLocalIPv6Address();
            // 创建 Label 显示 IPv6 地址
            Label ipAddressLabel = new Label(localIPv6Address == null ? "No IPv6 address found" : localIPv6Address);
            // 创建按钮
            Button copyButton = new Button("Copy");
            copyButton.setOnAction(e -> {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(ipAddressLabel.getText());
                clipboard.setContent(content);
            });

            // 创建 HBox 容器
            HBox hBox1 = new HBox(10, ipAddressLabel, copyButton);
            hBox1.setAlignment(Pos.CENTER);


            ipv6Field = new TextField();
            ipv6Field.setPrefWidth(200); // 设置输入框的首选宽度
            portField = new TextField();
            portField.setPrefWidth(100); // 设置输入框的首选宽度
            HBox hBox2 = new HBox(10, portField, ipv6Field);
            hBox2.setPrefWidth(320); // 设置 HBox 的首选宽度


            validateButton = new Button("Validate IPv6 Address");
            resultLabel = new Label();
            validateButton.setOnAction(event -> validateIPv6Address());

            VBox vBox = new VBox(10);
            vBox.setPadding(new Insets(10));
            vBox.getChildren().addAll(ipv6Field, validateButton, resultLabel);


            // 创建一个按钮
            uploadButton = new Button("选择文件");
            fileDetails = new TextArea();
            fileDetails.setEditable(false); // 不允许编辑
            fileDetails.setWrapText(true);
            fileDetails.setPrefWidth(100);
            fileDetails.setPrefRowCount(10); // 设置行数

            // 添加按钮点击事件
            uploadButton.setOnAction(event -> {
                // 创建文件选择器
                FileChooser fileChooser = new FileChooser();
                // 设置初始目录
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                // 设置文件过滤器
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("所有文件", "*.*"),
                        new FileChooser.ExtensionFilter("文本文件", "*.txt"),
                        new FileChooser.ExtensionFilter("图片文件", "*.png", "*.jpg", "*.gif")
                );

                // 打开文件选择对话框
                List<File> _selectedFiles = fileChooser.showOpenMultipleDialog(primaryStage);
                if (_selectedFiles == null || _selectedFiles.isEmpty()) return;
                // Create a new ArrayList from the selected files
                selectedFiles = new ArrayList<>(_selectedFiles);


                if (selectedFiles != null && !selectedFiles.isEmpty()) {
                    fileDetails.setText("");
                    for (File file : selectedFiles) {
                        fileDetails.appendText("文件名: " + file.getName() + "\n");
                        fileDetails.appendText("大小: " + file.length() / 1024 / 1024 + " MB \n");
                        fileDetails.appendText("路径: " + file.getAbsolutePath() + "\n\n");
                    }
                }
            });

            // 创建进度条
            ProgressBar progressBar = new ProgressBar();
            progressBar.setMinWidth(150);
            progressBar.progressProperty().bind(progress);

            // 创建按钮
            startButton = new Button("开始");
            startButton.setOnAction(event -> {
                if (selectedFiles != null && !selectedFiles.isEmpty()) {
                    startProgress(event);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("请先选择待文件！");
                    alert.setTitle(null);
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            });

            stopButton = new Button("停止");
            stopButton.setVisible(false);
            stopButton.setManaged(false);

            stopButton.setOnAction(this::stopProgress);

            // 创建状态标签
            Label statusLabel = new Label("未开始");


            // 将控件添加到VBox中
            vbox.getChildren().addAll(hBox1, hBox2, vBox, uploadButton, fileDetails, statusLabel, progressBar, startButton, stopButton);

            // 设置场景
            Scene scene = new Scene(vbox, 400, 500);
            primaryStage.setTitle("JavaFX 文件上传示例");
            primaryStage.setScene(scene);
            primaryStage.show();
        } else if (a == 4) {
            // 创建VBox布局
            VBox vbox = new VBox(10); // 间距为10
            vbox.setPadding(new Insets(10, 10, 10, 10));

            connectServerBtn = new Button("connect");
            connectServerBtn.setOnAction(actionEvent -> {
                try {
                    connectServer("open");
                    changeAppFlag("client");
                } catch (IOException e) {
                    e.printStackTrace();
//                    throw new RuntimeException(e);
                }
            });
            disconnectServerBtn = new Button("disConnect");
            disconnectServerBtn.setOnAction(actionEvent -> {
                try {
                    connectServer("close");
                    changeAppFlag("default");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            openServerbtn = new Button("server");
            openServerbtn.setOnAction(actionEvent -> {
                try {
                    createServer(serverport);
                    changeAppFlag("server");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            closeServerBtn = new Button("close Server");
            closeServerBtn.setOnAction(actionEvent -> {
                try {
                    createServer("close");
                    changeAppFlag("default");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });


            // 创建聊天区域
            chatArea = new TextArea();
            chatArea.setEditable(false);
            chatArea.setWrapText(true);


            flagLabel = new Label("标识");

            messageFiled = new TextField("发送文字");

            sendToClientBtn = new Button("发送给客户端");
            sendToClientBtn.setOnAction(actionEvent -> {
                String message = messageFiled.getText();
                p2pServer.send(message);
                chatArea.appendText(appFlag + ": " + Time.getFormattedTimestamp() + "\n");
                chatArea.appendText(appFlag + ": " + message + "\n");
                messageFiled.clear();
            });
            sendToServerBtn = new Button("发送给服务端");
            sendToServerBtn.setOnAction(actionEvent -> {
                String message = messageFiled.getText();
                p2pConnect.send(message);
                chatArea.appendText(appFlag + ": " + Time.getFormattedTimestamp() + "\n");
                chatArea.appendText(appFlag + ": " + message + "\n");
                messageFiled.clear();
            });

            Button clearChatBtn = new Button("清除聊天内容");
            clearChatBtn.setOnAction(actionEvent -> {
                messageFiled.clear();
            });

            // 将控件添加到VBox中
            vbox.getChildren().addAll(connectServerBtn, disconnectServerBtn, openServerbtn, closeServerBtn, chatArea, messageFiled, sendToClientBtn, sendToServerBtn, flagLabel);

            // 设置场景
            Scene scene = new Scene(vbox, 400, 500);
            primaryStage.setTitle("JavaFX 文件上传示例");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    private void changeAppFlag(String flag) {
        appFlag = flag;
        switch (flag) {
            case "client":
                openServerbtn.setVisible(false);
                closeServerBtn.setVisible(false);
                connectServerBtn.setVisible(true);
                disconnectServerBtn.setVisible(true);
                sendToServerBtn.setVisible(true);
                sendToClientBtn.setVisible(false);
                break;
            case "server":
                openServerbtn.setVisible(true);
                closeServerBtn.setVisible(true);
                connectServerBtn.setVisible(false);
                disconnectServerBtn.setVisible(false);
                sendToServerBtn.setVisible(false);
                sendToClientBtn.setVisible(true);
                break;
            default:
                openServerbtn.setVisible(true);
                closeServerBtn.setVisible(true);
                connectServerBtn.setVisible(true);
                disconnectServerBtn.setVisible(true);
                sendToServerBtn.setVisible(true);
                sendToClientBtn.setVisible(true);
                break;

        }
    }

    /**
     * 开启服务
     *
     * @param serverport
     * @throws IOException
     */
    private void createServer(int serverport) throws IOException {
        p2pServer = new PeerServer(serverport);
        p2pServer.listen();
        flagLabel.setText("当前为服务端" + serverhost);
//        p2pServer.
        System.out.println("create server");
    }

    /**
     * 关闭服务
     *
     * @param status
     * @throws IOException
     */
    private void createServer(String status) throws IOException {
        if (Objects.equals(status, "close") && !Objects.equals(p2pServer, null)) {
            p2pServer.close();
            System.out.println("closed server");
        } else {
            System.out.println("当前服务未启动");
        }
    }

    private void connectServer(String status) throws IOException {
        if (Objects.equals(status, "open")) {
            p2pConnect = new PeerClient(serverhost, serverport);
            flagLabel.setText("当前为客户端");
            System.out.println("connect to server");

        } else {
            if (Objects.equals(p2pConnect, null)) {
                System.out.println("当前未连接任何服务端");
                return;
            }
            p2pConnect.close();
            System.out.println("closed connect");
        }
    }

    private void validateIPv6Address() {
        String ipv6Address = ipv6Field.getText();
        boolean isValid = IPUtil.isIPv6(ipv6Address);
        resultLabel.setText(isValid ? "Valid IPv6 Address" : "Invalid IPv6 Address");
    }

    private void stopProgress(ActionEvent actionEvent) {
        timeline.stop();
        selectedFiles.clear();
        fileDetails.clear();
        progress.set(0);
        changeBtns(true);
    }

    /**
     * @param bf true 开启状态，默认状态｜ false 操作上传的中间状态
     */
    public void changeBtns(Boolean bf) {
        if (bf) {
            uploadButton.setDisable(false);
            stopButton.setVisible(false);
            startButton.setVisible(true);
            stopButton.setManaged(false);
            startButton.setManaged(true);
        } else {
            uploadButton.setDisable(true);
            startButton.setVisible(false);
            stopButton.setVisible(true);
            stopButton.setManaged(true);
            startButton.setManaged(false);
        }
    }

    private void startProgress(ActionEvent event) {
        changeBtns(false);
        // 重置进度
        progress.set(0);

        // 创建时间线动画
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            double currentValue = progress.get();
            if (currentValue < 1) {
                progress.set(currentValue + 0.1);
            } else {
                timeline.stop();
                System.out.println("完成");
                changeBtns(true);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.play();
    }


    public static void main(String[] args) {
        serverport = 12334;

        serverhost = IPUtil.getLocalIPv4Address();
        System.out.println("本机IPv6: " + serverhost);
        launch(args);
    }
}

