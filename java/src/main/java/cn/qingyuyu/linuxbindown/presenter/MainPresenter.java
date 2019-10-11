package cn.qingyuyu.linuxbindown.presenter;


import cn.qingyuyu.linuxbindown.Constant;
import cn.qingyuyu.linuxbindown.view.WebViewControl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;


@SuppressWarnings("NonAsciiCharacters")
public class MainPresenter {
    private WebViewControl mi;

    private int 启动进度 = 1;

    public String filePath="";

    private boolean running = false;

    public MainPresenter(WebViewControl mi) {
        this.mi = mi;
    }


    public void start(String sd,String pass) {
        if (!running) {
            running = true;
            new Thread(() -> {
                try {
                    Process process = Runtime.getRuntime().exec("chmod 777 "+ Constant.resourceFolder+Constant.exeName);
                    process.waitFor();
                    process.destroy();

                    //String[] cmds = {"/bin/bash", "-c", "echo \"" + pass + "\" | " + "sudo" + " -S " + Constant.resourceFolder+Constant.exeName+" "+filePath+" /dev/"+sd};
                    String[] cmds = {"/bin/bash" ,"-c","echo " + pass + " | sudo -S "+Constant.resourceFolder+Constant.exeName+" "+filePath+" /dev/"+sd+" >> "+Constant.resourceFolder+new Date().getTime()};
                    process = Runtime.getRuntime().exec(cmds);

                    InputStreamReader ir = new InputStreamReader(process.getInputStream());
                    BufferedReader input = new BufferedReader(ir);

                    String line="";

                    while ((line=input.readLine())!=null) {
                        System.out.println(line);
                        Thread.sleep(100);
                    }

                    input.close();
                    ir.close();
                    process.destroy();
                    mi.setRunLog("写入完成");
                } catch (Exception e) {
                    e.printStackTrace();
                    mi.setRunLog("写入失败");
                }
                running=false;

            }).start();

        }

    }
   public String[] getSDs()
   {
       String sds[]={"123"};
       try {
           Process process = Runtime.getRuntime().exec("ls /dev");
           process.waitFor();
           InputStreamReader ir = new InputStreamReader(process.getInputStream());
           BufferedReader input = new BufferedReader(ir);

           String line="";
           ArrayList<String> sdlist=new ArrayList<>();
           while ((line=input.readLine())!=null) {
              if(line.startsWith("sd"))
                  sdlist.add(line);
           }
           input.close();
           ir.close();
           process.destroy();
           sds=sdlist.toArray(new String[0]);
       } catch (Exception e) {
           e.printStackTrace();
       }
       return sds;
   }
   public String getSize(String sd)
   {

       String size="未知，谨慎操作";
       try {
           Process process = Runtime.getRuntime().exec("df");
           process.waitFor();
           InputStreamReader ir = new InputStreamReader(process.getInputStream());
           BufferedReader input = new BufferedReader(ir);

           String line="";
           while ((line=input.readLine())!=null) {
               if(line.contains(sd)) {
                   String ss[]=line.split(" ");
                  for(int i=1;i<ss.length;i++)
                      if(!ss[i].equals(""))
                      {
                       try{
                          float gb= Integer.parseInt(ss[i])/1024f/1024f;
                           DecimalFormat decimalFormat=new DecimalFormat(".00");
                           size=decimalFormat.format(gb)+"GB";
                       }catch (Exception e)
                       {
                           size="获取失败";
                       }
                       break;
                      }

               }
           }
           input.close();
           ir.close();
           process.destroy();
       } catch (Exception e) {
           e.printStackTrace();
       }
       return size;


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
