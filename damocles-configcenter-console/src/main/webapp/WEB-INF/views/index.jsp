<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
	});

	function append(item,node) {
		if(item.name == 'add'){
			if(node.attributes.type==1){
				var system = node.text;
				debugger;
				$.post("${ctx}/addVersion?system="+system+"&version="+"1.324",function(data){
					alert(data);
				});
			}
			else if(node.attributes.type==2){
			 var system =	$('#menu').tree("getParent",node.target).text;
			 var version = node.text;
				debugger;
				$.post("${ctx}/addEnv?system="+system+"&version="+version+"&env="+"1.1",function(data){
					alert(data);
				});
			}
		}
		else if (item.name == 'copy'){
			
		}
	}

	//初始化左侧
	function InitLeftMenu() {

		$('#menu').tree(
				{
					url : '${ctx}/menu2',
					onClick : function(node) {
						if (3 == node.attributes.type) {
							createTabsIframe('tabs', node.attributes.allPath, "${ctx}"
									+ node.attributes.url);
						} // 在用户点击的时候提示
					},
					onContextMenu : function(e, node) {
						e.preventDefault();
						// 查找节点
						$('#menu').tree('select', node.target);

						
						
						$('#mm').menu({    
						    onClick:function(item){    
						    	append(item,node);
						    }
						    
						}); 
						
						var add = $('#add')[0];
						var copy = $('#copy')[0];
						if (1 == node.attributes.type) {
							$('#mm').menu('disableItem', copy);
							$('#mm').menu('enableItem', add);

						} else if (3 == node.attributes.type) {
							$('#mm').menu('disableItem', add);
							$('#mm').menu('enableItem', copy);
						} else {
							$('#mm').menu('enableItem', copy);
							$('#mm').menu('enableItem', add);
						} 

						// 显示快捷菜单
						$('#mm').menu('show', {
							left : e.pageX,
							top : e.pageY
						}); 
					}

				});
	}
</script>
</head>

<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<div region="north" split="true" border="false"
		style="overflow: hidden; height: 65px; background: url(${ctx}/static/images/logo1.jpg) #7f99be; background-repeat: no-repeat; line-height: 20px; color: #fff; font-family: Verdana, 微软雅黑, 黑体">
		<span style="float: right; padding-right: 20px;" class="head">欢迎
			<sec:authentication property="principal.username" /> <a href="${ctx}/logout">安全退出</a>
		</span>
		<div>
			<div style="width: 80px; height: 80px; float: left;"
				onclick="window.location.reload();"></div>
			<div style="padding-left: 90px; padding-top: 25px">
				<!-- Damocles Sysetem V1.0 -->
				<b><font style="font-size: 20px">意真金融配置中心 </font></b> V1.0.0
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

	<div id="mm" 
		style="width: 150px;">
		<div id="add" data-options="iconCls:'icon-save',name:'add'">新增</div>
		<div id="copy" data-options="iconCls:'icon-remove',name:'copy'">复制</div>
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
