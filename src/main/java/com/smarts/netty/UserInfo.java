package com.smarts.netty;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Created by yangxuejun
 * on 16-11-24 下午10:35
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 858136617855463329L;
    private String uname;
    private int uid;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public  UserInfo buildName(String uname){
        this.uname = uname;
        return this;
    }
    public  UserInfo buildId(int id){
        this.uid = id;
        return this;
    }
    public byte[] codeC(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] value = this.uname.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.uid);
        buffer.flip();
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return  result;
    }

    public static void main(String[] args) throws IOException {
        UserInfo u = new UserInfo();
        u.buildId(100).buildName("Welcome to netty");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(u);
        os.flush();;
        os.close();
        byte[] b = bos.toByteArray();
        System.out.println("The Jdk serializable length is : "+b.length);
        bos.close();
        System.out.println("-----------------------------------------");
        System.out.println("The byte arrayl serializable length is : "+u.codeC().length);
    }
}