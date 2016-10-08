package com.smarts.zookeeper.demo;

import java.util.List;
import java.util.Map;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;

import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

public class CuratorUtil {

	private CuratorFramework client;

	public CuratorUtil(String zkAddress) {
		client = CuratorFrameworkFactory.newClient(zkAddress, new ExponentialBackoffRetry(1000, 3));
		client.getCuratorListenable().addListener(new NodeEventListener());
		client.start();
	}

	public boolean createNode(String nodeName, String value) {
		boolean suc = false;
		try {
			Stat stat = getClient().checkExists().forPath(nodeName);
			if (stat == null) {
				String opResult = null;
				if (Strings.isNullOrEmpty(value)) {
					opResult = getClient().create().creatingParentsIfNeeded().forPath(nodeName);
				} else {
					opResult = getClient().create().creatingParentsIfNeeded().forPath(nodeName,value.getBytes("UTF-8"));
				}
				suc = Objects.equal(nodeName, opResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return suc;
	}

	public boolean updateNode(String nodeName, String value) {
		boolean suc = false;
		try {
			Stat stat = getClient().checkExists().forPath(nodeName);
			if (stat != null) {
				Stat opRusult = getClient().setData().forPath(nodeName, value.getBytes(Charsets.UTF_8));
				suc = opRusult != null;
			}
		} catch (Exception e) {

		}
		return suc;
	}

	public CuratorFramework getClient() {
		return client;
	}

	public void deleteNode(String nodeName) {
		try {
			getClient().delete().deletingChildrenIfNeeded().forPath(nodeName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Map<String,String> listChildrenDetail(String node){
		Map<String,String> map = Maps.newHashMap();
		try{
			GetChildrenBuilder cb = getClient().getChildren();
			List<String> children = cb.forPath(node);
			GetDataBuilder dataBuilder = getClient().getData();
			if(children!=null){
				for(String child:children){
					String propPath = ZKPaths.makePath(node, child);
					map.put(child, new String(dataBuilder.forPath(propPath),Charsets.UTF_8));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	public List<String> listChildren(String node){
		List<String> children = null;
		try{
			GetChildrenBuilder cb = getClient().getChildren();
			children = cb.forPath(node);
		}catch(Exception e){
			e.printStackTrace();
		}
		return children;
	}
	
	public void addWatch(String node,boolean isSelf) throws Exception{
		if(isSelf){
			getClient().getData().watched().forPath(node);
		}else{
			getClient().getChildren().watched().forPath(node);
		}
	}
	
	public void addWatch(String node,boolean isSelf,Watcher watcher) throws Exception{
		if(isSelf){
			getClient().getData().usingWatcher(watcher).forPath(node);
		}else{
			getClient().getChildren().usingWatcher(watcher).forPath(node);
		}
	}
	public void addWatch(String node,boolean isSelf,CuratorWatcher watcher) throws Exception{
		if(isSelf){
			getClient().getData().usingWatcher(watcher).forPath(node);
		}else{
			getClient().getChildren().usingWatcher(watcher).forPath(node);
		}
	}
	
	public void destory(){
		if(client!=null){
			client.close();
		}
	}
	
}


final class NodeEventListener implements CuratorListener {

	@Override
	public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
		System.out.println(event.toString() + "..............................");
		final WatchedEvent watchedEvent = event.getWatchedEvent();
		if (watchedEvent != null) {
			System.out.println(watchedEvent.getState() + "========================" + watchedEvent.getType());
			if (watchedEvent.getState() == KeeperState.SyncConnected) {
			}
		}
	}

}
