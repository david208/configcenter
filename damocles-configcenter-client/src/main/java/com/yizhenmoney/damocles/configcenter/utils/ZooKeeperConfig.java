package com.yizhenmoney.damocles.configcenter.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Component;

import com.yizhenmoney.damocles.configcenter.config.Config;

@Component
public class ZooKeeperConfig implements Config {
	@SuppressWarnings("rawtypes")
	private ConcurrentSkipListSet watchers = new ConcurrentSkipListSet();
	private CuratorFramework client;
	private CuratorFramework baseClient;
	private static final String path = "application.properties";
	private PropitesUtils pu = new PropitesUtils(path);
	private static ZooKeeperConfig zk = null;

	// private static ZooKeeperConfig baseZk = null;
	public CuratorFramework getClient() {
		return client;
	}

	public CuratorFramework getBaseClient() {
		return baseClient;
	}

	/**
	 * 连接zookeeper
	 * */
	public static ZooKeeperConfig getZookeeperConfig(String auth)
			throws IOException {
		if (zk == null) {
			zk = new ZooKeeperConfig(auth);
		}
		return zk;
	}

	// /**
	// * 连接base zookeeper
	// * */
	// public static ZooKeeperConfig getBaseZookeeperConfig(String auth) throws
	// IOException {
	// if (baseZk==null) {
	// baseZk = new ZooKeeperConfig(1,auth);
	// }
	// return baseZk;
	// }
	public ZooKeeperConfig(String auth) throws IOException {
		client = CuratorFrameworkFactory.builder()
				.connectString(pu.readValue("zoo.url")).sessionTimeoutMs(5000)
				.connectionTimeoutMs(1000)
				.authorization("digest", auth.getBytes("utf-8"))
				.retryPolicy(new ExponentialBackoffRetry(3, 10000))
				.namespace(pu.readValue("zoo.nameSpace")).build();
		client.start();
	}

	// public ZooKeeperConfig(Integer base,String auth) throws IOException {
	// baseClient = CuratorFrameworkFactory
	// .builder()
	// .connectString(pu.readValue("zoo.url"))
	// .sessionTimeoutMs(5000)
	// .connectionTimeoutMs(1000)
	// .authorization("digest",
	// auth.getBytes("utf-8"))
	// .retryPolicy(
	// new ExponentialBackoffRetry(Integer.parseInt(pu
	// .readValue("zoo.base_sleep_timems")), Integer
	// .parseInt(pu.readValue("zoo.max_retries"))))
	// .namespace(pu.readValue("zoo.base.nameSpace")).build();
	// baseClient.start();
	// }

	/**
	 * 注册监控
	 * */
	public void register(String registeNode) throws Exception {
		byte[] data = "disable".getBytes("utf-8");// 节点值
		CuratorWatcher watcher = new ZKWatchRegister(registeNode, data, client,
				pu.readValue("zoo.nameSpace")); // 创建一个register
		addReconnectionWatcher(registeNode,
				ZookeeperWatcherType.CREATE_ON_NO_EXITS, watcher);
		data = client.getData().usingWatcher(watcher).forPath(registeNode);
		System.out.println("get path form zk : " + registeNode + ":"
				+ new String(data, "utf-8"));
	}

	// /**
	// * 注册监控
	// * */
	// public void baseRegister(String registeNode) throws Exception {
	// byte[] data = "disable".getBytes("utf-8");// 节点值
	// CuratorWatcher watcher = new ZKWatchRegister(registeNode, data,
	// baseClient,pu.readValue("zoo.base.nameSpace")); // 创建一个register
	// baseAddReconnectionWatcher(registeNode,
	// ZookeeperWatcherType.CREATE_ON_NO_EXITS, watcher);
	// data = baseClient.getData().usingWatcher(watcher).forPath(registeNode);
	// System.out.println("get path form zk : " + registeNode + ":"
	// + new String(data, "utf-8"));
	// }
	/**
	 * 是否存在
	 * */
	private boolean exists(CuratorFramework client, String path)
			throws Exception {
		Stat stat = client.checkExists().forPath(path);
		return !(stat == null);
	}

	/**
	 * 获取配置文件数据 并监听数据
	 * */
	@Override
	public List<String> getConfig2(String path) throws Exception {
		List<String> result = new ArrayList<String>();
		if (!exists(client, path)) {
			throw new RuntimeException("Path " + path + " does not exists.");
		}
		/**
		 * 获得节点下的节点 如properties/db/username properties/db/password
		 * 现在只读到properties
		 * */
		List<String> datas = client.getChildren().forPath(path);
		if (!datas.isEmpty()) {
			for (String string : datas) {
				while (exists(client, path + "/" + string)) {
					List<String> childrenNode = client.getChildren().forPath(
							path + "/" + string);
					for (String string2 : childrenNode) {
						if (exists(client, path + "/" + string + "/" + string2)) {
							String d = new String(client.getData().forPath(
									path + "/" + string + "/" + string2),
									"utf-8");
							register(path + "/" + string + "/" + string2);
							result.add(d);
						}
					}
					break;
				}
				// //判断是否存在 判断db是否存在
				// if (exists(client, path + "/"+string)) {
				// //获取db下面的节点
				// List<String> childrenNode = client.getChildren().forPath(path
				// + "/"+string);
				// for (String string2 : childrenNode) {
				//
				// }
				// String d = new String(client.getData().forPath(
				// path + "/" + string), "utf-8");
				// register(path + "/" + string);
				// result.add(d);
				// }
			}
		}
		return result;
	}

