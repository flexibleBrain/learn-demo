package com.smarts.memcached;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.TextCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * memcached 工具类
 * 
 * @author Yang xue jun<br>
 *         2014年8月27日 下午2:39:34
 *
 */
public class MemcachedUtils {

	private MemcachedClient memClient;

	private int exp = 1;
	private String ips = "smm-info01.wisers.com:21212";

	public MemcachedUtils() {
	}

	public void init() throws IOException {
		XMemcachedClientBuilder builder = new XMemcachedClientBuilder(ips);
		builder.setConnectionPoolSize(5);// 设置连接池
		builder.setCommandFactory(new TextCommandFactory());
		builder.setName("chartCache");
		builder.getConfiguration().setSessionIdleTimeout(10 * 1000); // 设置心跳间隔时间
		memClient = builder.build();
		/**
		 * Xmemcached默认会做两个优化：将连续的单个get合并成一个multi
		 * get批量操作获取，将连续的请求合并成socket发送缓冲区大小的buffer发送。
		 * 如果你对响应时间比较在意，那么可以将合并的因子减小，或者关闭合并buffer的优化：
		 */

		memClient.setMergeFactor(50);
		memClient.setOptimizeMergeBuffer(false);
	}

	public synchronized void update(String key, Object t, int campaignId,
			int timeStamp) throws TimeoutException, InterruptedException,
			MemcachedException {
		if(t!=null)
		memClient.set(key, exp, t);
		memClient.set(campaignId + "", 0, timeStamp);
	}

	public MemcachedClient getMemClient() {
		return memClient;
	}

	public static void main(String[] args) throws TimeoutException,
			InterruptedException, MemcachedException, IOException {
		MemcachedUtils memClient = new MemcachedUtils();
		memClient.init();
		// memClient.getMemClient().delete("111");
		// memClient.getMemClient().set("1111", 1, "1111");
		// memClient.getMemClient().set("1111", 1, "2222");
//		String c = null;
//		memClient.getMemClient().set("111", 1, null);
//		int z = 0;
//		if (c != null)
//			z = Integer.parseInt(c);
		String key = "appendTest";
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		
//		memClient.getMemClient().set(key, 0, list);
		List<Integer> l1 = memClient.getMemClient().get(key);
		for(Integer i:l1){
			System.out.println(i);
		}
		
//		l1.add(3);
//		memClient.getMemClient().set(key, 0, l1);
//		List<Integer> l2 = memClient.getMemClient().get(key);
//		for(Integer i:l2){
//			System.out.println(i);
//		}
		System.out.println("over..................");
		memClient.getMemClient().shutdown();
	}
}
