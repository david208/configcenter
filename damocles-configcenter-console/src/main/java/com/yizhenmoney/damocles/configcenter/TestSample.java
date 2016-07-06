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

//		Properties properties = PropertiesLoaderUtils.loadAllProperties("application2.properties");
//		for (Object key : properties.keySet()) {
//			PropertyInfo info = new PropertyInfo();
//			info.setValue((String) properties.get(key));
//			map.put((String) key, info);
//		}
		//propertiesServerInter.deleteEnv("crm", "1.0", "dev");
	//	propertiesServerInter.deleteSystem("c");

		PropertyInfo info = propertiesServerInter.getProperties("crm", "2.0.0.20160705", "dev1").getProperties().get("jdbc.driver");
		info.setTag("jdbc");
	propertiesServerInter.editProperty("crm", "2.0.0.20160705", "dev1", "jdbc.driver", info);
		//propertiesServerInter.copyEnv("crm", "2.2", "dev1","newDev");;
		//propertiesServerInter.deleteProperty("crm", "2.2", "newDev", "add.pp");
		//System.out.println(propertiesServerInter.getProperties("crm", "2.2", "newDev"));

	}
}
