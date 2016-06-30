package com.yizhenmoney.damocles.configcenter.controller;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yizhenmoney.damocles.configcenter.helper.LoginUserHelper;
import com.yizhenmoney.damocles.configcenter.service.server.PropertiesServerInter;
import com.yizhenmoney.damocles.configcenter.service.server.PropertiesServerService;
import com.yizhenmoney.damocles.configcenter.vo.EnvInfo;
import com.yizhenmoney.damocles.configcenter.vo.MenuVo;
import com.yizhenmoney.damocles.configcenter.vo.MenuVo2;
import com.yizhenmoney.damocles.configcenter.vo.ResultVo;

@RequestMapping("")
@Controller
public class IndexController {
	// @Autowired
	// private LoginUserHelper loginUserHelper;
	@RequestMapping("/")
	public String index(Model model) {
		// model.addAttribute("userName", loginUserHelper.loginUserName());
		return "/index";
	}

	@RequestMapping("/login")
	public String login() {
		return "/login";
	}

	@RequestMapping("/403")
	public String m403() {
		return "/403";
	}

	@Autowired
	private PropertiesServerInter propertiesServerService;

	@RequestMapping("/menu")
	@ResponseBody
	public ResultVo menu() {
		try {
			List<String> systemList = propertiesServerService.getSystemList();
			List<MenuVo> menuVos = new LinkedList<>();
			int i = 0;
			for (String system : systemList) {

				MenuVo menuVo = new MenuVo(Long.valueOf(++i), system, null, null, i);
				List<String> versionList = propertiesServerService.getVersionList(system);
				int j = 0;
				List<MenuVo> menuVos2 = new LinkedList<>();
				for (String version : versionList) {

					MenuVo menuVo2 = new MenuVo(Long.valueOf(++j), version, null, null, j);

					menuVos2.add(menuVo2);
				}
				menuVo.setChildren(Sets.newHashSet(menuVos2));
				menuVos.add(menuVo);
			}
			return ResultVo.OK(menuVos);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultVo.error("");
	}
	
	@RequestMapping("/getProperties")
	@ResponseBody
	public EnvInfo  getProperties(String system,String version,String env) throws Exception {
		return propertiesServerService.getProperties(system, version, env);
		
	}
	
	@RequestMapping("/menu2")
	@ResponseBody
	public List<MenuVo2> menu2() {
		try {
			List<String> systemList = propertiesServerService.getSystemList();
			List<MenuVo2> menuVos = new LinkedList<>();
			for (String system : systemList) {

				MenuVo2 menuVo = MenuVo2.createTreeNode(system);
				List<String> versionList = propertiesServerService.getVersionList(system);
				List<MenuVo2> menuVos2 = new LinkedList<>();
				for (String version : versionList) {

					MenuVo2 menuVo2 = MenuVo2.createTreeNode(version);
					
					List<String> envList = propertiesServerService.getEnvList(system, version);
					List<MenuVo2> menuVos3 = new LinkedList<>();
					for (String env : envList) {

						MenuVo2 menuVo3 = MenuVo2.createLeafNode(env, "/getProperties?system="+system+"&version="+version+"&env="+env);
						menuVos3.add(menuVo3);
					}
					menuVo2.setChildren(menuVos3);
					menuVos2.add(menuVo2);
				}
				menuVo.setChildren(menuVos2);
				menuVos.add(menuVo);
			}
			return menuVos;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
