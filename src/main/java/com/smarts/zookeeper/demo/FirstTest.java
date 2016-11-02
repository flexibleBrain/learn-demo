package com.smarts.zookeeper.demo;

import java.util.List;
import java.util.Map;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

public class FirstTest {

	public static void main(String[] args) {
		String zkAddress = "192.168.16.83:2181,192.168.16.84:2181,192.168.16.63:2181";
		CuratorUtil curator = new CuratorUtil(zkAddress);
		// curator.createNode("/yxj/test1", "ÄãºÃabc11");
		// curator.createNode("/yxj/test2", "ÄãºÃabc22");
		// curator.updateNode("/yxj/test2", "ÄãºÃabc333");
		// curator.deleteNode("/yxj");
		// List<String> list = curator.listChildren("/yxj");
		// Map<String, String> map = curator.listChildrenDetail("/yxj");
		// System.out.println("=========================================");
		// for (String str : list) {
		// System.out.println(str);
		// }
		//
		// System.out.println("=========================================");
		// for (Map.Entry<String, String> entry : map.entrySet()) {
		// System.out.println(entry.getKey() + "=>" + entry.getValue());
		// }
		PathChildrenCache watcher = new PathChildrenCache(curator.getClient(), "/yxj", true);
		watcher.getListenable().addListener(new PathChildrenCacheListener() {

			@Override
			public void childEvent(CuratorFramework client1, PathChildrenCacheEvent event) throws Exception {
				ChildData data = event.getData();
				if (data == null) {
					System.out.println("No data in event[" + event + "]");
				} else {
					System.out.println("receive event: type=[" + event.getType() + "], path=["
							+ event.getData().getPath() + "], data=[" + new String(event.getData().getData())
							+ "],stat=[" + event.getData().getStat() + "]");
				}
			}
		});
		try {
			watcher.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("==========================> add listener");
		try {
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
