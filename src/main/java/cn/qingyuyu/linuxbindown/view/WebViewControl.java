package cn.qingyuyu.linuxbindown.view;


import cn.qingyuyu.linuxbindown.Constant;
import cn.qingyuyu.linuxbindown.presenter.MainPresenter;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;

import javafx.scene.control.MenuItem;
import javafx.scene.web.WebView;

import netscape.javascript.JSObject;

import java.net.URL;

public class WebViewControl {
    private MainPresenter mp;

    private MyJsObject js = new MyJsObject();

    @FXML
    MenuItem aboutMenu;

    public void init() {
        mp = new MainPresenter(this);
        try {
            webView.getEngine().load(new URL(Constant.resourceFolder+"centerControl.html").toString());
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
            webView.getEngine().load(new URL(Constant.resourceFolder+"centerControl.html").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private WebView webView;

    public void close() {
        mp.close();
    }

    public void setRunLog(String text) {
        System.out.println(text);
        Platform.runLater(
                () -> webView.getEngine().executeScript("javascript:log('" + text + "')"));
    }

    public void setRunningState(String percent) {
        Platform.runLater(
                () -> webView.getEngine().executeScript("javascript:setRunState('" + percent + "')"));
    }

    // JavaScript 接口0.
    public class MyJsObject {

        @SuppressWarnings("unused")
        public void start() {

            mp.start();

        }

        @SuppressWarnings("unused")
        public void stop() {

            mp.stop();

        }

        public void close() {
            Platform.exit();
        }
    }
}
