package com.yizhenmoney.damocles.configcenter.service.sever;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.api.transaction.CuratorTransactionFinal;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import com.yizhenmoney.damocles.configcenter.config.Constants;
import com.yizhenmoney.damocles.configcenter.utils.DESCoder;
import com.yizhenmoney.damocles.configcenter.utils.Object2ByteArrayUtils;
import com.yizhenmoney.damocles.configcenter.vo.EnvInfo;
import com.yizhenmoney.damocles.configcenter.vo.Token;

public class PropertiesServerService implements PropertiesServerInter {

	private final ACL adminAcl;

	private final ACL readAcl = new ACL(Perms.READ,
			new Id(Constants.DIGEST, DigestAuthenticationProvider.generateDigest(Constants.READ_AUTH)));;

	private CuratorFramework client;

	public PropertiesServerService(String adminAuth) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		adminAcl = new ACL(Perms.ALL, new Id(Constants.DIGEST, DigestAuthenticationProvider.generateDigest(adminAuth)));
		client = CuratorFrameworkFactory.builder().connectString("192.168.220.194:2181").sessionTimeoutMs(5000)
				.connectionTimeoutMs(5000).aclProvider(new ACLProvider() {

					@Override
					public List<ACL> getDefaultAcl() {

						return Arrays.asList(adminAcl);
					}

					@Override
					public List<ACL> getAclForPath(String path) {
						return Arrays.asList(adminAcl);
					}
				}).authorization(Constants.DIGEST, adminAuth.getBytes("utf-8"))
				.retryPolicy(new ExponentialBackoffRetry(3, 10000)).namespace(Constants.CONFIG_CENTER_NAMESPACE)
				.build();
		client.start();
	}

	@Override
	public void addSystem(String system) throws Exception {
		client.create().forPath(Constants.PROPERTY_PATH + system);

	}

	@Override
	public void addVersion(String system, String version) throws Exception {
		if (null == client.checkExists().forPath(Constants.PROPERTY_PATH + system)) {
			addSystem(system);
		}
		client.create().forPath(Constants.PROPERTY_PATH + system + "/" + version);
	}

	@Override
	public void addEnv(String system, String version, String env, String memo, String tag) throws Exception {

		if (null == client.checkExists().forPath(Constants.PROPERTY_PATH + system + "/" + version)) {
			addVersion(system, version);
		}

		Token token = new Token();
		String passowrd = RandomStringUtils.random(8, true, true);
		String shortToken = DESCoder.md5(RandomStringUtils.random(8, true, true));
		String path = Constants.PROPERTY_PATH + system + "/" + version + "/" + env;
		String digest = system + ":" + passowrd;
		System.out.println("digest:" + digest);
		System.out.println("shortToken:" + shortToken);
		token.setAuth(digest);
		token.setPath(path);

		EnvInfo envInfo = new EnvInfo();
		envInfo.setTag(tag);
		envInfo.setMemo(memo);
		envInfo.setToken(shortToken);

		ACL acl = new ACL(Perms.READ, new Id(Constants.DIGEST, DigestAuthenticationProvider.generateDigest(digest)));

		client.inTransaction().create().withACL(Arrays.asList(acl, adminAcl))
				.forPath(path, Object2ByteArrayUtils.ObjectToByte(envInfo)).and().create()
				.withACL(Arrays.asList(readAcl, adminAcl))
				.forPath(Constants.ORIGIN_PATH + shortToken,
						DESCoder.encrypt(Object2ByteArrayUtils.ObjectToByte(token), Constants.SECRET_KEY))
				.and().commit();

	}

	@Override
	public void addProperties(String system, String version, String env, Map<String, String> properties)
			throws Exception {
		String path = Constants.PROPERTY_PATH + system + "/" + version + "/" + env;
		List<ACL> aclList = client.getACL().forPath(path);
		List<String> children = client.getChildren().forPath(path);
		CuratorTransaction transaction = client.inTransaction();
		CuratorTransactionFinal curatorTransactionFinal = transaction.check().forPath(path).and();
		for (String key : properties.keySet()) {
			if (children.contains(key)) {
				curatorTransactionFinal = curatorTransactionFinal.setData()
						.forPath(path + "/" + key, properties.get(key).getBytes("utf-8")).and();
			} else {
				curatorTransactionFinal = curatorTransactionFinal.create().withACL(aclList)
						.forPath(path + "/" + key, properties.get(key).getBytes("utf-8")).and();
			}
		}
		curatorTransactionFinal.commit();

	}

}
