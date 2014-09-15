package com.smarts.exception;

public class CacheException {

	public static void main(String[] args) {
		ThrowExceptionClass tec = new ThrowExceptionClass();
		try {
			tec.get1();
            System.out.println(true);
		} catch (Exception e) {
			System.out.println(false);
		}

	}
}
