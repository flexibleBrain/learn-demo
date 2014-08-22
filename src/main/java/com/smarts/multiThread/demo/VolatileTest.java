package com.smarts.multiThread.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VolatileTest {

	private int a = 0;
	boolean flag = false;
	volatile int v1 = 1;
	volatile int v2 = 1;
	CountDownLatch cd = new CountDownLatch(1);

	public synchronized void write() {
		a = 2;
		flag = true;
		// cd.countDown();
	}

	public synchronized void read() {
		// try {
		// cd.await();
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		if (flag) {
			int i = a * a;
			System.out.println(i);
		} else {
			System.out.println("a=" + a);
			System.out.println("flag=false");
		}
	}

	public  void readAndWrite() {
		int i = v1;
		int j = v2;
		a = i + j;
		v1 = i + 1;
		v2 = j + 2;
	}

	public static void main(String[] args) {
		final VolatileTest vt = new VolatileTest();
		ExecutorService es = Executors.newFixedThreadPool(2);
		es.execute(new Runnable() {

			@Override
			public void run() {
				vt.write();
				// vt.read();
			}
		});
		es.execute(new Runnable() {

			@Override
			public void run() {
				vt.read();
			}
		});
		System.out.println("over..........");
	}
}
