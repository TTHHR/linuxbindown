package cn.qingyuyu.linuxbindown.view;


import cn.qingyuyu.linuxbindown.Constant;
import cn.qingyuyu.linuxbindown.presenter.MainPresenter;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;

import javafx.scene.control.MenuItem;
import javafx.scene.web.WebView;

import javafx.stage.FileChooser;
import netscape.javascript.JSObject;

import java.io.File;
import java.net.URL;

public class WebViewControl {
    private MainPresenter mp;

    private MyJsObject js = new MyJsObject();

    @FXML
    MenuItem aboutMenu;

    public void init() {
        mp = new MainPresenter(this);
        try {
            webView.getEngine().load(new URL("file:"+Constant.resourceFolder+"view.html").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // process page loading
        webView.getEngine().getLoadWorker().stateProperty().addListener(
                (ObservableValue<? extends Worker.State> ov, Worker.State oldState,
                 Worker.State newState) -> {
                    if (newState == Worker.State.SUCCEEDED) {
                        JSObject win
                                = (JSObject) webView.getEngine().executeScript("window");
                        win.setMember("app", js);
                    }
                });
    }

    @FXML

    private void reloadLocal() {
        try {
            webView.getEngine().load(new URL("file:"+Constant.resourceFolder+"view.html").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private WebView webView;

    public void close() {
        mp.close();
    }
    public void about() {
        Platform.runLater(
                () -> {
                    webView.getEngine().executeScript("javascript:dialog('仅支持正点原子Linux Alpha开发板，本程序开源 ','true')");
                });
    }
    public void loadGithub()
    {
        try {
            webView.getEngine().load(new URL(Constant.github).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRunLog(String text) {
        System.out.println(text);
        Platform.runLater(
                () -> webView.getEngine().executeScript("javascript:log('" + text + "')"));
    }

    // JavaScript 接口0.
    public class MyJsObject {

        @SuppressWarnings("unused")
        public void start(String sd,String pass) {

            mp.start(sd,pass);

        }
        public String getSDSize(String sd)
        {
           return mp.getSize(sd);
        }

        @SuppressWarnings("unused")
        public String selectFile(String filter) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择设备文件");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("File", filter)
            );
            File file =
                    fileChooser.showOpenDialog(null);
            if (file != null) {
                mp.filePath=file.getAbsolutePath();
                return file.getAbsolutePath();
            }

            return "";
        }
        public String[] getSDs()
        {
            return mp.getSDs();
        }

    }
}
