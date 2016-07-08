package com.yizhenmoney.damocles.configcenter.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
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

import com.google.gson.Gson;
import com.yizhenmoney.damocles.configcenter.config.Constants;
import com.yizhenmoney.damocles.configcenter.utils.DESCoder;
import com.yizhenmoney.damocles.configcenter.vo.EnvInfo;
import com.yizhenmoney.damocles.configcenter.vo.PropertyInfo;
import com.yizhenmoney.damocles.configcenter.vo.Token;

public class PropertiesServerService implements PropertiesServerInter {

	private final ACL adminAcl;

	private String zkUrl;

	private Gson gson = new Gson();

	private final ACL readAcl = new ACL(Perms.READ,
			new Id(Constants.DIGEST, DigestAuthenticationProvider.generateDigest(Constants.READ_AUTH)));;

	private CuratorFramework client;

	public PropertiesServerService(String adminAuth, String zkUrl) throws Exception {
		this.zkUrl = zkUrl;
		adminAcl = new ACL(Perms.ALL, new Id(Constants.DIGEST, DigestAuthenticationProvider.generateDigest(adminAuth)));
		client = CuratorFrameworkFactory.builder().connectString(this.zkUrl).sessionTimeoutMs(5000)
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
				.retryPolicy(new ExponentialBackoffRetry(1000, 3)).namespace(Constants.CONFIG_CENTER_NAMESPACE)
				.build();
		client.start();
		if (client.checkExists().forPath(Constants.PROPERTY_PATH) == null)
			client.create().creatingParentsIfNeeded().forPath(Constants.PROPERTY_PATH);
		if (client.checkExists().forPath(Constants.ORIGIN_PATH) == null)
			client.create().creatingParentsIfNeeded().forPath(Constants.ORIGIN_PATH);
	}

	@Override
	public void addSystem(String system, CuratorTransactionFinal parentTransactionFinal) throws Exception {
		String path = Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system;

		boolean isAlone = (null == parentTransactionFinal);
		if (!isAlone) {
			if (null != client.checkExists().forPath(path)) {
				return;
			}
		}
		CuratorTransactionFinal curatorTransactionFinal = prepare(isAlone, parentTransactionFinal, path);

		curatorTransactionFinal.create().forPath(Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system);

		if (isAlone) {
			curatorTransactionFinal.commit();
		}

	}

	private CuratorTransactionFinal prepare(boolean isAlone, CuratorTransactionFinal parentTransactionFinal,
			String path) throws Exception {
		CuratorTransactionFinal curatorTransactionFinal;
		if (isAlone) {
			CuratorTransaction transaction = client.inTransaction();
			curatorTransactionFinal = transaction.check().forPath(Constants.PROPERTY_PATH).and();
		} else {
			curatorTransactionFinal = parentTransactionFinal;
		}
		return curatorTransactionFinal;
	}

	@Override
	public void addVersion(String system, String version, CuratorTransactionFinal parentTransactionFinal)
			throws Exception {
		String path = Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system + Constants.PATH_SPLIT + version;

		boolean isAlone = (null == parentTransactionFinal);
		if (!isAlone) {
			if (null != client.checkExists().forPath(path)) {
				return;
			}
		}
		CuratorTransactionFinal curatorTransactionFinal = prepare(isAlone, parentTransactionFinal, path);

		addSystem(system, curatorTransactionFinal);

		curatorTransactionFinal.create()
				.forPath(Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system + Constants.PATH_SPLIT + version);

		if (isAlone) {
			curatorTransactionFinal.commit();
		}
	}

