package com.yizhenmoney.damocles.configcenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yizhenmoney.damocles.configcenter.config.PropritesConfig;
import com.yizhenmoney.damocles.configcenter.helper.LoginUserHelper;
import com.yizhenmoney.damocles.configcenter.utils.ZooKeeperUtils;
import com.yizhenmoney.damocles.configcenter.vo.PageVo;
import com.yizhenmoney.damocles.configcenter.vo.ResultVo;
import com.yizhenmoney.damocles.configcenter.vo.ZookeeperTree;

@RequestMapping("/test")
@Controller
public class TestTxtController {
	@Autowired
	private LoginUserHelper loginUserHelper;

	@RequestMapping(value = { "", "/", "/list" })
	public String index(Model model) {
		String userName = loginUserHelper.loginUserName();
		String role=loginUserHelper.getRole(userName);
		model.addAttribute("roleName", role);
		return "/test/list";
	}

	@RequestMapping("/result")
	@ResponseBody
	public String getResult() {
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "mterrcode=000&mtstat=11";
	}

	@Autowired
	private PropritesConfig propritesConfig;
	private ZooKeeper zk ;
	@RequestMapping("/pages")
	@ResponseBody
	public PageVo<ZookeeperTree> newlist(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "rows", defaultValue = "10") int pageSize,
			@RequestParam(value = "sort", defaultValue = "auto") String sort,
			@RequestParam(value = "order", defaultValue = "ASC") String order,
			Model model, ServletRequest request) {
		try {
			
			String userName = loginUserHelper.loginUserName();
			String password = loginUserHelper
							.getPassWordByUserName(userName);
			zk= new ZooKeeper(propritesConfig.getUrl(), 10000,null);
			zk.addAuthInfo("digest", (userName+":"+password).getBytes());
			
			List<ZookeeperTree> zList = new ArrayList<ZookeeperTree>();
			// getChildNode("",c,new ArrayList<ZookeeperTree>(),new
			// ZookeeperTree(),true,"/rao/com/yizhen","",zList);
			List<String> firstNode = zk.getChildren("/",true);

			for (String string : firstNode) {
				Random random = new Random();
				ZookeeperTree zt = new ZookeeperTree();
				zt.setId((long)random.nextInt(10000000));
				zt.setFunctionCode(string);
				zt.setName(string);
				zt.setNodeUrl(string);
				try {
					zt.setFunctionSum(new String(zk.getData("/"+string,true,null)));
					zt.setUrl(propritesConfig.getZooNameSpace() + "/" + string);
					List<String> childRendList = zk.getChildren("/"+string,true);
					test(childRendList, string, zt,zk);
					zList.add(zt);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				// List<ZookeeperTree> zList1 = new ArrayList<ZookeeperTree>();

				// if (!childRendList.isEmpty()) {
				// for (String string2 : childRendList) {
				// ZookeeperTree zt1 = new ZookeeperTree();
				// zt1.setFunctionCode(string + "/" + string2);
				// zt1.setName(string2);
				// zt1.setId(id);
				// zt1.setFunctionSum(new String(c.getData().forPath(
				// string + "/" + string2)));
				// zt1.setUrl(propritesConfig.getZooNameSpace() + "/"
				// + string + "/" + string2);
				// // zt1.setParent(zt);
				// zList1.add(zt1);
				// id++;
				// List<ZookeeperTree> zList2 = new ArrayList<ZookeeperTree>();
				// List<String> nexChildRendList =
				// c.getChildren().forPath(string + "/" + string2);
				// if(!nexChildRendList.isEmpty()){
				// for (String string3 : nexChildRendList) {
				// ZookeeperTree zt2 = new ZookeeperTree();
				// zt2.setFunctionCode(string + "/" + string2+"/"+string3);
				// zt2.setName(string3);
				// zt2.setId(id);
				// zt2.setFunctionSum(new String(c.getData().forPath(
				// string + "/" + string2+"/"+string3)));
				// zt2.setUrl(propritesConfig.getZooNameSpace() + "/"
				// + string + "/" + string2+"/"+string3);
				// // zt1.setParent(zt);
				// zList2.add(zt2);
				// id++;
				// }
				// zt1.setChildren(zList2);
				// }
				// }
				// zt.setChildren(zList1);
				// }
				
			}
			return new PageVo<ZookeeperTree>(new PageImpl<ZookeeperTree>(
					zList.subList((pageNumber - 1) * pageSize,
							Math.min(pageNumber * pageSize, zList.size())),
					new PageRequest(pageNumber, pageSize), zList.size()));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
		// Page<Function> funs =
		// functionService.getPages(buildSpecification(request, null),
		// buildPageRequest(page, rows, sort, order));
		// return new PageVo<Function>(funs);
		// return null;
	}

	public void test(List<String> childRendList, String partPath,
			ZookeeperTree zt, ZooKeeper c) throws Exception {
		List<ZookeeperTree> zList1 = new ArrayList<ZookeeperTree>();
		if (!childRendList.isEmpty()) {
			for (String string2 : childRendList) {
				ZookeeperTree zt1 = new ZookeeperTree();
				zt1.setFunctionCode(partPath + "/" + string2);
				zt1.setName(string2);
				zt1.setNodeUrl(partPath + "/" + string2);
				Random random = new Random();
				
				zt1.setId((long)random.nextInt(10000000));
				zt1.setUrl(propritesConfig.getZooNameSpace() + "/" + partPath
						+ "/" + string2);
				List<String> nexChildRendList= new ArrayList<String>();
				try {
					zt1.setFunctionSum(new String(c.getData("/"+partPath + "/" + string2,true,null)));
					nexChildRendList = c.getChildren("/"+partPath + "/" + string2,true);
					zList1.add(zt1);
					test(nexChildRendList, partPath + "/" + string2, zt1, c);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			zt.setChildren(zList1);
		}
	}

	@RequestMapping("/add")
	public String add(String nodeUrl, Model model) {
		// 添加子节点
		model.addAttribute("parUrl", nodeUrl);
		return "/test/add";
	}

	@RequestMapping("/edit")
	public String edit(String nodeUrl, Model model) {
		model.addAttribute("nodeUrl", nodeUrl);
		// 获取值
		try {
			String value =new String(zk.getData("/"+nodeUrl, true, null),"utf-8");
			model.addAttribute("nodeValue", value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/test/edit";
	}

	@RequestMapping("/update")
	@ResponseBody
	public ResultVo update(String nodeUrl, String nodeValue) {
		try {
			check(nodeUrl, nodeValue);
			Stat stat = zk.exists("/"+nodeUrl,true);
			if(null !=stat){
				zk.setData("/"+nodeUrl, nodeValue.getBytes("utf-8"),stat.getVersion());
			}
			
			return new ResultVo(0, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultVo(-1, e.getMessage());
		}
	}

	@RequestMapping("/save")
	@ResponseBody
	public ResultVo save(String parUrl, String nodeUrl, String nodeValue,String role) {
		try {
			//获取权限
			String[] roles = role.substring(0,role.lastIndexOf(",")).split(",");
			List<ACL> acls = new ArrayList<ACL>();
			for (String username : roles) {
				String roleName = loginUserHelper.getRole(username);
				String password = loginUserHelper.getPassWordByUserName(username);
				String auth = username + ":" + password;
				Id id1 = new Id("digest",
						DigestAuthenticationProvider.generateDigest(auth));
				int code;
				switch (roleName) {
				case "all":
					code = ZooDefs.Perms.ALL;
					break;
				case "read":
					code = ZooDefs.Perms.READ;
					break;
				case "write":
					code = ZooDefs.Perms.WRITE;
					break;
				case "create":
					code = ZooDefs.Perms.CREATE;
					break;
				case "delete":
					code = ZooDefs.Perms.DELETE;
					break;
				default:
					code = ZooDefs.Perms.ALL;
					break;
				}

				ACL acl1 = new ACL(code, id1);
				acls.add(acl1);
			}		
			if (StringUtils.isNotBlank(parUrl)) {
				nodeUrl =parUrl + "/" + nodeUrl;

			}
			check(nodeUrl, nodeValue);
			//ZooKeeper zk = new ZooKeeper(propritesConfig.getUrl(), 10000,null);
			zk.create(propritesConfig.getZooNameSpace()+nodeUrl, nodeValue.getBytes("utf-8"), acls, CreateMode.PERSISTENT);
			return new ResultVo(0, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultVo(-1, e.getMessage());
		}
	}

	@RequestMapping("/delete")
	@ResponseBody
	public ResultVo delete(String nodeUrl) {
		try {
			if (StringUtils.isNotBlank(nodeUrl)) {
				ZooKeeperUtils.delete(nodeUrl);
			}
			return new ResultVo(0, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultVo(-1, e.getMessage());
		}
	}

	public static boolean check(String nodeUrl, String nodeValue)
			throws Exception {
		if (StringUtils.isBlank(nodeUrl)) {
			throw new Exception("节点不能为空！");
		}
		if (StringUtils.isBlank(nodeValue)) {
			throw new Exception("节点值不能为空！");
		}
		// if(!nodeValue.contains("=")){
		// throw new Exception("节点值格式不正确！");
		// }
		return true;
	}

	public static boolean isNextNode(String path, CuratorFramework c)
			throws Exception {
		List<String> firstNode = c.getChildren().forPath(path);
		if (firstNode.isEmpty()) {
			return false;
		}
		return true;
	}
	
	@RequestMapping("/getAllUser")
	@ResponseBody
	public List<Map<String,Object>> getAllUser(){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> userMap = loginUserHelper.getAllUser();
		for (Map.Entry<String, Object> user : userMap.entrySet()) {
			String username = (String) user.getKey();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("roleCode", loginUserHelper.getRole(username));
			resultMap.put("roleName", username);
			result.add(resultMap);
		}	
		return result;
	}
}
