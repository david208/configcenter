package com.yizhenmoney.damocles.configcenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yizhenmoney.damocles.configcenter.service.PropertiesService;
import com.yizhenmoney.damocles.configcenter.vo.EnvInfo;
import com.yizhenmoney.damocles.configcenter.vo.MenuVo2;

@RequestMapping
@Controller
public class IndexController {

	@RequestMapping("/")
	public String index(Model model) {
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
	PropertiesService propertiesService;

	@RequestMapping("/getProperties")
	@ResponseBody
	public EnvInfo getProperties(String system, String version, String env) throws Exception {
		return propertiesService.getProperties(system, version, env);

	}

	@RequestMapping("/addVersion")
	@ResponseBody
	public String addVersion(String system, String version) throws Exception {
		return propertiesService.addVersion(system, version);

	}

	@RequestMapping("/menu2")
	@ResponseBody
	public List<MenuVo2> menu2() {
		try {
			return propertiesService.getMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
