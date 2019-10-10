package cn.qingyuyu.linuxbindown.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    static Log l = null;
    static BufferedWriter bw = null;
    private String fileName = "";

    private Log() {
        if (l != null) {
            l.closeFile();
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// set date
            // format
            File logFile = new File(df.format(new Date()) + ".log");
            if (!logFile.exists())
                logFile.createNewFile();

            fileName = logFile.getName();
            bw = new BufferedWriter(new FileWriter(df.format(new Date()) + ".log", true));
        } catch (Exception e) {
            System.out.println("Log File Error");
        }
    }

    private void closeFile() {
        if (bw != null) {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Log getInstance() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// set date
        if (l == null)
            l = new Log();
        else {
            if (!l.fileName.equals(df.format(new Date()) + ".log"))
                l = new Log();
        }

        return l;
    }

    public void d(String name, String describe) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String log = "#Debug#" + df.format(new Date()).toString() + "/ " + name + ":  " + describe + "\n";
        try {
            bw.write(log);
            bw.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void e(String name, String describe) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String log = "#Error#" + df.format(new Date()).toString() + "/ " + name + ":  " + describe + "\n";
        try {

            bw.write(log);
            bw.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void i(String name, String describe) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String log = "#Info#" + df.format(new Date()).toString() + "/ " + name + ":  " + describe + "\n";
        try {
            bw.write(log);
            bw.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}