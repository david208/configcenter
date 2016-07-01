package com.yizhenmoney.damocles.configcenter;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.yizhenmoney.damocles.configcenter.service.client.ZooKeeperPropertiesService;
import com.yizhenmoney.damocles.configcenter.service.server.PropertiesServerInter;
import com.yizhenmoney.damocles.configcenter.service.server.PropertiesServerService;
import com.yizhenmoney.damocles.configcenter.vo.PropertyInfo;
import com.yizhenmoney.damocles.configcenter.vo.Token;

public class TestSample {

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, Exception {
		PropertiesServerInter propertiesServerInter = new PropertiesServerService("admin:123456",
				"192.168.220.194:2181");
		Map<String, String> proMap = new HashMap<>();
		Map<String, PropertyInfo> map = new TreeMap<>();
		PropertyInfo info = new PropertyInfo();
	info.setValue("dddd");
	map.put("add.pp", info);
//		Properties properties = PropertiesLoaderUtils.loadAllProperties("application.properties");
//		for (Object key : properties.keySet()) {
//			PropertyInfo info = new PropertyInfo();
//			info.setValue((String) properties.get(key));
//			map.put((String) key, info);
//		}
//
//		propertiesServerInter.addProperties("crm", "2.2", "dev1", map, null);
		//propertiesServerInter.copyEnv("crm", "2.2", "dev1","newDev");;
		propertiesServerInter.deleteProperty("crm", "2.2", "newDev", "add.pp");
		System.out.println(propertiesServerInter.getProperties("crm", "2.2", "newDev"));
		// propertiesServerInter.copyVersion("uc", "1.0","1.5");
		// propertiesServerInter.addEnv("c2", "1.4", "dt19",null,null);
		// Map<String, String> properties = new TreeMap<>();
		// properties.put("spring.version", "1.2.2.3");
		// properties.put("jdbc.url", "sdjfljl");
		// propertiesServerInter.addProperties("c", "1.0", "dt1", properties,
		// null);
		// ZooKeeperPropertiesService keeperPropertiesService = new
		// ZooKeeperPropertiesService();
		// String acl = System.getProperty("acl");
		// if (StringUtils.isEmpty(acl))
		// throw new RuntimeException("acl不存在");
		// Properties props = new Properties();
		// Token token = keeperPropertiesService.getToken(acl);
		// keeperPropertiesService.getProperties(token, props);
		// System.out.println(props);
	}
}
