package org.example.p2p.p2pfiletransfx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import org.example.p2p.p2pfiletransfx.App;
import org.example.p2p.p2pfiletransfx.utils.IPUtil;
import org.example.p2p.p2pfiletransfx.utils.OS;
import org.example.p2p.p2pfiletransfx.utils.Time;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

public class AppController {
    @FXML
    private Label welcomeText;
    @FXML
    private Button copyBtn;
    @FXML
    private Button uploadFile;
    @FXML
    private TextField currentTipText;
    @FXML
    private ProgressBar fileProgress;
    private Stage stage;
    @FXML
    private TextArea fileList;

    @FXML
    private Button connect;
    @FXML
    private Button disconnect;
    @FXML
    private Button startserver;
    @FXML
    private Button closeserver;

    @FXML
    private TextArea messageText;
    @FXML
    private TextArea chatHistory;

    private App app;
    private String appFlag;

    public Label getWelcomeText() {
        return welcomeText;
    }

    public Button getcopyBtn() {
        return copyBtn;
    }

    public TextField getCurrenTipText() {
        return currentTipText;
    }

    public ProgressBar getFileProgress() {
        return fileProgress;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    protected void initialize(){
        System.out.println("执行初始化操作");

        String localIPv6Address = IPUtil.getLocalIPv4Address();
        currentTipText.setText(localIPv6Address);

        copyBtn.requestFocus();
    }
    @FXML
    protected void onCopyButtonClick(){
        copyBtn.setOnAction(actionEvent -> {
            System.out.println("click onCopyButtonClick");
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(currentTipText.getText());
            clipboard.setContent(content);
        });
    }

    @FXML
    protected void pickFiles(){
        List<File> _selectedFiles  = OS.choseFiles(this.stage);
        if (_selectedFiles == null || _selectedFiles.isEmpty()) return;
        // Create a new ArrayList from the selected files
        ArrayList<File> selectedFiles = new ArrayList<>(_selectedFiles);
        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            fileList.setText("");
            for (File file : selectedFiles) {
                fileList.appendText("文件名: " + file.getName() + "\n");
                fileList.appendText("大小: " + file.length() / 1024 / 1024 + " MB \n");
                fileList.appendText("路径: " + file.getAbsolutePath() + "\n\n");
            }
        }
    }

    @FXML
    protected void connectServer(){
        try {
            app.connectServer("open",IPUtil.getLocalIPv4Address(),1234);
            String message = "正在连接...";
            chatHistory.appendText( message + "\n");
        } catch (IOException e) {
            String message = "建立连接失败！";
            chatHistory.appendText( message + "\n");
        }
    }
    @FXML
    protected void disConnectServer(){
        try {
            app.connectServer("close");
            String message = "关闭连接";
            chatHistory.appendText( message + "\n");
        } catch (IOException e) {
            String message = "退出连接错误！";
            chatHistory.appendText( message + "\n");
        }
    }

    @FXML
    protected void createServer(){
        try {
            app.createServer(1234);
            String message = "服务启动成功";
            chatHistory.appendText( message + "\n");
        } catch (IOException e) {
            String message = "服务启动失败！";
            chatHistory.appendText( message + "\n");
        }
    }
    @FXML
    protected void closeServer(){
        try {
            app.createServer("close");
            String message = "服务关闭";
            chatHistory.appendText( message + "\n");
        } catch (IOException e) {
            String message = "服务关闭失败！";
            chatHistory.appendText( message + "\n");
        }
    }



    @FXML
    protected void sendMessage(){
        String message = messageText.getText();
        try {
            if(app.sendMessage(message,appFlag)){
                chatHistory.appendText(appFlag + ": " + Time.getFormattedTimestamp() + "\n");
                chatHistory.appendText(appFlag + ": " + message + "\n");
                messageText.clear();
            } else {
                System.out.println("发送失败："+message);
            }
        } catch (Exception e) {
            String _message = "讯息发送失败！或未建立、开启连接";
            chatHistory.appendText( _message + "\n");
            e.printStackTrace();
        }
    }

    public void setApp(App app) {
        this.app = app;
    }
}