package com.yizhenmoney.damocles.configcenter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import com.google.common.base.Strings;
import com.google.common.primitives.Bytes;
import com.yizhenmoney.damocles.configcenter.service.ZooKeeperPropertiesService;
import com.yizhenmoney.damocles.configcenter.utils.DESCoder;
import com.yizhenmoney.damocles.configcenter.utils.Object2ByteArrayUtils;
import com.yizhenmoney.damocles.configcenter.vo.Token;

public class TestSample {
	
	private  CuratorFramework  client;
	
	public  TestSample() throws IOException, NoSuchAlgorithmException {
		final ACL acl = new ACL(Perms.ALL, new Id("digest", DigestAuthenticationProvider.generateDigest("admin:123456")));
		final ACL aclf = new ACL(Perms.READ, new Id("digest", DigestAuthenticationProvider.generateDigest("cc:123456")));
		client = CuratorFrameworkFactory.builder()
				.connectString("192.168.220.194:2181").sessionTimeoutMs(5000)
				.connectionTimeoutMs(1000)
				.aclProvider(new ACLProvider() {
					
					@Override 
					public List<ACL> getDefaultAcl() {
						
						return Arrays.asList(acl,aclf);
					}
					
					@Override
					public List<ACL> getAclForPath(String path) {
						   return Arrays.asList(acl,aclf);
					}
				})
				.authorization("digest", "admin:123456".getBytes("utf-8"))
				.retryPolicy(new ExponentialBackoffRetry(3, 10000))
				.namespace("configCenter").build();
		client.start();
	}
	
	public void createNewEvn(String name) throws NoSuchAlgorithmException{


	//	ACL acl2 = new ACL(Perms.READ, new Id("digest", DigestAuthenticationProvider.generateDigest(name+":"+RandomStringUtils.random(8))));	
		try {
			//client.setData().forPath("/property/"+name, "a".getBytes());
		
		
			Token token = new Token();
			token.setAuth("cc:123456");
			token.setPath("/property/crm/1.0/test1");
		
			client.create().creatingParentsIfNeeded().forPath("/origin/s2342k",DESCoder.encrypt(Object2ByteArrayUtils.ObjectToByte(token),"abcdefegg"));
			client.create().creatingParentsIfNeeded().forPath("/property/crm/1.0/test1","xx".getBytes());
			
			client.create().forPath("/property/crm/1.0/test1/"+"spring.ok","4444".getBytes());
			client.create().forPath("/property/crm/1.0/test1/"+"spring.error","5555".getBytes());
			//client.inTransaction().create().withACL(Arrays.asList(acl,acl2)).forPath("/property/"+name).and().create().withACL(Arrays.asList(acl,acl2)).forPath("/property/"+name).and().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		TestSample main = new TestSample();
		main.createNewEvn("crm");
		ZooKeeperPropertiesService keeperPropertiesService = new ZooKeeperPropertiesService();
		String acl = System.getProperty("acl");
		if (StringUtils.isEmpty(acl))
			throw new RuntimeException("acl不存在");
        Properties props = new Properties();
		Token token= keeperPropertiesService.getToken(acl);
		keeperPropertiesService.getProperties(token,props);
		
	}

}
