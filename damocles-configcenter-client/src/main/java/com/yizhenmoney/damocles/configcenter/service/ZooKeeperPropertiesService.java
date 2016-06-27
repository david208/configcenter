package com.yizhenmoney.damocles.configcenter.service;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.util.Base64Utils;

import com.yizhenmoney.damocles.configcenter.utils.DESCoder;
import com.yizhenmoney.damocles.configcenter.utils.Object2ByteArrayUtils;
import com.yizhenmoney.damocles.configcenter.vo.Token;

public class ZooKeeperPropertiesService implements PropertiesInter {

	private static final String READ_AUTH = "cc:123456";

	private String zkIp;

	private static final String SECRET_KEY = "abcdefegg";

	@Override
	public Token getToken(String token) throws Exception {
		String[] tokenInfos = StringUtils.split(token, "_");
		zkIp = new String(Base64Utils.decodeFromString(tokenInfos[0]));
		CuratorFramework originClient = CuratorFrameworkFactory.builder().connectString(zkIp).sessionTimeoutMs(5000)
				.connectionTimeoutMs(1000).authorization("digest", READ_AUTH.getBytes("utf-8"))
				.retryPolicy(new ExponentialBackoffRetry(3, 10000)).namespace("configCenter").build();
		originClient.start();
		String shortToken = new String(Base64Utils.decodeFromString(tokenInfos[1]));
		Token tokenInfo = Object2ByteArrayUtils
				.ByteToObject(DESCoder.decrypt(originClient.getData().forPath("/origin/" + shortToken), SECRET_KEY));
		tokenInfo.setName(shortToken);
		originClient.close();
		return tokenInfo;

	}

	@Override
	public void getProperties(Token token, Properties props) throws Exception {
		CuratorFramework propertyClient = CuratorFrameworkFactory.builder().connectString(zkIp).sessionTimeoutMs(5000)
				.connectionTimeoutMs(1000).authorization("digest", token.getAuth().getBytes("utf-8"))
				.retryPolicy(new ExponentialBackoffRetry(3, 10000)).namespace("configCenter").build();
		propertyClient.start();
		List<String> keys = propertyClient.getChildren().forPath(token.getPath());
		for (String key : keys) {
			String value = new String(propertyClient.getData().forPath(token.getPath() + "/" + key));
			props.setProperty(key, value);
		}
		propertyClient.close();
	}

}
