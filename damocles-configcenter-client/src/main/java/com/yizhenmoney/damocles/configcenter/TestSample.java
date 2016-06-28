package com.yizhenmoney.damocles.configcenter;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.yizhenmoney.damocles.configcenter.service.client.ZooKeeperPropertiesService;
import com.yizhenmoney.damocles.configcenter.service.sever.PropertiesServerInter;
import com.yizhenmoney.damocles.configcenter.service.sever.PropertiesServerService;
import com.yizhenmoney.damocles.configcenter.vo.Token;

public class TestSample {

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, Exception {
		PropertiesServerInter propertiesServerInter =  new PropertiesServerService("admin:123456");
	    propertiesServerInter.addEnv("c", "1.0", "dt1","","");
				Map<String, String> properties = new TreeMap<>();
		properties.put("spring.version", "1.2.2.3");
		properties.put("jdbc.url", "sdjfljl");
				propertiesServerInter.addProperties("c", "1.0", "dt1", properties);
		ZooKeeperPropertiesService keeperPropertiesService = new ZooKeeperPropertiesService();
		String acl = System.getProperty("acl");
		if (StringUtils.isEmpty(acl))
			throw new RuntimeException("acl不存在");
		Properties props = new Properties();
		Token token = keeperPropertiesService.getToken(acl);
		keeperPropertiesService.getProperties(token, props);
		System.out.println(props);
	}
}
