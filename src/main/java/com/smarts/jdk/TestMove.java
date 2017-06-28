package com.smarts.jdk;

/**
 * Created by yangxuejun
 * on 2017/5/17.16:18
 */
public class TestMove {
    public static void main(String[] args) {
        int i = -10;
        System.out.println(Integer.toBinaryString(i));
        int j = i>>1;
        int c = j<<2;
        System.out.println("j : "+j +" - "+Integer.toBinaryString(j));
        System.out.println(" i : "+i+" - "+Integer.toBinaryString(i));
        System.out.println("c : "+c +" - "+Integer.toBinaryString(c));
        System.out.println(Math.pow(2,2));
    }
}
