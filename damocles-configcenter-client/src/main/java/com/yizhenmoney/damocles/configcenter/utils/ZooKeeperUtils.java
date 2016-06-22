package com.yizhenmoney.damocles.configcenter.utils;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import com.netflix.curator.framework.CuratorFramework;



public class ZooKeeperUtils {
	private static ZooKeeperConfig z;
	
	static {
		try {
			z=ZooKeeperConfig.getZookeeperConfig(PropitesUtils.getValue("zoo.path"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取节点的值
	 * */
	public static String getNodeValue(String path) throws Exception{
		if(!exists(z.getClient(),path)){
			throw new Exception("节点不存在");
		}
		String data=new String(z.getClient().getData().forPath(path),"utf-8");
		return data;
	}
	/**
	 * @param path 路径
	 * 获取某个节点下的所有子文件
	 * */
	public static List<String> getListChildren(String path) throws Exception{
		if(!exists(z.getClient(),path)){
			throw new Exception("节点不存在");
		}
		List<String> paths=z.getClient().getChildren().forPath(path);
		return paths;
	}
	
	/**
	 * 创建或更新一个节点
	 * 
	 * @param path 路径
	 * @param content 内容
	 * **/
	public static void createrOrUpdate(String path,String content)throws Exception{
		z.getClient().newNamespaceAwareEnsurePath(path).ensure(z.getClient().getZookeeperClient());
		z.getClient().setData().forPath(path,content.getBytes());	
	    System.out.println("添加成功！！！");
	}
	/**
	 * 通过角色创建节点
	 * @throws Exception 
	 * */
	public static void createrOrUpdateByAcl(String path,String content,List<ACL> acls) throws Exception{
		CuratorFramework c=z.getClient(); 
		c.setACL().withACL(acls);
		c.newNamespaceAwareEnsurePath(path).ensure(z.getClient().getZookeeperClient());
		c.setData().forPath(path,content.getBytes());	
	}
	/**
	 * 删除zk节点
	 * @param path 删除节点的路径
	 * 
	 * **/
	public static void delete(String path)throws Exception{
		z.getClient().delete().guaranteed().forPath(path);
		System.out.println("删除成功!");
	}
	/**
	 * 是否存在
	 * */
	private static boolean exists(CuratorFramework client, String path)
			throws Exception {
		Stat stat = client.checkExists().forPath(path);
		return !(stat == null);
	}
	
	/*public static void main(String[] args) throws Exception {
		ZooKeeperConfig zc =ZooKeeperConfig.getZookeeperConfig(PropitesUtils.getValue("zoo.path"));
//
	    List<ACL> acls = new ArrayList<ACL>(2);     
	      
	    Id id1 = new Id("digest", DigestAuthenticationProvider.generateDigest("admin:admin123"));  
	    ACL acl1 = new ACL(ZooDefs.Perms.ALL, id1);  
	      
//	    Id id2 = new Id("digest", DigestAuthenticationProvider.generateDigest("test:test123"));  
//	    ACL acl2 = new ACL(ZooDefs.Perms., id2);  
	      
	    acls.add(acl1);  
//	    acls.add(acl2);  
	    ZooKeeper zk = new ZooKeeper("192.168.220.82:2181", 10000,null);
	   // zk.addAuthInfo("digest", "admin:admin123".getBytes());
//	    zk.create("/rmx", new byte[0], acls, CreateMode.PERSISTENT);
//	    zk.create("/rmx/com", new byte[0], acls, CreateMode.PERSISTENT);
//	    zk.create("/rmx/com/yizhen", new byte[0], acls, CreateMode.PERSISTENT);
	   // zk.create("/rmx/com/yizhen/test", new byte[0], acls, CreateMode.PERSISTENT);
	   // System.out.println(new String(zk.getData("/rao/com/yizhen/sms/192.168.220.82/properties/email/host",true,null)));
//	    //z.getClient().newNamespaceAwareEnsurePath(path).ensure(z.getClient().getZookeeperClient());
//		//z.getClient().setData().forPath(path,content.getBytes());	
//	   // zk.create("/test", new byte[0], acls, CreateMode.PERSISTENT);  
	   // zk.create("/rao", new byte[0], acls, CreateMode.PERSISTENT);
//	    zk.create("/rao/com", new byte[0], acls, CreateMode.PERSISTENT); 
//	    zk.create("/rao/com/yizhen", new byte[0], acls, CreateMode.PERSISTENT);  
	   // zk.create("/rao/com/yizhen/sms", new byte[0], acls, CreateMode.PERSISTENT);  
	    //zk.create("/rao/com/yizhen/sms/192.168.220.82", new byte[0], acls, CreateMode.PERSISTENT);  
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties", new byte[0], acls, CreateMode.PERSISTENT);  
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/db", new byte[0], acls, CreateMode.PERSISTENT);  
//	    
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/db/username", "jdbc.username=yizhen123".getBytes("utf-8"), acls, CreateMode.PERSISTENT);  
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/db/password", "jdbc.password=123456".getBytes("utf-8"), acls, CreateMode.PERSISTENT);  
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/db/driver", "jdbc.driver=net.sf.log4jdbc.DriverSpy".getBytes("utf-8"), acls, CreateMode.PERSISTENT);  
//	    zk.setData("/rao/com/yizhen/sms/192.168.220.82/properties/db/url","jdbc.url=jdbc:log4jdbc:oracle:thin:@192.168.220.188:1521:damocles".getBytes("utf-8"), 1);  
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/db/auto", "hibernate.hbm2ddl.auto=none".getBytes("utf-8"), acls, CreateMode.PERSISTENT);  
//
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/dubbo", new byte[0], acls, CreateMode.PERSISTENT); 
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/dubbo/applicationName", "dubbo.application.name=sms".getBytes("utf-8"), acls, CreateMode.PERSISTENT); 
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/dubbo/address", "dubbo.registry.address=zookeeper://192.168.220.196:2181".getBytes("utf-8"), acls, CreateMode.PERSISTENT); 
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/dubbo/load", "dubbo.service.loadbalance=roundrobin".getBytes("utf-8"), acls, CreateMode.PERSISTENT); 
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/sys", new byte[0], acls, CreateMode.PERSISTENT);  
////	    
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/sys/loginUser", "loginUser=raomx-123456,caoz-1234567".getBytes("utf-8"), acls, CreateMode.PERSISTENT);  
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/email", new byte[0], acls, CreateMode.PERSISTENT);  
//	    zk("/rao/com/yizhen/sms/192.168.220.82/properties/email/host", "smtp.email.host=smtppop.263xmail.com".getBytes("utf-8"), acls, CreateMode.PERSISTENT); 
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/email/port", "smtp.email.port=25".getBytes("utf-8"), acls, CreateMode.PERSISTENT);  
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/email/username", "smtp.email.username=raomx@yizhenmoney.com".getBytes("utf-8"), acls, CreateMode.PERSISTENT);  
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/email/password", "smtp.email.password=raomingxiang0301".getBytes("utf-8"), acls, CreateMode.PERSISTENT);  
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/email/alarm", "alarm.email=raomx@yizhenmoney.com".getBytes("utf-8"), acls, CreateMode.PERSISTENT);  
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/email/to", "to.email=raomx@yizhenmoney.com,raomx@yizhenmoney.com".getBytes("utf-8"), acls, CreateMode.PERSISTENT);  
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/email/errorExcel", "E://yizhenworkspace//test//sms//sms-1.0.3//sms-webapp//error.xls".getBytes("utf-8"), acls, CreateMode.PERSISTENT);
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/message", new byte[0], acls, CreateMode.PERSISTENT);
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/message/jzurl", "jz.url=http://esms.etonenet.com/sms/mt".getBytes("utf-8"), acls, CreateMode.PERSISTENT);
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/message/jzaccount", "jz.account=5867".getBytes("utf-8"), acls, CreateMode.PERSISTENT);
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/message/jzpassword", "jz.password=123".getBytes("utf-8"), acls, CreateMode.PERSISTENT);
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/message/jzkey", "jz.key=123456".getBytes("utf-8"), acls, CreateMode.PERSISTENT);
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/message/yturl", "http://192.168.220.138:8084/sms/test/result".getBytes("utf-8"), acls, CreateMode.PERSISTENT);
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/message/ytaccount", "yt.account=5867".getBytes("utf-8"), acls, CreateMode.PERSISTENT);
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/message/ytpassword", "yt.password=".getBytes("utf-8"), acls, CreateMode.PERSISTENT);
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/message/ytkey", "yt.key=123456".getBytes("utf-8"), acls, CreateMode.PERSISTENT);
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/message/ischange", "sms_is_change=1".getBytes("utf-8"), acls, CreateMode.PERSISTENT);
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/message/channel", "sms_channel=jz,yt".getBytes("utf-8"), acls, CreateMode.PERSISTENT);
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/message/sendsize", "sms_send_size=100".getBytes("utf-8"), acls, CreateMode.PERSISTENT);
//	    
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/log4jdbc", new byte[0], acls, CreateMode.PERSISTENT);  
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/jetty", new byte[0], acls, CreateMode.PERSISTENT);  
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/jetty/port", "jetty.port =8088".getBytes("utf-8"), acls, CreateMode.PERSISTENT);  
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/jetty/context", "jetty.context =/sms".getBytes("utf-8"), acls, CreateMode.PERSISTENT);  
//	    zk.create("/rao/com/yizhen/sms/192.168.220.82/properties/jetty/dir", "jetty.dir=src/main/webapp".getBytes("utf-8"), acls, CreateMode.PERSISTENT);  
//	    zk.create("/rao/com/yizhen/base", new byte[0], acls, CreateMode.PERSISTENT); 
//	    zk.create("/rao/com/yizhen/base/properties", new byte[0], acls, CreateMode.PERSISTENT); 
//	    zk.create("/rao/com/yizhen/base/properties/email", new byte[0], acls, CreateMode.PERSISTENT);
//	    zk.create("/rao/com/yizhen/base/properties/email/host", "base.email.host=smtppop.263xmail.com".getBytes("utf-8"), acls, CreateMode.PERSISTENT);
//	    zk.create("/rao/com/yizhen/base/properties/email/port", "base.email.port=25".getBytes("utf-8"), acls, CreateMode.PERSISTENT);
//	    zk.create("/rao/com/yizhen/base/properties/email/username", "base.email.username=raomx@yizhenmoney.com".getBytes("utf-8"), acls, CreateMode.PERSISTENT);
//	    zk.create("/rao/com/yizhen/base/properties/email/password", "base.email.password=raomingxiang0301".getBytes("utf-8"), acls, CreateMode.PERSISTENT);
//	    zk.create("/rao/com/yizhen/base/properties/email/to", "base.email.to=raomx@yizhenmoney.com,raomx@yizhenmoney.com".getBytes("utf-8"), acls, CreateMode.PERSISTENT);
//	    zk.create("/rao/com/yizhen/base/properties/email/from", "base.email.from=raomx@yizhenmoney.com".getBytes("utf-8"), acls, CreateMode.PERSISTENT);
	   // zk.setData("/rao/com/yizhen/sms/192.168.220.82/properties/email/host", "smtp.email.host=smtppop.263xmail.com".getBytes("utf-8"),3);
	}*/
}
