package com.simon.lesson4.server;

import javax.servlet.Servlet;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TomcatServer3 {
//性能指标 线程池大小
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
                            //解析http请求  找到对应的项目和servlet
                            String project = "";
                            String path = "";
                            Servlet servlet = null;

                            System.out.println("-----------------end");

                            OutputStream response = request.getOutputStream();
                            response.write("HTTP/1.1 200 OK\r\n".getBytes());
                            response.write("Content-Length: 11\r\n\r\n".getBytes());
                            response.write("hello world!".getBytes());
                            response.flush();
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

        server.close();


    }

}