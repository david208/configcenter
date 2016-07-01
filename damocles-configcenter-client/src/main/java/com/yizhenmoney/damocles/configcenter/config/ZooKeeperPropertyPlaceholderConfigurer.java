package com.yizhenmoney.damocles.configcenter.config;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.yizhenmoney.damocles.configcenter.service.client.ZooKeeperPropertiesService;
import com.yizhenmoney.damocles.configcenter.vo.Token;

public class ZooKeeperPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		try {
			getDatas(props);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.processProperties(beanFactoryToProcess, props);
	}

	ZooKeeperPropertiesService zooKeeperPropertiesService = new ZooKeeperPropertiesService();

	private void getDatas(Properties props) throws Exception {
		String acl = System.getProperty("acl");
		if (StringUtils.isEmpty(acl))
			throw new RuntimeException("acl不存在");

		Token token = zooKeeperPropertiesService.getToken(acl);
		zooKeeperPropertiesService.getProperties(token, props);
	}
}
