package com.yizhenmoney.damocles.configcenter.service.client;

import java.util.List;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.ExponentialBackoffRetry;
import com.yizhenmoney.damocles.configcenter.config.Constants;
import com.yizhenmoney.damocles.configcenter.utils.DESCoder;
import com.yizhenmoney.damocles.configcenter.vo.PropertyInfo;
import com.yizhenmoney.damocles.configcenter.vo.Token;

public class ZooKeeperPropertiesService implements PropertiesClientInter {

	private String zkIp;

	private Gson gson = new Gson();

	@Override
	public Token getToken(String token) throws Exception {
		String[] tokenInfos = StringUtils.split(token, Constants.TOKEN_SPLIT);
		zkIp = new String(Base64.decodeBase64(tokenInfos[0]));
		CuratorFramework originClient = CuratorFrameworkFactory.builder().connectString(zkIp).sessionTimeoutMs(5000)
				.connectionTimeoutMs(5000).authorization(Constants.DIGEST, Constants.READ_AUTH.getBytes("utf-8"))
				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).namespace(Constants.CONFIG_CENTER_NAMESPACE)
				.build();
		originClient.start();
		String shortToken = tokenInfos[1];
		Token tokenInfo = gson.fromJson(new String(DESCoder.decrypt(
				originClient.getData().forPath(Constants.ORIGIN_PATH + Constants.PATH_SPLIT + shortToken),
				Constants.SECRET_KEY)), Token.class);
		tokenInfo.setName(shortToken);
		originClient.close();
		return tokenInfo;

	}

	@Override
	public void getProperties(Token token, Properties props) throws Exception {
		CuratorFramework propertyClient = CuratorFrameworkFactory.builder().connectString(zkIp).sessionTimeoutMs(5000)
				.connectionTimeoutMs(1000).authorization(Constants.DIGEST, token.getAuth().getBytes("utf-8"))
				.retryPolicy(new ExponentialBackoffRetry(3, 10000)).namespace(Constants.CONFIG_CENTER_NAMESPACE)
				.build();
		propertyClient.start();
		List<String> keys = propertyClient.getChildren().forPath(token.getPath());
		for (String key : keys) {
			PropertyInfo info = gson.fromJson(
					new String(propertyClient.getData().forPath(token.getPath() + Constants.PATH_SPLIT + key)),
					PropertyInfo.class);
			props.setProperty(key, info.getValue().trim());
		}
		propertyClient.close();
	}

}
