package com.simon.lesson4.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TomcatServer {

    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args)  throws Exception{

        ServerSocket server = new ServerSocket(8080);
        System.out.println("tomcat启动成功!");
        while(!server.isClosed()){
            Socket request = server.accept();
            threadPool.execute(
                    ()->{
                        try {
                            InputStream in = request.getInputStream();
                            System.out.println("收到请求！");
                            String msg = null;
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                            while ((msg = reader.readLine()) != null) {
                                if (msg.length() == 0) {
                                    break;
                                }
                                System.out.println("收到信息：" + msg);
                            }
                            System.out.println("-----------------end");
                        }catch (IOException e){
                            e.printStackTrace();
                        }finally {
                            if(request!=null){
                               try{ request.close();}catch (IOException e){e.printStackTrace();}
                            }
                        }
                    }

            );

        }




    }

}