package com.smarts.dubbo.boot;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by yangxuejun
 * on 16-11-1 下午9:27
 */
public class BootStartp {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-*.xml");
        ctx.start();
        System.out.println("start....................................");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
