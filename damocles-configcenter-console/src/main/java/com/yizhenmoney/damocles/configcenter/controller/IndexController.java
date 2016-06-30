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

	// @RequestMapping("/menu")
	// @ResponseBody
	// public ResultVo menu() {
	// try {
	// List<String> systemList = propertiesServerService.getSystemList();
	// List<MenuVo> menuVos = new LinkedList<>();
	// int i = 0;
	// for (String system : systemList) {
	//
	// MenuVo menuVo = new MenuVo(Long.valueOf(++i), system, null, null, i);
	// List<String> versionList =
	// propertiesServerService.getVersionList(system);
	// int j = 0;
	// List<MenuVo> menuVos2 = new LinkedList<>();
	// for (String version : versionList) {
	//
	// MenuVo menuVo2 = new MenuVo(Long.valueOf(++j), version, null, null, j);
	//
	// menuVos2.add(menuVo2);
	// }
	// menuVo.setChildren(Sets.newHashSet(menuVos2));
	// menuVos.add(menuVo);
	// }
	// return ResultVo.OK(menuVos);
	//
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return ResultVo.error("");
	// }

	@RequestMapping("/getProperties")
	@ResponseBody
	public EnvInfo getProperties(String system, String version, String env) throws Exception {
		return propertiesServerService.getProperties(system, version, env);

	}

	@RequestMapping("/addVersion")
	@ResponseBody
	public String addVersion(String system, String version) throws Exception {
		propertiesServerService.addVersion(system, version, null);
		return "ok";

	}

	@RequestMapping("/menu2")
	@ResponseBody
	public List<MenuVo2> menu2() {
		try {
			List<String> systemList = propertiesServerService.getSystemList();
			List<MenuVo2> menuVos = new LinkedList<>();
			for (String system : systemList) {
				MenuVo2 menuVo;
				List<String> versionList = propertiesServerService.getVersionList(system);
				if (CollectionUtils.isEmpty(versionList)) {
					menuVo = MenuVo2.createLeafNode(system, null, "1");
				} else {
					menuVo = MenuVo2.createTreeNode(system, "1");

				}

				List<MenuVo2> menuVos2 = new LinkedList<>();
				for (String version : versionList) {

					List<String> envList = propertiesServerService.getEnvList(system, version);
					MenuVo2 menuVo2;
					if (CollectionUtils.isEmpty(envList)) {
						menuVo2 = MenuVo2.createLeafNode(version, null, "2");
					} else {
						menuVo2 = MenuVo2.createTreeNode(version, "2");

					}
					List<MenuVo2> menuVos3 = new LinkedList<>();
					for (String env : envList) {

						MenuVo2 menuVo3 = MenuVo2.createLeafNode(env,
								"/getProperties?system=" + system + "&version=" + version + "&env=" + env, "3");
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
