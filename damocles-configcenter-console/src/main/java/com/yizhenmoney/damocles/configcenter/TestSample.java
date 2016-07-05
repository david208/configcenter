package com.yizhenmoney.damocles.configcenter;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.yizhenmoney.damocles.configcenter.service.PropertiesServerInter;
import com.yizhenmoney.damocles.configcenter.service.PropertiesServerService;
import com.yizhenmoney.damocles.configcenter.vo.PropertyInfo;

public class TestSample {

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, Exception {
		PropertiesServerInter propertiesServerInter = new PropertiesServerService("admin:123456",
				"192.168.220.194:2181");
//		Map<String, String> proMap = new HashMap<>();
//		Map<String, PropertyInfo> map = new TreeMap<>();
//
//		Properties properties = PropertiesLoaderUtils.loadAllProperties("application-tpp.properties");
//		for (Object key : properties.keySet()) {
//			PropertyInfo info = new PropertyInfo();
//			info.setValue((String) properties.get(key));
//			map.put((String) key, info);
//		}
		//propertiesServerInter.deleteEnv("crm", "1.0", "dev");
		propertiesServerInter.deleteSystem("c");

	//propertiesServerInter.addProperties("tpp", "1.0.0", "dev1", map, null);
		//propertiesServerInter.copyEnv("crm", "2.2", "dev1","newDev");;
		//propertiesServerInter.deleteProperty("crm", "2.2", "newDev", "add.pp");
		//System.out.println(propertiesServerInter.getProperties("crm", "2.2", "newDev"));
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
