package cn.qingyuyu.linuxbindown.presenter;


import cn.qingyuyu.linuxbindown.view.WebViewControl;




@SuppressWarnings("NonAsciiCharacters")
public class MainPresenter {
    private WebViewControl mi;

    private int 启动进度 = 1;


    private boolean running = false;

    public MainPresenter(WebViewControl mi) {
        this.mi = mi;
    }


    public void start() {
        if (!running) {
            running = true;
            mi.setRunLog("正在启动");
            new Thread(() -> {

                running=false;
            }).start();

        }

    }

    public void stop() {
        running = false;
    }


    public void close() {
        System.out.println("mp close");
        stop();
        System.exit(0);
    }
}
