package com.yizhenmoney.damocles.configcenter.config;

import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.CollectionUtils;

import com.yizhenmoney.damocles.configcenter.service.client.PropertyPlaceholderCallback;
import com.yizhenmoney.damocles.configcenter.service.client.ZooKeeperPropertiesService;
import com.yizhenmoney.damocles.configcenter.vo.Token;

public class ZooKeeperPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private Set<PropertyPlaceholderCallback<?>> propertyPlaceholderCallbackSet;
	
	

	public Set<PropertyPlaceholderCallback<?>> getPropertyPlaceholderCallbackSet() {
		return propertyPlaceholderCallbackSet;
	}

	public void setPropertyPlaceholderCallbackSet(Set<PropertyPlaceholderCallback<?>> propertyPlaceholderCallbackSet) {
		this.propertyPlaceholderCallbackSet = propertyPlaceholderCallbackSet;
	}

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		try {
			getDatas(props);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.processProperties(beanFactoryToProcess, props);
		callback(propertyPlaceholderCallbackSet, props);
	}

	ZooKeeperPropertiesService zooKeeperPropertiesService = new ZooKeeperPropertiesService();

	private void getDatas(Properties props) throws Exception {
		String acl = System.getProperty("acl");
		if (StringUtils.isEmpty(acl))
			throw new RuntimeException("acl不存在");

		Token token = zooKeeperPropertiesService.getToken(acl);
		zooKeeperPropertiesService.getProperties(token, props);
	}

	private void callback(Set<PropertyPlaceholderCallback<?>> propertyPlaceholderCallbackSet, Object object) {
		if (CollectionUtils.isEmpty(propertyPlaceholderCallbackSet))
			return;
		for (PropertyPlaceholderCallback propertyPlaceholderCallback : propertyPlaceholderCallbackSet) {
			propertyPlaceholderCallback.callback(object);
		}
	}
}
