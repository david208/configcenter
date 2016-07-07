package com.yizhenmoney.damocles.configcenter.config;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.yizhenmoney.damocles.configcenter.utils.DESCoder;

/**
 * @description: 加密用户名和密码的外部配置服务,key长度需要大于8
 * @author sm
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {

		String auth = props.getProperty("zoo.admin.auth");
		if (StringUtils.isNoneEmpty(auth)) {
			props.setProperty("zoo.admin.auth", decodeBuffer(auth));
		}

		super.processProperties(beanFactoryToProcess, props);
	}

	private String decodeBuffer(String plainText) {
		try {
			return DESCoder.decryptFromHex(plainText, Constants.SECRET_KEY);
		} catch (Exception e) {
			return null;
		}
	}

	public static String encodeBuffer(String plainText, String key) {
		try {
			return DESCoder.encryptToHex(plainText, key);
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(EncryptPropertyPlaceholderConfigurer.encodeBuffer("admin:123456", Constants.SECRET_KEY));
	}

}
