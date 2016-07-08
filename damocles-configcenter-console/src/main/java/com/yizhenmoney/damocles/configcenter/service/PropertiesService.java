package com.yizhenmoney.damocles.configcenter.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yizhenmoney.damocles.configcenter.vo.EnvInfo;
import com.yizhenmoney.damocles.configcenter.vo.MenuVo2;
import com.yizhenmoney.damocles.configcenter.vo.PropertyInfo;

@Service
public class PropertiesService {

	@Autowired
	private PropertiesServerInter propertiesServerService;

	public EnvInfo getProperties(String system, String version, String env) throws Exception {
		return propertiesServerService.getProperties(system, version, env);

	}

	public EnvInfo getEnv(String system, String version, String env) throws Exception {
		return propertiesServerService.getEnv(system, version, env);

	}

	public List<PropertyInfo> getPropertyInfos(String system, String version, String env) throws Exception {
		return propertiesServerService.getPropertyInfos(system, version, env);

	}

	public String deleteSystem(String system) throws Exception {
		propertiesServerService.deleteSystem(system);
		return "ok";

	}
	
	public String deleteVersion(String system, String version) throws Exception {
		propertiesServerService.deleteVersion(system, version);
		return "ok";

	}

	public String deleteEnv(String system, String version, String env) throws Exception {
		propertiesServerService.deleteEnv(system, version, env);
		return "ok";

	}
	
	public String copyVersion(String system, String version,String newVersion) throws Exception {
		propertiesServerService.copyVersion(system, version,newVersion);
		return "ok";

	}
	
	public String copyEnv(String system, String version,String env,String newEnv) throws Exception {
		propertiesServerService.copyEnv(system, version,env,newEnv);
		return "ok";

	}
	
	public String addVersion(String system, String version) throws Exception {
		propertiesServerService.addVersion(system, version, null);
		return "ok";

	}

	public String addEnv(String system, String version, String env, String memo) throws Exception {
		propertiesServerService.addEnv(system, version, env, memo, null);
		return "ok";

	}

	public String addProperties(String system, String version, String env, Map<String, PropertyInfo> properties)
			throws Exception {
		propertiesServerService.addProperties(system, version, env, properties, null);
		return "ok";

	}
	public String deleteProperty(String system, String version, String env,String key)
			throws Exception {
		propertiesServerService.deleteProperty(system, version, env, key);
		return "ok";

	}
	
	public String editPropertys(String system, String version, String env,String key,PropertyInfo property)
			throws Exception {
		propertiesServerService.editProperty(system, version, env, key,property);
		return "ok";

	}
	
	public String addPropertys(String system, String version, String env, Map<String, PropertyInfo> properties)
			throws Exception {
		propertiesServerService.addProperties(system, version, env, properties,null);
		return "ok";

	}
	
	public List<MenuVo2> getMenu() throws Exception {
		List<String> systemList = propertiesServerService.getSystemList();
		List<MenuVo2> menuVos = new LinkedList<>();
		for (String system : systemList) {
			MenuVo2 menuVo;
			List<String> versionList = propertiesServerService.getVersionList(system);
			if (CollectionUtils.isEmpty(versionList)) {
				menuVo = MenuVo2.createLeafNode(system, null, "1", "");
			} else {
				menuVo = MenuVo2.createTreeNode(system, "1", "");

			}

			List<MenuVo2> menuVos2 = new LinkedList<>();
			for (String version : versionList) {

				List<String> envList = propertiesServerService.getEnvList(system, version);
				MenuVo2 menuVo2;
				if (CollectionUtils.isEmpty(envList)) {
					menuVo2 = MenuVo2.createLeafNode(version, null, "2", system);
				} else {
					menuVo2 = MenuVo2.createTreeNode(version, "2", system);

				}
				List<MenuVo2> menuVos3 = new LinkedList<>();
				for (String env : envList) {

					MenuVo2 menuVo3 = MenuVo2.createLeafNode(env,
							"/getProperties?system=" + system + "&version=" + version + "&env=" + env, "3",
							system + "." + version);
					menuVos3.add(menuVo3);
				}
				menuVo2.setChildren(menuVos3);
				menuVos2.add(menuVo2);
			}
			menuVo.setChildren(menuVos2);
			menuVos.add(menuVo);
		}
		return menuVos;
	}
}
