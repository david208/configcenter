package com.yizhenmoney.damocles.configcenter.controller;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yizhenmoney.damocles.configcenter.service.PropertiesService;
import com.yizhenmoney.damocles.configcenter.vo.EnvInfo;
import com.yizhenmoney.damocles.configcenter.vo.MenuVo2;
import com.yizhenmoney.damocles.configcenter.vo.PageVo;
import com.yizhenmoney.damocles.configcenter.vo.PropertyInfo;

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
	
	public String getProperties(String system, String version, String env,Model model) throws Exception {
		model.addAttribute("system", system);
		model.addAttribute("version", version);
		model.addAttribute("env", env);
		propertiesService.getEnv(system, version, env);
		return "test/check";
//		return propertiesService.getEnv(system, version, env);
//		List<PropertyInfo> e = propertiesService.getPropertyInfos(system, version, env);
//		return propertiesService.getPropertyInfos(system, version, env);
	}
	
	//查看
	@RequestMapping("/getPropertiesList")
	@ResponseBody
	public List<PropertyInfo> getPropertiesList(String system, String version, String env) throws Exception {
		List<PropertyInfo> propertyInfo=propertiesService.getPropertyInfos(system, version, env);

		 return propertyInfo;
	}
	
	//导出properties
	@RequestMapping("/getPropertie")
	@ResponseBody
	public void getPropertie(String system, String version, String env) throws Exception {
		EnvInfo e = propertiesService.getProperties(system, version, env);
		Map<String, PropertyInfo> map = e.getProperties();
		Properties prop = new Properties();
		for (Map.Entry<String, PropertyInfo> entry : map.entrySet()) {
			prop.put(entry.getKey(), entry.getValue());
		}		  
		//保存
	    FileOutputStream out = new FileOutputStream("src//program.properties");
		//为properties添加注释
		prop.store(out, "the properties's comment");
		out.close();		
	}
	
    //增加版本
	@RequestMapping("/addVersion")
	@ResponseBody
	public String addVersion(String system, String version) throws Exception {
		return propertiesService.addVersion(system, version);

	}
	//增加环境
	@RequestMapping("/addEnv")
	@ResponseBody
	public String addEnv(String system, String version,String env, String memo) throws Exception {
		return propertiesService.addEnv(system, version,env,memo);

	}
	//增加属性
	@RequestMapping("/addProperties")
	@ResponseBody
	public String addProperties(String system, String version,String env,Map<String, PropertyInfo> properties) throws Exception {
		return propertiesService.addProperties(system, version,env,properties);

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
