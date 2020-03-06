package com.simon.lesson3.mvc;

import com.simon.lesson3.annotation.Controller;
import com.simon.lesson3.annotation.Qualifier;
import com.simon.lesson3.annotation.RequestMapping;
import com.simon.lesson3.annotation.Service;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * Created by sang on 2018/12/11.
 */
public class MyDispatchServlet extends HttpServlet{
    private static  final  long serialVersionUID = 1L;

    private Properties properties = new Properties();

    private List<String> classNames = new ArrayList<String>();

    private Map<String,Object> ioc = new HashMap<String, Object>();
    //保存url和controller关系
    private Map<String, Method> handlerMapping = new HashMap<String,Method>();

    @Override
    public void init(ServletConfig config) throws ServletException {

        //1加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        //2初始化所有的相关联的类，扫描用户设置的包下的所有类
        doScanner(properties.getProperty("scanPackage"));

        //3 拿到扫描到的类，通过反射机制实例化，并放到ioc容器中 beanname默认首字母小写
        doInstance();

        //4 初始化HandlerMapping(将URL和Method对应上)
        initHanlderMapping();

        //5 实现注入
        doIoc();

    }

    private void doIoc(){

        if(ioc.isEmpty()){
            return;
        }

        for(Map.Entry entry : ioc.entrySet()){
           Class clazz = entry.getValue().getClass();
           Field[] fields = clazz.getDeclaredFields();
           for(Field field : fields){
               field.setAccessible(true);
               if(!field.isAnnotationPresent(Qualifier.class)){
                    continue;
               }
               Qualifier qualifier = field.getAnnotation(Qualifier.class);
               String key  ;
               if(!"".equals(qualifier.value()) ){
                   key=qualifier.value() ;
               }else{
                   key =field.getName();
               }
               try {
                   field.set(entry.getValue(),ioc.get(key));
               }catch (IllegalArgumentException e) {
                    e.printStackTrace();
               }catch (IllegalAccessException e){
                   e.printStackTrace();
               }
           }

        }

    }

    private void initHanlderMapping(){
        if(ioc.isEmpty()){
            return ;
        }
        Map<String,Object> urlBeanMapping = new HashMap<String ,Object>();
        for(Map.Entry entry : ioc.entrySet()){
            Class<? extends Object> clazz = entry.getValue().getClass();
            if(!clazz.isAnnotationPresent(Controller.class)){
                continue;
            }
            RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
            String baseUrl = "";
            if(requestMapping!=null && requestMapping.value().length>0){
                baseUrl =  requestMapping.value()[0];
            }
           Method[] methods = clazz.getDeclaredMethods();
            for(Method method : methods ){
                //method.setAccessible(true);
               if( !method.isAnnotationPresent(RequestMapping.class)){
                   continue;
               }
              RequestMapping requestMappingMethod =  method.getAnnotation(RequestMapping.class);
                String url =(baseUrl+"/"+requestMappingMethod.value()[0]).replaceAll("/+","/");
                handlerMapping.put(url,method);
                //这个是否要 newInstence（）
                urlBeanMapping.put(url,entry.getValue());
                System.out.println(url+"="+method);
            }

        }


    }

    private void doInstance(){

        if(classNames.isEmpty()){
            return ;
        }

        for(String className : classNames){
            try {
                //Class<?> clazz =  getClass().getClassLoader().loadClass(className);
                Class<?> clazz = Class.forName(className);
                if(clazz.isAnnotationPresent(Controller.class)){
                    Controller controller = clazz.getAnnotation(Controller.class);
                    String key = controller.value();
                    if(!"".equals(key) && key!=null){
                        ioc.put(key ,clazz.newInstance());
                    }else {
                        ioc.put( tolowFirstWord(clazz.getSimpleName()),clazz.newInstance());
                    }
                }else if(clazz.isAnnotationPresent(Service.class)){
                    Service service = clazz.getAnnotation(Service.class);
                    String key = service.value();
                    if(!"".equals(key) && key!=null){
                        ioc.put(key,clazz.newInstance());
                    }else{
                        ioc.put(tolowFirstWord(clazz.getSimpleName()),clazz.newInstance());
                    }
                }
                continue;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private String tolowFirstWord(String name){

        return name;
    }

    private void doScanner(String packageName){

        URL url = getClass().getClassLoader().getResource("/"+packageName.replaceAll("\\.","."));
        File dir = new File(url.getFile());

        for(File file : dir.listFiles()){
            if(file.isDirectory()){
                doScanner(packageName+"."+file.getName());
            }else{
                String className = packageName+"."+file.getName().replace(".class","");
                classNames.add(className);
                System.out.println("Spring容器扫描的类名："+packageName+"."+file.getName());
            }
        }

    }

    private void  doLoadConfig(String location){
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(location);
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(resourceAsStream!=null){
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public String getInitParameter(String name) {
        return super.getInitParameter(name);
    }

    @Override
    public Enumeration getInitParameterNames() {
        return super.getInitParameterNames();
    }

    @Override
    public ServletConfig getServletConfig() {
        return super.getServletConfig();
    }

    @Override
    public ServletContext getServletContext() {
        return super.getServletContext();
    }

    @Override
    public String getServletInfo() {
        return super.getServletInfo();
    }


    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void log(String msg) {
        super.log(msg);
    }

    @Override
    public void log(String message, Throwable t) {
        super.log(message, t);
    }

    @Override
    public String getServletName() {
        return super.getServletName();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected long getLastModified(HttpServletRequest req) {
        return super.getLastModified(req);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doHead(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doTrace(req, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        super.service(req, res);
    }
}
