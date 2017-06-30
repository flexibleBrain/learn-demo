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
    public void test22(){
        int COUNT_BITS = Integer.SIZE - 3;
        int RUNNING    = -1 << COUNT_BITS;
        System.out.println(RUNNING);
        System.out.println(RUNNING|0);
    }
    @Test
    public void testOne(){
        ExecutorService e = Executors.newFixedThreadPool(1);
        for(int i=0;i<1;i++){
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
            this.wait();
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
