package com.yizhenmoney.damocles.configcenter.controller;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public void exportProperties(String system, String version, String env,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Properties prop = new Properties(); //properties对象 
		String path=request.getServletContext().getRealPath("/upload/"+system+"/"+version+"/"+env);
//		String path="E:/property/"+system+"/"+version+"/"+env; //绝对路径
		File file = new File(path);
		   if(!file.exists()){
		    file.mkdirs();
		   }
		List<PropertyInfo> propertyInfo = propertiesService.getPropertyInfos(system, version, env);   
		for(int i=0;i<propertyInfo.size();i++){
			prop.put(propertyInfo.get(i).getName(), propertyInfo.get(i).getValue());
		}
		// 保存
//		FileOutputStream outputStream = new FileOutputStream(path+"/123.properties"); 
        OutputStream outputStream = new FileOutputStream(path+"/123.properties");
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
		// 为properties添加注释
		prop.store(out, "the properties's comment ");
		out.close();
		
		//String downLoadPath = excelName;
		// 创建要下载的文件的对象(参数为要下载的文件在服务器上的路径)
		File serverFile = new File(path+"/123.properties");

		// 设置要显示在保存窗口的文件名，如果文件名中有中文的话，则要设置字符集，否则会出现乱码。另外，要写上文件后缀名
		String fileName = java.net.URLEncoder.encode(system+"-"+version+"-"+env+".properties", "utf-8");
		// 该步是最关键的一步，使用setHeader()方法弹出"是否要保存"的对话框，打引号的部分都是固定的值，不要改变
		response.setHeader("Content-disposition", "attachment;filename=" + fileName);

		/*
		 * 以下四行代码经测试似乎可有可无，可能是我测试的文件太小或者其他什么原因。。。
		 */
		response.setContentType("application/msword");
		// 定义下载文件的长度 /字节
		long fileLength = serverFile.length();
		// 把长整形的文件长度转换为字符串
		String length = String.valueOf(fileLength);
		// 设置文件长度(如果是Post请求，则这步不可少)
		response.setHeader("content_Length", length);

		response.setCharacterEncoding("UTF-8");
		/*
		 * 以上内容仅是下载一个空文件 以下内容用于将服务器中相应的文件内容以流的形式写入到该空文件中
		 */
		// 获得一个 ServletOutputStream(向客户端发送二进制数据的输出流)对象
		OutputStream servletOutPutStream = response.getOutputStream();
		// 获得一个从服务器上的文件myFile中获得输入字节的输入流对象
		FileInputStream fileInputStream = new FileInputStream(serverFile);
	
		byte bytes[] = new byte[1024];// 设置缓冲区为1024个字节，即1KB
		int len = 0;
	//	prop.store(servletOutPutStream, "");
		// 读取数据。返回值为读入缓冲区的字节总数,如果到达文件末尾，则返回-1
		while ((len = fileInputStream.read(bytes)) != -1) {
			// 将指定 byte数组中从下标 0 开始的 len个字节写入此文件输出流,(即读了多少就写入多少)
			servletOutPutStream.write(bytes, 0, len);
		}

		servletOutPutStream.close();
		fileInputStream.close();
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
	
	// 增加系统
	@RequestMapping("/addSystem")
	@ResponseBody
	public String addSystem(String system) throws Exception {
		return propertiesService.addSystem(system);

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
		String replaceName=name.replace(" ", "");
		if(replaceName.equals("")||replaceName.equals(null)){
			return new ResultVo(-1,"主键为空");
		}
		property.setName(replaceName);
		Map<String,PropertyInfo> map = new HashMap<String,PropertyInfo>();
		map.put(replaceName, property);
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