	/**
	 * 获取配置文件数据 并监听数据
	 * */
	/*
	 * @Override public List<String> getBaseConfig(String path) throws Exception
	 * { List<String> result = new ArrayList<String>(); if (!exists(baseClient,
	 * path)) { throw new RuntimeException("Path " + path +
	 * " does not exists."); }
	 *//**
	 * 获得节点下的节点 如properties/db/username properties/db/password
	 * 现在只读到properties
	 * */
	/*
	 * List<String> datas = baseClient.getChildren().forPath(path); if
	 * (!datas.isEmpty()) { for (String string : datas) {
	 * while(exists(baseClient, path + "/"+string)){ List<String> childrenNode =
	 * baseClient.getChildren().forPath(path + "/"+string); for (String string2
	 * : childrenNode) { if(exists(baseClient, path + "/"+string+"/"+string2)){
	 * String d = new String(baseClient.getData().forPath( path + "/" +
	 * string+"/"+string2), "utf-8"); baseRegister(path + "/" +
	 * string+"/"+string2); result.add(d); } } break; } // //判断是否存在 判断db是否存在 //
	 * if (exists(client, path + "/"+string)) { // //获取db下面的节点 // List<String>
	 * childrenNode = client.getChildren().forPath(path + "/"+string); // for
	 * (String string2 : childrenNode) { // // } // String d = new
	 * String(client.getData().forPath( // path + "/" + string), "utf-8"); //
	 * register(path + "/" + string); // result.add(d); // } } } return result;
	 * }
	 */
	public List<String> getConfig(String path) throws Exception {
		List<String> result = new ArrayList<String>();
		if (!exists(client, path)) {
			throw new RuntimeException("Path " + path + " does not exists.");
		}

		String d = new String(client.getData().forPath(path), "utf-8");
		result.add(d);
		return result;
	}

	/**
	 * 监听事件
	 * */
	@SuppressWarnings("unchecked")
	public void addReconnectionWatcher(final String path,
			final ZookeeperWatcherType watcherType, final CuratorWatcher watcher) {
		synchronized (this) {
			if (!watchers.contains(watcher.toString()))// 不要添加重复的监听事件
			{
				watchers.add(watcher.toString());
				System.out.println("add new watcher " + watcher);
				client.getConnectionStateListenable().addListener(
						new ConnectionStateListener() {
							@Override
							public void stateChanged(CuratorFramework client,
									ConnectionState newState) {
								System.out.println(newState);
								if (newState == ConnectionState.LOST) {// 处理session过期
									try {
										if (watcherType == ZookeeperWatcherType.EXITS) {
											client.checkExists()
													.usingWatcher(watcher)
													.forPath(path);
										} else if (watcherType == ZookeeperWatcherType.GET_CHILDREN) {
											client.getChildren()
													.usingWatcher(watcher)
													.forPath(path);
										} else if (watcherType == ZookeeperWatcherType.GET_DATA) {
											client.getData()
													.usingWatcher(watcher)
													.forPath(path);
										} else if (watcherType == ZookeeperWatcherType.CREATE_ON_NO_EXITS) {
											// ephemeral类型的节点session过期了，需要重新创建节点，并且注册监听事件，之后监听事件中，
											// 会处理create事件，将路径值恢复到先前状态
											Stat stat = client.checkExists()
													.usingWatcher(watcher)
													.forPath(path);
											if (stat == null) {
												System.err.println("to create");
												client.create()
														.creatingParentsIfNeeded()
														.withMode(
																CreateMode.EPHEMERAL)
														.withACL(
																ZooDefs.Ids.OPEN_ACL_UNSAFE)
														.forPath(path);
											}
										}
									} catch (Exception e) {
										e.printStackTrace();

									}
								}
							}
						});
			}
		}
	}
	/**
	 * 监听事件
	 * */
	/*
	 * @SuppressWarnings("unchecked") public void
	 * baseAddReconnectionWatcher(final String path, final ZookeeperWatcherType
	 * watcherType, final CuratorWatcher watcher) { synchronized (this) { if
	 * (!watchers.contains(watcher.toString()))// 不要添加重复的监听事件 {
	 * watchers.add(watcher.toString()); System.out.println("add new watcher " +
	 * watcher); baseClient.getConnectionStateListenable().addListener( new
	 * ConnectionStateListener() {
	 * 
	 * @Override public void stateChanged(CuratorFramework client,
	 * ConnectionState newState) { System.out.println(newState); if (newState ==
	 * ConnectionState.LOST) {// 处理session过期 try { if (watcherType ==
	 * ZookeeperWatcherType.EXITS) { baseClient.checkExists()
	 * .usingWatcher(watcher) .forPath(path); } else if (watcherType ==
	 * ZookeeperWatcherType.GET_CHILDREN) { baseClient.getChildren()
	 * .usingWatcher(watcher) .forPath(path); } else if (watcherType ==
	 * ZookeeperWatcherType.GET_DATA) { baseClient.getData()
	 * .usingWatcher(watcher) .forPath(path); } else if (watcherType ==
	 * ZookeeperWatcherType.CREATE_ON_NO_EXITS) { //
	 * ephemeral类型的节点session过期了，需要重新创建节点，并且注册监听事件，之后监听事件中， //
	 * 会处理create事件，将路径值恢复到先前状态 Stat stat = baseClient.checkExists()
	 * .usingWatcher(watcher) .forPath(path); if (stat == null) {
	 * System.err.println("to create"); baseClient.create()
	 * .creatingParentsIfNeeded() .withMode( CreateMode.EPHEMERAL) .withACL(
	 * ZooDefs.Ids.OPEN_ACL_UNSAFE) .forPath(path); } } } catch (Exception e) {
	 * e.printStackTrace();
	 * 
	 * } } } }); } } }
	 */
}
