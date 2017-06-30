package com.smarts.date;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by yangxuejun
 * on 2017/6/30.10:23
 */
public class TestWait {
    @Test
    public void testW(){
        final  Object mor = new Object();
        Thread c = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (mor) {
                    try {
                        mor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("customer thread start..............");
                }
            }
        });
        c.start();
        Thread p = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<3;i++){
                    System.out.println("producter sleep 1 's..........");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                synchronized (mor) {
                    mor.notifyAll();
                }
            }
        });
        p.start();
        try {
            p.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
