package com.smarts.multiThread.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReentrantLockTest {

	private final int i;
	private int j;
	ReentrantLockTest obj;

	public ReentrantLockTest() {
		i = 10;
		j = 8;
	}

	public void write() {
		obj = new ReentrantLockTest();
	}

	public void read() {
		ReentrantLockTest obj1 = obj;
		System.out.println(obj1.i);
		System.out.println(obj1.j);
	}

	public static void main(String[] args) {
		final ReentrantLockTest rt = new ReentrantLockTest();
		ExecutorService ex = Executors.newFixedThreadPool(2);
		ex.execute(new Runnable() {

			@Override
			public void run() {
				rt.write();
			}
		});
		ex.execute(new Runnable() {

			@Override
			public void run() {
				rt.read();
			}
		});
	}
}
