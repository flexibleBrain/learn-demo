package com.smarts.multiThread.demo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ThreadInterrupt {

	public static void main(String[] args) {
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		try {
			queue.put("d");
		} catch (InterruptedException e1) {
		}
		ExecutorService es = Executors.newSingleThreadExecutor();
		Future<String>  future= es.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				TimeUnit.SECONDS.sleep(1);
				System.out.println("sleep 10's.............");
				return "return";
			}
		});
		try {
			System.out.println(future.isCancelled());
			String str = future.get(4, TimeUnit.SECONDS);
			System.out.println(future.cancel(true));
			System.out.println(str);
			es.shutdown();
			es.shutdownNow();
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
	}
}
