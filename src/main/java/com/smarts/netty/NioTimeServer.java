package com.smarts.netty;

/**
 * Created by yangxuejun
 * on 16-11-14 下午8:59
 */
public class NioTimeServer {
    public static void main(String[] args) {
        int port = 8080;
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer,"Nio-MultiplexertimeServer-001").start();

    }
}