	@Override
	public List<ACL> addEnv(String system, String version, String env, String memo,
			CuratorTransactionFinal parentTransactionFinal) throws Exception {

		String path = Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system + Constants.PATH_SPLIT + version
				+ Constants.PATH_SPLIT + env;

		boolean isAlone = (null == parentTransactionFinal);
		if (!isAlone) {
			if (null != client.checkExists().forPath(path)) {
				return client.getACL().forPath(path);
			}
		}
		CuratorTransactionFinal curatorTransactionFinal = prepare(isAlone, parentTransactionFinal, path);

		addVersion(system, version, curatorTransactionFinal);

		Token token = new Token();
		String passowrd = RandomStringUtils.random(8, true, true);
		String shortToken = DESCoder.md5(RandomStringUtils.random(8, true, true));
		String digest = system + Constants.TOKEN_SPLIT + passowrd;
//		System.out.println("digest:" + digest);
//		System.out.println("shortToken:" + shortToken);
		token.setAuth(digest);
		token.setPath(path);

		EnvInfo envInfo = new EnvInfo();
		envInfo.setMemo(memo);
		envInfo.setToken(shortToken);

		ACL acl = new ACL(Perms.READ, new Id(Constants.DIGEST, DigestAuthenticationProvider.generateDigest(digest)));
		List<ACL> aclList = Arrays.asList(acl, adminAcl);
		curatorTransactionFinal.create().withACL(aclList).forPath(path, gson.toJson(envInfo).getBytes("utf-8")).and()
				.create().withACL(Arrays.asList(readAcl, adminAcl))
				.forPath(Constants.ORIGIN_PATH + Constants.PATH_SPLIT + shortToken,
						DESCoder.encrypt(gson.toJson(token).getBytes("utf-8"), Constants.SECRET_KEY));

		if (isAlone) {
			curatorTransactionFinal.commit();
		}
		return aclList;

	}

