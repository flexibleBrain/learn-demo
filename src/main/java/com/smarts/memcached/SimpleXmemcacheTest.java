package com.smarts.memcached;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.TextCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class SimpleXmemcacheTest {

	private static MemcachedClient client;
	public static void main(String[] args) throws IOException,
			TimeoutException, InterruptedException, MemcachedException {
		XMemcachedClientBuilder builder = new XMemcachedClientBuilder(
				AddrUtil.getAddresses("192.168.16.102:11211"));
		builder.setCommandFactory(new TextCommandFactory());
		builder.setConnectionPoolSize(1);
	    client = builder.build();
		Person p1 = new Person();
		p1.setAge(11);
		p1.setId(1);
		p1.setName("yxj1");
		List<Person> list = new ArrayList<Person>();
		list.add(p1);
		Person p2 = new Person();
		p2.setAge(22);
		p2.setId(2);
		p2.setName("yxj2");
		list.add(p2);
		
		Person p3 = new Person();
		p3.setAge(33);
		p3.setId(3);
		p3.setName("yxj3");
		List<Person> list1 = new ArrayList<Person>();
		list1.add(p3);
		Person p4 = new Person();
		p4.setAge(44);
		p4.setId(4);
		p4.setName("yxj4");
		list1.add(p4);
		
		client.add("list", 1, list);
		List<Person> lists = null;
		lists=getValue("list",lists);
		for(Person p:lists){
			System.out.println(p.getId()+":"+p.getName());
		}
		
		Map<String, List<Person>> map = new HashMap<String, List<Person>>();
		map.put("m1", list);
		map.put("m2", list1);
		client.add("map", 3, map);
		Map<String, List<Person>> map1=null;
		map1 = getValue("map",map1);
	    for(Map.Entry<String, List<Person>> entry:map1.entrySet()){
	    	System.out.println("key:"+entry.getKey());
	    	for(Person p:entry.getValue()){
	    		System.out.println("    id:"+p.getId()+" name:"+p.getName()+" age:"+p.getName());
	    	}
	    }
	
		client.shutdown();
	}
	
	public static <T> T getValue(String key,T t) throws TimeoutException, InterruptedException, MemcachedException{
		t = client.get(key);
		return t;
	}
}
