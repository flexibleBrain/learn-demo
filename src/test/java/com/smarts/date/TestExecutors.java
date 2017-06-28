package com.smarts.date;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangxuejun
 * on 2017/6/28.9:31
 */
public class TestExecutors {
    @Test
    public void testOne(){
        ExecutorService e = Executors.newFixedThreadPool(3);
        for(int i=0;i<3;i++){
            e.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("current thread name : "+Thread.currentThread().getName());
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
    @Test
    public void testThread(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("current Thread name : "+Thread.currentThread().getName());
            }
        };
        Thread t = new Thread(r);
        t.start();
    }
}
