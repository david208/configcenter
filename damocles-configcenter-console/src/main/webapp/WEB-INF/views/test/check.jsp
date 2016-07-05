<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<style type="text/css">
input {
    width:100px;
   
}
</style>
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

<script>

function formatOpt(opt,row){
	return "<a href='javascript:void(0);' onclick='updateConfig("+row.id+")'>修改</a>";		
}

function formatOpt1(opt,row){
	return "<a href='javascript:void(0);' onclick='checkConfig("+row.name+")'>删除</a>";	
}
function updateConfig(id) {
	window.location.href = "${ctx}/admin/config/active/edit?id="+id;
}

function checkConfig(name) {
	window.location.href = "${ctx}/delete?system=${system}&version=${version}&env=${env}&name="+name;
}

function add(){
	window.location.href = '${ctx}/admin/config/active/add?search_EQ_configCode='+"1002";
}
function formatUrl(product){
	debugger;
	   var system=$("#system").val();
	   var version=$("#version").val();
	   var env=$("#env").val();
	   value=product+"?system="+system+"&version="+version+"&env="+env;
		return value;
}
</script>
</head>
<body>
<table class="easyui-datagrid" id="tenderDatagridMain" 
	data-options="
		url:'${ctx}/getPropertiesList?system=${system}&version=${version}&env=${env}',
		rownumbers:false,
		pagination:false,
		fit:true,
		pageSize:'20',
		singleSelect:false,
		checkOnSelect:true,
		selectOnCheck:true,
		toolbar:'#tb',
		sortName:'name',
        sortOrder:'asc',
        remoteSort:false ,
        rowStyler: function(index,row){
			return 'width:400; overflow:hidden; white-space:nowrap; text-overflow:ellipsis';    
	}
        
       
		
	">
	<thead>
		<tr>
			<th data-options="field:'name',width:400,align:'center'">名称</th>
			<th data-options="field:'value',width:400,align:'center'">内容</th>
			<th data-options="field:'memo',width:100,align:'center'">备注</th>
			<th data-options="field:'tag',width:100,align:'center'">标签</th>
		    <th data-options="field:'opt',formatter:formatOpt,width:90,align:'center'">操作</th> 
		    <th data-options="field:'opt1',formatter:formatOpt1,width:90,align:'center'">查看</th> 
		</tr>
	</thead>

</table>

<div id ='tb'>
</div>

</html>