package com.simon.lesson4.server;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import com.sun.deploy.net.MessageHeader;

import javax.servlet.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TomcatServer3 {
//性能指标 线程池大小
    private static ExecutorService threadPool = Executors.newFixedThreadPool(20);

    public static void main(String[] args)  throws Exception{
        Map<String,PropertiesLoad.WebXml> webXmlMap = PropertiesLoad.load();
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
                            //解析http请求  找到对应的项目project和servlet名称
                            String project = "";
                            String path = "";

                            PropertiesLoad.WebXml servletLoad = webXmlMap.get(project);

                            String servletName = servletLoad.servletMapping.get(path);

                            Servlet servlet = servletLoad.servletInstances.get(servletName);

                            servlet.service(creatRequest(),creatResponse());
                            System.out.println("-----------------end");

                            OutputStream response = request.getOutputStream();
                            response.write("HTTP/1.1 200 OK\r\n".getBytes());
                            response.write("Content-Length: 11\r\n\r\n".getBytes());
                            response.write("hello world!".getBytes());
                            response.flush();
                        }catch (IOException e){
                            e.printStackTrace();
                        }catch (ServletException e){
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
    public static ServletRequest creatRequest(){
        return new ServletRequest() {
            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Enumeration getAttributeNames() {
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

            }

            @Override
            public int getContentLength() {
                return 0;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletInputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public String getParameter(String s) {
                return null;
            }

            @Override
            public Enumeration getParameterNames() {
                return null;
            }

            @Override
            public String[] getParameterValues(String s) {
                return new String[0];
            }

            @Override
            public Map getParameterMap() {
                return null;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public String getScheme() {
                return null;
            }

            @Override
            public String getServerName() {
                return null;
            }

            @Override
            public int getServerPort() {
                return 0;
            }

            @Override
            public BufferedReader getReader() throws IOException {
                return null;
            }

            @Override
            public String getRemoteAddr() {
                return null;
            }

            @Override
            public String getRemoteHost() {
                return null;
            }

            @Override
            public void setAttribute(String s, Object o) {

            }

            @Override
            public void removeAttribute(String s) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public Enumeration getLocales() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public RequestDispatcher getRequestDispatcher(String s) {
                return null;
            }

            @Override
            public String getRealPath(String s) {
                return null;
            }

            @Override
            public int getRemotePort() {
                return 0;
            }

            @Override
            public String getLocalName() {
                return null;
            }

            @Override
            public String getLocalAddr() {
                return null;
            }

            @Override
            public int getLocalPort() {
                return 0;
            }
        };
    }
    public static ServletResponse creatResponse(){
        return  new ServletResponse() {
            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletOutputStream getOutputStream() throws IOException {
                return null;
            }

            @Override
            public PrintWriter getWriter() throws IOException {
                return null;
            }

            @Override
            public void setCharacterEncoding(String s) {

            }

            @Override
            public void setContentLength(int i) {

            }

            @Override
            public void setContentType(String s) {

            }

            @Override
            public void setBufferSize(int i) {

            }

            @Override
            public int getBufferSize() {
                return 0;
            }

            @Override
            public void flushBuffer() throws IOException {

            }

            @Override
            public void resetBuffer() {

            }

            @Override
            public boolean isCommitted() {
                return false;
            }

            @Override
            public void reset() {

            }

            @Override
            public void setLocale(Locale locale) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }
        };
    }

}