package com.smarts.date;

/**
 * Created by yangxuejun
 * on 2017/6/30.9:14
 */
public class ConcurrencyTest {
    private  static final long count = 100000000L;
    public static void main(String[] args) {
        concurrency();
        serial();
    }
    private static void concurrency(){
        long start = System.currentTimeMillis();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int a =0;
                for(long i=0L;i<count;i++){
                    a+=5;
                }
            }
        });
        t.start();
        int b =0;
        for(long i=0L;i<count;i++){
            b--;
        }
        long time = System.currentTimeMillis()-start;
        System.out.println("concurrency : "+time + "ms,b="+b);
    }

    private  static void serial(){
        long start = System.currentTimeMillis();
        int a = 0;
        for(long i=0L;i<count;i++){
            a+=5;
        }
        int b=0;
        for(long i=0;i< count;i++){
            b--;
        }
        long time = System.currentTimeMillis()-start;
        System.out.println("serial time : "+time+"ms, a="+a+" ,b="+b);
    }
}