	@Override
	public void addProperties(String system, String version, String env, Map<String, PropertyInfo> properties,
			CuratorTransactionFinal parentTransactionFinal) throws Exception {
		String path = Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system + Constants.PATH_SPLIT + version
				+ Constants.PATH_SPLIT + env;

		boolean isAlone = (null == parentTransactionFinal);
		CuratorTransactionFinal curatorTransactionFinal = prepare(isAlone, parentTransactionFinal, path);

		List<ACL> aclList = addEnv(system, version, env, null, curatorTransactionFinal);

		List<String> children = new ArrayList<>();
		if (null != client.checkExists().forPath(path)) {
			children = client.getChildren().forPath(path);
		}

		for (String key : properties.keySet()) {
			byte[] propertyInfoByte = gson.toJson(properties.get(key)).getBytes("utf-8");
			if (children.contains(key)) {
				curatorTransactionFinal.setData().forPath(path + Constants.PATH_SPLIT + key, propertyInfoByte);
			} else {
				curatorTransactionFinal.create().withACL(aclList).forPath(path + Constants.PATH_SPLIT + key,
						propertyInfoByte);

				if (isAlone) {
					String versionPath = Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system + Constants.PATH_SPLIT
							+ version;
					if (null != client.checkExists().forPath(versionPath)) {
						List<String> envList = client.getChildren().forPath(versionPath);
						for (String envNode : envList) {
							String evnPath = versionPath + Constants.PATH_SPLIT + envNode;
							String keyPath = evnPath + Constants.PATH_SPLIT + key;
							if (!keyPath.equals(path + Constants.PATH_SPLIT + key))
								curatorTransactionFinal.create().withACL(client.getACL().forPath(evnPath))
										.forPath(keyPath, propertyInfoByte);
						}
					}
				}
			}
		}

		try{
		if (isAlone) {
			curatorTransactionFinal.commit();
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<String> getSystemList() throws Exception {
		return client.getChildren().forPath(Constants.PROPERTY_PATH);
	}

	@Override
	public List<String> getVersionList(String system) throws Exception {
		return client.getChildren().forPath(Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system);
	}

	@Override
	public List<String> getEnvList(String system, String version) throws Exception {
		return client.getChildren()
				.forPath(Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system + Constants.PATH_SPLIT + version);
	}

	@Override
	public EnvInfo getProperties(String system, String version, String env) throws Exception {

		String path = Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system + Constants.PATH_SPLIT + version
				+ Constants.PATH_SPLIT + env;
		List<String> keys = client.getChildren().forPath(path);
		Map<String, PropertyInfo> properties = new HashMap<String, PropertyInfo>();
		for (String key : keys) {
			PropertyInfo propertyInfo = gson.fromJson(
					new String(client.getData().forPath(path + Constants.PATH_SPLIT + key)), PropertyInfo.class);
			properties.put(key, propertyInfo);
		}
		EnvInfo envInfo = this.getEnv(system, version, env);
		envInfo.setProperties(properties);
		return envInfo;
	}

	@Override
	public void deleteSystem(String system) throws Exception {
		client.delete().deletingChildrenIfNeeded().forPath(Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system);
	}

	@Override
	public void deleteVersion(String system, String version) throws Exception {
		client.delete().deletingChildrenIfNeeded().forPath(Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system+ Constants.PATH_SPLIT+version);

	}

	@Override
	public void deleteEnv(String system, String version, String env) throws Exception {
		String path = Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system + Constants.PATH_SPLIT + version
				+ Constants.PATH_SPLIT + env;
		
		client.delete().deletingChildrenIfNeeded().forPath(path);

	}

	@Override
	public void deleteProperty(String system, String version, String env, String key) throws Exception {
		String versionPath = Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system + Constants.PATH_SPLIT + version;
		String path = versionPath + Constants.PATH_SPLIT + env + Constants.PATH_SPLIT + key;
		List<String> envList = client.getChildren().forPath(versionPath);

		CuratorTransaction transaction = client.inTransaction();
		CuratorTransactionFinal curatorTransactionFinal = transaction.check().forPath(path).and();

		for (String envNode : envList) {
			String keyPath = versionPath + Constants.PATH_SPLIT + envNode + Constants.PATH_SPLIT + key;
			curatorTransactionFinal.delete().forPath(keyPath);
		}
		curatorTransactionFinal.commit();
	}

	@Override
	public void editEnv(String system, String version, String env, String memo) throws Exception {
		String path = Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system + Constants.PATH_SPLIT + version
				+ Constants.PATH_SPLIT + env;
		EnvInfo envInfo = gson.fromJson(new String(client.getData().forPath(path)), EnvInfo.class);
		envInfo.setMemo(memo);
		client.setData().forPath(path, gson.toJson(envInfo).getBytes("utf-8"));

	}

	@Override
	public void editProperty(String system, String version, String env, String key, PropertyInfo propertyInfo)
			throws Exception {
		String path = Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system + Constants.PATH_SPLIT + version
				+ Constants.PATH_SPLIT + env + Constants.PATH_SPLIT + key;
		client.setData().forPath(path, gson.toJson(propertyInfo).getBytes("utf-8"));

	}

	@Override
	public void copyVersion(String system, String version, String newVersion) throws Exception {
		String path = Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system + Constants.PATH_SPLIT + version;
		Map<String, EnvInfo> envMap = new HashMap<>();
		List<String> envList = getEnvList(system, version);
		for (String env : envList) {
			envMap.put(env, getProperties(system, version, env));
		}
		String newPath = Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system + Constants.PATH_SPLIT + newVersion;
		CuratorTransaction transaction = client.inTransaction();
		CuratorTransactionFinal curatorTransactionFinal = transaction.check().forPath(path).and();
		client.create().forPath(newPath);
		for (String env : envMap.keySet()) {
			addProperties(system, newVersion, env, envMap.get(env).getProperties(), curatorTransactionFinal);
		}
		curatorTransactionFinal.commit();

	}

	@Override
	public void copyEnv(String system, String version, String env, String newEnv) throws Exception {
		EnvInfo envInfo = getProperties(system, version, env);
		CuratorTransaction transaction = client.inTransaction();
		CuratorTransactionFinal curatorTransactionFinal = transaction.check().forPath(Constants.PROPERTY_PATH).and();
		addProperties(system, version, newEnv, envInfo.getProperties(), curatorTransactionFinal);
		curatorTransactionFinal.commit();
	}

	@Override
	public EnvInfo getEnv(String system, String version, String env) throws Exception {

		String path = Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system + Constants.PATH_SPLIT + version
				+ Constants.PATH_SPLIT + env;

		EnvInfo envInfo = gson.fromJson(new String(client.getData().forPath(path)), EnvInfo.class);
		envInfo.setLongToken(
				Base64.encodeBase64URLSafeString(zkUrl.getBytes("utf-8")) + Constants.TOKEN_SPLIT + envInfo.getToken());
		return envInfo;
	}

	@Override
	public List<PropertyInfo> getPropertyInfos(String system, String version, String env) throws Exception {
		String path = Constants.PROPERTY_PATH + Constants.PATH_SPLIT + system + Constants.PATH_SPLIT + version
				+ Constants.PATH_SPLIT + env;
		List<String> keys = client.getChildren().forPath(path);
		List<PropertyInfo> properties = new LinkedList<PropertyInfo>();
		for (String key : keys) {
			PropertyInfo propertyInfo = gson.fromJson(
					new String(client.getData().forPath(path + Constants.PATH_SPLIT + key)), PropertyInfo.class);
			propertyInfo.setName(key);
			properties.add(propertyInfo);
		}
		return properties;
	}

}
