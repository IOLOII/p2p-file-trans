package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

public class AppApplet extends Application {
    private SimpleDoubleProperty progress = new SimpleDoubleProperty(0);
    private Timeline timeline;
    private Button startButton;
    private Button uploadButton;
    private Button stopButton;
    private List<File> selectedFiles;

    @Override
    public void start(Stage primaryStage) throws Exception {
        int a = 3;
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

            // 创建一个按钮
            uploadButton = new Button("选择文件");
            TextArea fileDetails = new TextArea();
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
                selectedFiles = fileChooser.showOpenMultipleDialog(primaryStage);
                if (selectedFiles != null && !selectedFiles.isEmpty()) {
                    fileDetails.setText("");
                    for (File file : selectedFiles) {
                        fileDetails.appendText("文件名: " + file.getName() + "\n");
                        fileDetails.appendText("大小: " + file.length()/1024/1024 + " MB \n");
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
            startButton.setOnAction(event->{
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
            vbox.getChildren().addAll(uploadButton, fileDetails, statusLabel,progressBar, startButton,stopButton);

            // 设置场景
            Scene scene = new Scene(vbox, 400, 300);
            primaryStage.setTitle("JavaFX 文件上传示例");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    private void stopProgress(ActionEvent actionEvent) {
        timeline.stop();
        selectedFiles.clear();
//        progress.set(0);
        changeBtns(true);
    }

    /**
     *
     * @param bf true 开启状态，默认状态｜ false 操作上传的中间状态
     */
    public void changeBtns(Boolean bf) {
        if (bf){
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
        launch(args);
    }
}

