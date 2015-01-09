package com.smarts.multiThread.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


public class CallableTest {
	private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;
	public static void main(String[] args) {
		System.out.println(COUNT_BITS);
		System.out.println(CAPACITY);
		System.out.println(workerCountOf(1));
		ExecutorService es = Executors.newFixedThreadPool(3);
		Future<String> future = es.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				System.out.println("sleep 10's............");
				TimeUnit.SECONDS.sleep(10);
				return "future";
			}
		});
		
		System.out.println("dddddd");
		try {
			String str = future.get();
			System.out.println(str);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	private static int workerCountOf(int c)  { return c & CAPACITY; }
}
