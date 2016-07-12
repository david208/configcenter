<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>ConfigCenter</title>

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

//弹出一个输入框，输入一段文字，可以提交  
function prom1(system) {  
    var name = prompt("请输入新的版本", ""); //将输入的内容赋给变量 name ，  

    //这里需要注意的是，prompt有两个参数，前面是提示的话，后面是当对话框出来后，在对话框里的默认值  
    if (name)//如果返回的有内容  
    {  
    	$.post("${ctx}/addVersion?system="+system+"&version="+name,function(data){InitLeftMenu();});
    	
    }  

}  
function prom2(system,version) {  
    var name = prompt("请输入新的环境", ""); //将输入的内容赋给变量 name ，  

    //这里需要注意的是，prompt有两个参数，前面是提示的话，后面是当对话框出来后，在对话框里的默认值  
    if (name)//如果返回的有内容  
    {  
    	$.post("${ctx}/addEnv?system="+system+"&version="+version+"&env="+name,function(data){InitLeftMenu();});
    	
    }  

}
//复制粘贴
function copy1(system,version) {  
    var name = prompt("请输入新的版本", ""); //将输入的内容赋给变量 name ，  

    //这里需要注意的是，prompt有两个参数，前面是提示的话，后面是当对话框出来后，在对话框里的默认值  
    if (name)//如果返回的有内容  
    {  
    	$.post("${ctx}/copyVersion?system="+system+"&version="+version+"&newVersion="+name,function(data){InitLeftMenu();});
    	
    }  

}  
function copy2(system,version,env) {  
    var name = prompt("请输入新的版本", ""); //将输入的内容赋给变量 name ，  

    //这里需要注意的是，prompt有两个参数，前面是提示的话，后面是当对话框出来后，在对话框里的默认值  
    if (name)//如果返回的有内容  
    {  
    	$.post("${ctx}/copyEnv?system="+system+"&version="+version+"&env="+env+"&newEnv="+name,function(data){InitLeftMenu();});
    	
    }  

}  
//这是删除
function deletes2(system) {  
    $.messager.defaults = { ok: "是", cancel: "否" };  
    $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {  
        if (data) {               
        	$.post("${ctx}/deleteSystem?system="+system,function(data){InitLeftMenu();});
        }  
        else {              
        }  
    });
}  
function deletes1(system,version) {  
    $.messager.defaults = { ok: "是", cancel: "否" };  
    $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {  
        if (data) {               
        	$.post("${ctx}/deleteVersion?system="+system+"&version="+version,function(data){InitLeftMenu();});
        }  
        else {              
        }  
    });  
}  
function deletes(system,version,env) {  
    $.messager.defaults = { ok: "是", cancel: "否" };  
    $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {  
        if (data) {               
            $.post("${ctx}/deleteEnv?system="+system+"&version="+version+"&env="+env,function(data){InitLeftMenu();});
        }  
        else {              
        }  
    });     	
}  
//导出
function exportProp(system,version,env) {  
	window.location.href="${ctx}/exportProperties?system="+system+"&version="+version+"&env="+env;
}  

//弹出一个输入框，输入一段文字，可以提交  
function addSystem() {  
    var name = prompt("请输入新的版本", ""); //将输入的内容赋给变量 name ，  

    //这里需要注意的是，prompt有两个参数，前面是提示的话，后面是当对话框出来后，在对话框里的默认值  
    if (name)//如果返回的有内容  
    {  
    	$.post("${ctx}/addSystem?system="+name,function(data){InitLeftMenu();});
    	
    }  

}

	$(function() {
		InitLeftMenu();
	});

	function append(item,node) {
		if(item.name == 'add'){
			if(node.attributes.type==1){
				var system = node.text;
				prom1(system);
			}
			else if(node.attributes.type==2){
				debugger;
			 var system =	$('#menu').tree("getParent",node.target).text;
			 var version = node.text;
			 var length =	$('#menu').tree("getChildren",node.target).length;//判断是否有子节点长度
			 if (length<1){
				 prom2(system,version); 
			 }
			}
		}
		else if (item.name == 'deletes'){
			if(node.attributes.type==1){
				var system = node.text;
				deletes2(system);
			}
			else if(node.attributes.type==2){
			 var system =	$('#menu').tree("getParent",node.target).text;
			 var version = node.text;
			 deletes1(system,version);
			}
			else if(node.attributes.type==3){				
				 var version1 =	$('#menu').tree("getParent",node.target);
				 var env = node.text;
				 var version =	$('#menu').tree("getParent",node.target).text;
				 var system =	$('#menu').tree("getParent",version1.target).text;
				 deletes(system,version,env);
				}
		}
		else if(item.name == 'copy'){
			if(node.attributes.type==2){
				 var system =	$('#menu').tree("getParent",node.target).text;
				 var version = node.text;
				 copy1(system,version);
				}
				else if(node.attributes.type==3){				
					 var version1 =	$('#menu').tree("getParent",node.target);
					 var env = node.text;
					 var version =	$('#menu').tree("getParent",node.target).text;
					 var system =	$('#menu').tree("getParent",version1.target).text;
					 copy2(system,version,env);
					}
		}
		else if(item.name == 'exportProp'){	
				if(node.attributes.type==3){				
					 var version1 =	$('#menu').tree("getParent",node.target);
					 var env = node.text;
					 var version =	$('#menu').tree("getParent",node.target).text;
					 var system =	$('#menu').tree("getParent",version1.target).text;
					 exportProp(system,version,env);
					}
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
						var deletes = $('#deletes')[0];
						var exportProp = $('#exportProp')[0];
						if (1 == node.attributes.type) {
							$('#mm').menu('disableItem', copy);
							$('#mm').menu('enableItem', add);

						} else if (3 == node.attributes.type) {
							$('#mm').menu('disableItem', add);
							$('#mm').menu('enableItem', copy);
							$('#mm').menu('enableItem', exportProp);
						} else {
							$('#mm').menu('enableItem', copy);
							$('#mm').menu('enableItem', add);
							$('#mm').menu('enableItem', deletes);
							$('#mm').menu('disableItem', exportProp);
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
		<div class="footer">Damocles System 版权@意真（上海）金融信息服务有限公司</div>
	</div>

	<div region="west" split="true" title="导航菜单" style="width: 180px;"
		id="west" >
		<div fit="true" border="false">
			<a href="javascript:void(0)" style="vertical-align: middle;" class="easyui-linkbutton"
					onclick="addSystem()">新增</a>
			<ul id="menu" class="easyui-tree" ></ul>
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
		<div id="deletes" data-options="iconCls:'icon-remove',name:'deletes'">删除</div>
		<div id="exportProp" data-options="iconCls:'icon-print',name:'exportProp'">导出</div>
		<div id="copy" data-options="iconCls:'icon-cut',name:'copy'">复制</div>
	</div>

</body>
</html>
