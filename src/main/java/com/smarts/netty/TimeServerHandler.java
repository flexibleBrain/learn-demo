package com.smarts.netty;

import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * Created by yangxuejun
 * on 16-11-7 下午9:04
 */
public class TimeServerHandler implements  Runnable {
    private Socket socket;
    public TimeServerHandler(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            String CurrentTime = null;
            String body = null;
            while(true){
                body = in.readLine();
                if(body == null){
                    break;
                }
                System.out.println("The time server receive order:"+body);
                CurrentTime = "query time order".equalsIgnoreCase(body)?new Date().toString():"bad order";
                out.println(CurrentTime);
            }
            System.out.println("read out....................");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (socket!=null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
