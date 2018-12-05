package com.simon.lesson1.example;

import sun.net.spi.nameservice.dns.DNSNameService;

/**
 * Created by sang on 2018/12/5.
 */
public class BootStrapTest {

    public static void main(String[] args) {

        System.out.println(String.class.getClassLoader());

        System.out.println(DNSNameService.class.getClassLoader());

        System.out.println(BootStrapTest.class.getClassLoader());

    }

}
