package com.smarts.netty;

import org.apache.xpath.SourceTree;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;

/**
 * Created by yangxuejun
 * on 16-11-7 下午9:19
 */
public class TimeServer {
    public static void main(String[] args) {
        int port =8080;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("The time server is start in port : "+port);
            Socket socket = null;
            while (true){
                socket = serverSocket.accept();
                System.out.println("accept : "+socket.getRemoteSocketAddress().toString());
                Executors.newSingleThreadExecutor().execute(new TimeServerHandler(socket));
                System.out.println("over..................................");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                System.out.println("Time time server close");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
