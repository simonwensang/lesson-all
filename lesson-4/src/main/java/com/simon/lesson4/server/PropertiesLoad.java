package com.simon.lesson4.server;

import com.simon.lesson4.utils.WebXmlConfigUtil;

import javax.servlet.Servlet;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sang on 2018/12/18.
 */
public class PropertiesLoad {

    private static String webapps = "/Users/souibun/mytomcat";

    public static Map<String,WebXml> load(){
        //解析xml 实例化servlet
        Map<String,WebXml> result = new HashMap<String,WebXml>();
        File[] projects = new File(webapps).listFiles(file -> file.isDirectory());
        for(File project : projects){

            WebXml webXml = WebXmlConfigUtil.loadXml(project.getPath()+"/WEB-INF/web.xml");
            webXml.projectPath = project.getPath();
            webXml.loadServler();
            result.put(project.getName(),webXml);
        }

        return  result;
    }

    public static class WebXml{

        public String projectPath = null;

        Map<String,String> servlets = new HashMap<String,String>();

        Map<String,String> servletMapping = new HashMap<String,String>();

        Map<String,Servlet> servletInstances = new HashMap<String,Servlet>();

        public void loadServler() {
            try {
                URLClassLoader classLoader = new URLClassLoader(new URL[]{new URL("file:"+projectPath+"//WEB-INF/classes")});
                for (Map.Entry<String,String> entry : servlets.entrySet()){
                    String servletName = entry.getKey();
                    String servletClassName = entry.getValue();
                    Class<?> servlet = classLoader.loadClass(servletClassName);
                    servletInstances.put(servletName,(Servlet)servlet.newInstance());
                }

            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }catch (InstantiationException e){
                e.printStackTrace();
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }

    }
}
