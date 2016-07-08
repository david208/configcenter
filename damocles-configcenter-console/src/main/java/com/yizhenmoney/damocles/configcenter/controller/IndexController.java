package com.yizhenmoney.damocles.configcenter.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yizhenmoney.damocles.configcenter.service.PropertiesService;
import com.yizhenmoney.damocles.configcenter.vo.EnvInfo;
import com.yizhenmoney.damocles.configcenter.vo.MenuVo2;
import com.yizhenmoney.damocles.configcenter.vo.PropertyInfo;
import com.yizhenmoney.damocles.configcenter.vo.ResultVo;

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

	public String getProperties(String system, String version, String env, Model model) throws Exception {
		model.addAttribute("system", system);
		model.addAttribute("version", version);
		model.addAttribute("env", env);
		EnvInfo envInfo = propertiesService.getEnv(system, version, env);
		model.addAttribute("longToken", envInfo.getLongToken());
		return "test/check";
	}

	// 查看
	@RequestMapping("/getPropertiesList")
	@ResponseBody
	public List<PropertyInfo> getPropertiesList(String system, String version, String env) throws Exception {
		List<PropertyInfo> propertyInfo = propertiesService.getPropertyInfos(system, version, env);

		return propertyInfo;
	}

	// 导出properties
	@RequestMapping("/exportProperties")
	@ResponseBody
	public String exportProperties(String system, String version, String env) throws Exception {
		Properties prop = new Properties(); //properties对象 
		String Path="E:/property/"+system+"/"+version+"/"+env; //绝对路径
		File file = new File(Path);
		   if(!file.exists()){
		    file.mkdirs();
		   }
		List<PropertyInfo> propertyInfo = propertiesService.getPropertyInfos(system, version, env);   
		for(int i=0;i<propertyInfo.size();i++){
			prop.put(propertyInfo.get(i).getName(), propertyInfo.get(i).getValue());
		}
		// 保存
		FileOutputStream out = new FileOutputStream(Path+"/program.properties"); 
		// 为properties添加注释
		prop.store(out, "the properties's comment");
		out.close();
		return Path;
	}

	
	//导入properties
	@RequestMapping("/importProperties")
	@ResponseBody
	public  ResultVo importProperties(String system, String version, String env,HttpServletRequest request) throws Exception {
		// 判断form是否为上传表单
		if (!ServletFileUpload.isMultipartContent(request)) {
			throw new RuntimeException("不是文件上传表单！");
		}
		// 步骤一 工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 步骤二 获得核心解析类
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		// 解决上传文件 中文名乱码
		fileUpload.setHeaderEncoding("utf-8");
		
		InputStream in=null;
		// 步骤三 解析请求
	
			List<FileItem> list = fileUpload.parseRequest(request);
			// 步骤四 遍历集合
			for (FileItem fileItem : list) {
				// 步骤五 判断是否为上传文件
				if (fileItem.isFormField()) {
				} else {
					// 上传文件
					String filename = fileItem.getName();
					// 判断有没有上传
					if (filename == null || filename.trim().length() == 0) {
						throw new RuntimeException("必须要上传文件！");
					}
					// 切割真实文件名
					int index = filename.lastIndexOf("\\");// 早期浏览器携带客户端路径
					if (index != -1) {
						filename = filename.substring(index + 1);
					}
					// 判断文件合法性 （扩展名）
					String ext = filename.substring(filename.lastIndexOf("."));
					if (!(ext.toLowerCase()).equals(".properties")) {
						throw new RuntimeException("上传文件格式非法！");
					}

					// 上传文件内容
					in = new BufferedInputStream(fileItem
							.getInputStream());


				}
			}

		    Properties props = new Properties();
		    
//		    InputStream in = new FileInputStream("E:/property/tpp/1.0.0/dev1/program.properties");

		    // 或使用文件输入流(不推荐)，假设当前工作目录为bin
		    //InputStream in = new FileInputStream("./config.properties");

		    props.load(in);
		    in.close();
		    
		    Map<String,PropertyInfo> map = new HashMap<String,PropertyInfo>();
		  
		    
		    Enumeration en = props.propertyNames();
		    while (en.hasMoreElements()){		    	
		        String k =  (String) en.nextElement();
		        String v= props.getProperty(k);
		        PropertyInfo propertyInfo= new PropertyInfo();
		        //propertyInfo.setName(k);
		        propertyInfo.setValue(v);
		        map.put(k, propertyInfo);
		    }
		    propertiesService.addPropertys(system, version, env,map);
		
		    return new ResultVo(1, "成功");
	
	}

	// 删除环境
	@RequestMapping("/deleteEnv")
	@ResponseBody
	public String deleteEnv(String system, String version, String env) throws Exception {
		return propertiesService.deleteEnv(system, version, env);

	}
	
	// 删除版本
	@RequestMapping("/deleteVersion")
	@ResponseBody
	public String deleteVersion(String system, String version) throws Exception {
		return propertiesService.deleteVersion(system, version);

	}
	
	// 删除system
	@RequestMapping("/deleteSystem")
	@ResponseBody
	public String deleteSystem(String system) throws Exception {
		return propertiesService.deleteSystem(system);

	}
	
	// 复制粘贴版本
	@RequestMapping("/copyVersion")
	@ResponseBody
	public String copyVersion(String system, String version, String newVersion) throws Exception {
		return propertiesService.copyVersion(system, version, newVersion);

	}
	
	// 复制粘贴环境
	@RequestMapping("/copyEnv")
	@ResponseBody
	public String copyEnv(String system, String version,String env ,String newEnv) throws Exception {
		return propertiesService.copyEnv(system, version,env,newEnv);

	}
	
	// 增加版本
	@RequestMapping("/addVersion")
	@ResponseBody
	public String addVersion(String system, String version) throws Exception {
		return propertiesService.addVersion(system, version);

	}

	// 增加环境
	@RequestMapping("/addEnv")
	@ResponseBody
	public String addEnv(String system, String version, String env, String memo) throws Exception {
		return propertiesService.addEnv(system, version, env, memo);

	}

	// 增加属性
	@RequestMapping("/addProperties")
	@ResponseBody
	public String addProperties(String system, String version, String env, Map<String, PropertyInfo> properties)
			throws Exception {
		return propertiesService.addProperties(system, version, env, properties);

	}

	// 删除属性
	@RequestMapping("/deleteProperty")
	@ResponseBody
	public ResultVo deleteProperty(String system, String version, String env, String name) throws Exception {
		propertiesService.deleteProperty(system, version, env, name);
		return new ResultVo(1, "删除成功");

	}
	
	// 修改属性
	@RequestMapping("/editProperty")
	public String editProperty(String system, String version, String env, String name, Model model) throws Exception {
		model.addAttribute("system", system);
		model.addAttribute("version", version);
		model.addAttribute("env", env);
		List<PropertyInfo> propertyInfo = propertiesService.getPropertyInfos(system, version, env);
		for(int i=0;i<propertyInfo.size();i++){
			if(name.equals(propertyInfo.get(i).getName())){
				model.addAttribute("propertyInfo", propertyInfo.get(i));
			}
		}		
		return "test/editproperty";

	}
	
	// 修改properteis属性
	@RequestMapping("/editPropertys")
	@ResponseBody
	public ResultVo editPropertys(String system, String version, String env, String name, PropertyInfo property) throws Exception {
		propertiesService.editPropertys(system, version, env, name,property);
		return new ResultVo(1, "修改成功");

	}
	
	// 增加属性
	@RequestMapping("/addProperty")
	public String addProperty(String system, String version, String env, Model model) throws Exception {
		model.addAttribute("system", system);
		model.addAttribute("version", version);
		model.addAttribute("env", env);		
		return "test/addproperty";

	}
	
	// 增加properteis属性
	@RequestMapping("/addPropertys")
	@ResponseBody
	public ResultVo addPropertys(String system, String version, String env, String name, PropertyInfo property) throws Exception {
		Map<String,PropertyInfo> map = new HashMap<String,PropertyInfo>();
		map.put(name, property);
		propertiesService.addPropertys(system, version, env,map);
		return new ResultVo(1, "添加成功");

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
