package com.simon.lesson1.example;

import sun.misc.Launcher;
import sun.net.spi.nameservice.dns.DNSNameService;

import java.net.URL;

/**
 * Created by sang on 2018/12/5.
 */
public class BootStrapTest {

    public static void main(String[] args) {

        System.out.println(String.class.getClassLoader());

        System.out.println(DNSNameService.class.getClassLoader());

        System.out.println(BootStrapTest.class.getClassLoader());
        System.out.println(BootStrapTest.class.getClassLoader().getParent());
        System.out.println(BootStrapTest.class.getClassLoader().getParent().getParent());


        URL[] urls = Launcher.getBootstrapClassPath().getURLs();
        for(URL u : urls){
            System.out.println(u);
        }
    }

/*    Bootstrap ClassLoader是由C/C++编写的，它本身是虚拟机的一部分，所以它并不是一个JAVA类，
    也就是无法在java代码中获取它的引用，JVM启动时通过Bootstrap类加载器加载rt.jar等核心jar包中的class文件，
    之前的int.class,String.class都是由它加载。然后呢，我们前面已经分析了，JVM初始化sun.misc.Launcher并创建
    ExtensionClassLoader和AppClassLoader实例。并将ExtClassLoader设置为AppClassLoader的父加载器。Bootstrap没有父加载器，
    但是它却可以作用一个ClassLoader的父加载器。比如ExtClassLoader。这也可以解释之前通过ExtClassLoader的getParent方法获取为Null的现象*/

}
