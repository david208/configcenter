<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>Hestia Fortune System</title>

<link href="${ctx}/static/jquery-validation/1.11.1/validate.css"
	type="text/css" rel="stylesheet" />
<link href="${ctx}/static/css/index.css" type="text/css"
	rel="stylesheet" />
<link href="${ctx}/static/jquery-easyui-1.3.5/themes/default/easyui.css"
	type="text/css" rel="stylesheet" />
<link href="${ctx}/static/jquery-easyui-1.3.5/themes/icon.css"
	type="text/css" rel="stylesheet" />

<script src="${ctx}/static/jquery/jquery.min.js" type="text/javascript"></script>
<script
	src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"
	type="text/javascript"></script>
<script src="${ctx}/static/jquery-easyui-1.3.5/jquery.easyui.min.js"
	type="text/javascript"></script>
	<script src="${ctx}/static\jquery-easyui-1.3.5\extension\common.js"
	type="text/javascript"></script>
<script src="${ctx}/static/js/index.js" type="text/javascript"></script>
<script>
	$(function() {
		InitLeftMenu();
	})
	

	//初始化左侧
	function InitLeftMenu() {

		$('#menu').tree({
			url : '${ctx}/menu2',
				onClick: function(node){
					if(null != node.attributes.url)
						{
						createTabsIframe('tabs',node.text,"${ctx}"+ node.attributes.url);
						} // 在用户点击的时候提示
				}

		});

		/*   $(".easyui-accordion1").empty();
		  var menulist = "";
		 
		  $.each(_menus, function(i, menu) {
		      menulist += '<div title="'+menu.name+'"  style="overflow:auto;">';
			menulist += '<ul>';
		
			
			$.each(menu.children, function(j, m2) {
				 menulist += '<li><div title="'+m2.name+'">'+menu.name;
					menulist += '<ul>';
					  menulist += '</ul></div></li>';
					
					
		      $.each(m2.children, function(k, m3) {
				menulist += '<li><div><a target="mainFrame" href="${ctx}' + m3.url + '" >' + m3.name + '</a></div></li> ';
		      }); 
		      
			 });
		      menulist += '</ul></div>';
		  });

		$(".easyui-accordion1").append(menulist);
		
		$('.easyui-accordion1 li a').click(function(){
			var tabTitle = $(this).text();
			var url = $(this).attr("href");
			addTab(tabTitle,url);
			$('.easyui-accordion1 li div').removeClass("selected");
			$(this).parent().addClass("selected");
		}).hover(function(){
			$(this).parent().addClass("hover");
		},function(){
			$(this).parent().removeClass("hover");
		});

		$(".easyui-accordion1").accordion(); */
	}
</script>
</head>

<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<div region="north" split="true" border="false"
		style="overflow: hidden; height: 65px; background: url(./static/images/logo1.jpg) #7f99be; background-repeat: no-repeat; line-height: 20px; color: #fff; font-family: Verdana, 微软雅黑, 黑体">
		<span style="float: right; padding-right: 20px;" class="head">欢迎
			${userName} <a href="/sms/logout">安全退出</a>
		</span>
		<div>
			<div style="width: 80px; height: 80px; float: left;"
				onclick="window.location.reload();"></div>
			<div style="padding-left: 90px; padding-top: 25px">
				<!-- Damocles Sysetem V1.0 -->
				<b><font style="font-size: 20px">意真金融短信平台 </font></b> V1.0.0
			</div>
		</div>
	</div>
	<div region="south" split="true"
		style="height: 30px; background: #D2E0F2;">
		<div class="footer">Damocles Sysetem 版权@意真（上海）金融信息服务有限公司</div>
	</div>

	<div region="west" split="true" title="导航菜单" style="width: 180px;"
		id="west">
		<div fit="true" border="false">
			<ul id="menu" class="easyui-tree""></ul>
		</div>
	</div>

	<div id="mainPanle" region="center"
		style="background: #eee; overflow-y: hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div title="欢迎使用" style="padding: 20px; overflow: hidden;" id="home">
				<h1>Welcome to the damocles system (v0.2.0-beta)!</h1>
			</div>
		</div>
	</div>

	<div id="mm" class="easyui-menu" style="width: 150px;">
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>

	<!-- 修改密码 -->
	<div id="Password_dlg" class="easyui-dialog" iconCls="icon-edit"
		style="width: 400px; height: 250px; padding: 10px 20px" closed="true"
		buttons="#Password_dlg-buttons">
		<form id="Password_fm" method="post">
			<table class="m_table" style="width: 100%;">
				<tr>
					<td><label>账号</label></td>
					<td><input type="hidden" name="staffid" value="${staff.id}">${staff.name}
					</td>
				</tr>
				<tr>
					<td><label>登录名</label></td>
					<td><input type="hidden" name="loginInfoid"
						value="${loginInfo.id}">${loginInfo.username}</td>
				</tr>
				<tr>
					<td><label class="bitian">原密码</label></td>
					<td><input class="easyui-validatebox" type="password"
						name="oldPassword" id="oldPassword" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td><label class="bitian">新密码</label></td>
					<td><input class="easyui-validatebox" type="password"
						name="newPassword" id="newPassword" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<td><label class="bitian">重复新密码</label></td>
					<td><input class="easyui-validatebox" type="password"
						name="newPassword2" id="newPassword2" data-options="required:true"
						validType="passwordEqual" /></td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 修改密码保存取消 -->
	<div id="Password_dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="savePassword()">保存</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="javascript:$('#Password_dlg').dialog('close')">取消</a>
	</div>
</body>
</html>
