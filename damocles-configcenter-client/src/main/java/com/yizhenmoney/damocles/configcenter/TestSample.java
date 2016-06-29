package com.yizhenmoney.damocles.configcenter;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.yizhenmoney.damocles.configcenter.service.client.ZooKeeperPropertiesService;
import com.yizhenmoney.damocles.configcenter.service.server.PropertiesServerInter;
import com.yizhenmoney.damocles.configcenter.service.server.PropertiesServerService;
import com.yizhenmoney.damocles.configcenter.vo.PropertyInfo;
import com.yizhenmoney.damocles.configcenter.vo.Token;

public class TestSample {

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, Exception {
		PropertiesServerInter propertiesServerInter =  new PropertiesServerService("admin:123456","192.168.220.194:2181");
		/*Map<String,PropertyInfo> map = new TreeMap<>();
		PropertyInfo info = new PropertyInfo();
		info.setValue("121");
		info.setMemo("好热");
		map.put("spring.info", info);
		propertiesServerInter.addProperties("uc", "1.0", "dev", map,null);*/
		//propertiesServerInter.copyEnv("uc", "1.0", "dev","newDev");;
		
		propertiesServerInter.copyVersion("uc", "1.0","1.5");
	//    propertiesServerInter.addEnv("c2", "1.4", "dt19",null,null);
//				Map<String, String> properties = new TreeMap<>();
//		properties.put("spring.version", "1.2.2.3");
//		properties.put("jdbc.url", "sdjfljl");
//				propertiesServerInter.addProperties("c", "1.0", "dt1", properties, null);
//		ZooKeeperPropertiesService keeperPropertiesService = new ZooKeeperPropertiesService();
//		String acl = System.getProperty("acl");
//		if (StringUtils.isEmpty(acl))
//			throw new RuntimeException("acl不存在");
//		Properties props = new Properties();
//		Token token = keeperPropertiesService.getToken(acl);
//		keeperPropertiesService.getProperties(token, props);
//		System.out.println(props);
	}
}
