package com.smarts.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class WriteFileUtils {

	public static void write(String content) throws IOException {
		write(content,"d://page.html",false);
	}
	
	public static void write(String content,String file,boolean append) throws IOException{
		FileOutputStream fos = new FileOutputStream(file,append);
		OutputStreamWriter out = new OutputStreamWriter(fos, "GBK");
		BufferedWriter bw = new BufferedWriter(out);
        bw.write(content);
        bw.flush();
        bw.close();
	}
	public static void main(String[] args) throws IOException {
		write("你好");
	}
}
