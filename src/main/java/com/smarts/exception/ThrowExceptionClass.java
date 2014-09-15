package com.smarts.exception;

public class ThrowExceptionClass {

	public void get1(){
		try{
			ExceptionClass.get();
		}catch(Exception e){
//			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
