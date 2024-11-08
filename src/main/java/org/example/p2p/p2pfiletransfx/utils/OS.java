package org.example.p2p.p2pfiletransfx.utils;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OS {
    public static List<File> choseFiles(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("所有文件","*.*"),
                new FileChooser.ExtensionFilter("文本文件", "*.txt"),
                new FileChooser.ExtensionFilter("图片文件", "*.png", "*.jpg", "*.gif")
        );
        // 打开文件选择对话框
        return fileChooser.showOpenMultipleDialog(primaryStage);
    }
}
