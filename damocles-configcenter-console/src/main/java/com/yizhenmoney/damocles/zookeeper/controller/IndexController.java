package com.yizhenmoney.damocles.zookeeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yizhenmoney.damocles.zookeeper.helper.LoginUserHelper;
import com.yizhenmoney.damocles.zookeeper.vo.MenuVo;
import com.yizhenmoney.damocles.zookeeper.vo.ResultVo;

@RequestMapping("")
@Controller
public class IndexController {
	@Autowired
	private LoginUserHelper loginUserHelper;
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("userName", loginUserHelper.loginUserName());
		return "/index";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "/login";
	}
	

	@RequestMapping("/menu")
	@ResponseBody
	public ResultVo menu() {
		MenuVo vo2 = new MenuVo(2l, "node", "code2", "#", 2);
		MenuVo vo24 = new MenuVo(24l, "test", "code24", "/test/list", 24);
		vo2.getChildren().addAll(Sets.newHashSet(vo24));
		return ResultVo.OK(Lists.newArrayList(vo2));
	}
}
