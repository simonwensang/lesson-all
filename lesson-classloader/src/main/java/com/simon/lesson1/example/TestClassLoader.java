package com.simon.lesson1.example;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by sang on 2018/12/3.
 */
public class TestClassLoader {

    public static void main(String[] args) throws  Exception {
        String url = "file:///Users/souibun/IdeaProjects/lesson-all/lesson-2/target/lesson-2-1.0-SNAPSHOT.jar" ;
        String className = "com.simon.lesson2.bean.GameBean" ;
        URLClassLoader classLoader = null;
        URLClassLoader parentClassLoader = new URLClassLoader(new URL[]{new URL(url)});
        while(true){

            classLoader = new URLClassLoader(new URL[]{new URL(url)});//破坏双亲委派

           /// Class t = Class.forName("java.lang.Thread")};
            //双亲委派
           //  classLoader = new URLClassLoader(new URL[]{new URL(url)}, parentClassLoader);

            Class<?> loadClass = classLoader.loadClass(className);
            Object game = loadClass.newInstance();
            Method method = loadClass.getDeclaredMethod("start");
            method.setAccessible(true);
            method.invoke(game);

            Thread.sleep(3000L);
        }

    }

}
