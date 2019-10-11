package cn.qingyuyu.linuxbindown;

import cn.qingyuyu.linuxbindown.view.WebViewControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Scene root = null;
        WebViewControl wc = null;
        try {
            // 这里的root从FXML文件中加载进行初始化，这里FXMLLoader类用于加载FXML文件
            FXMLLoader fxmlLoader = new FXMLLoader();
//            String path=Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//            path=path.substring(0,path.lastIndexOf("/"))+"/resources/";
//           System.out.println(path);
           // Constant.resourceFolder=path;
            fxmlLoader.setLocation(new URL("file:"+Constant.resourceFolder+"web.fxml"));
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

            AnchorPane layout = fxmlLoader.load();

            root = new Scene(layout);
            wc = fxmlLoader.getController();
            wc.init();//初始化
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Linux裸机程序烧写器");
        primaryStage.setHeight(400);
        primaryStage.setWidth(600);
        primaryStage.setScene(root);
        if (wc != null) {
            WebViewControl finalWc = wc;
            primaryStage.setOnCloseRequest(event -> finalWc.close());
            primaryStage.getIcons().add(new Image(
                    "file:"+Constant.resourceFolder+"icon.png"));

        }
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}