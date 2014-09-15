package com.smarts.multiThread.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorsTest {

	public static void main(String[] args) {
		
		ExecutorService es = Executors.newFixedThreadPool(2);
		//任务数超过线程数时，会先执行最大的线程数任务，等线程释放后再执行其余的任务
		for(int i=0;i<3;i++){
			es.execute(new Runnable() {
				
				@Override
				public void run() {
					for(int j=0;j<5;j++){
						try {
							System.out.println("Thread name:"+Thread.currentThread().getName()+"   :  "+j);
							TimeUnit.SECONDS.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
		es.shutdown();
	}
}
