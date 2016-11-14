package com.smarts.dubbo.boot;

import com.alibaba.dubbo.rpc.service.EchoService;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.smarts.dubbo.service.FirstService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * Created by yangxuejun
 * on 16-11-1 下午10:27
 */
public class BootStart {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring*.xml");
        ctx.start();
        FirstService service = (FirstService)ctx.getBean("demoService");
//        EchoService echoService = (EchoService)service;
//        GenericService genericService = (GenericService)service;
//        genericService.$invoke()
//        String status = (String) echoService.$echo("ok");
//        System.out.println(status+".................");
        System.out.println(service.sayHello("yxj"));
        System.out.println(service.sayHello("yxj1"));
        System.out.println(service.sayHello("yxj2"));
        System.out.println(service.sayHello("yxj3"));
        System.out.println(service.sayHello("yxj4"));
        TimeUnit.SECONDS.sleep(10);
        System.exit(0);
    }
}
