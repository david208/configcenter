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
function submitForm() {
	$("#searchForm").form('submit', {
		url : "${ctx}/addPropertys?system=${system}&version=${version}&env=${env}",
		dataType : 'json',
		success : function(data) {
			eval(" var data = "+data);
			if (data.code >= 0) {
				$.messager.show({
					title : '添加配置',
					msg : '添加配置成功',
					timeout : 5000,
					showType : 'slide'
				});
				clearForm();
			} else {
				$.messager.show({
					title : '添加失败',
					msg : data.attachment,
					timeout : 5000,
					showType : 'slide'
				});
				alert("名称重复");
			}
			window.location.href = '${ctx}/getProperties?system=${system}&version=${version}&env=${env}';
		}
	});		
}
function clearForm() {
	$('#searchForm').form('clear');
};
function back(){
	window.location.href = '${ctx}/getProperties?system=${system}&version=${version}&env=${env}';
}
</script>
</head>
<body>
	<hr>
	<form id="searchForm" method="post">
		<table>
			<tr>			   
				<td>键:</td>
				<td><input class="easyui-validatebox" type="text" name="name" data-options="required:true"></input></td>				
			</tr>
			<tr>
			<td>值:</td>
				<td><input class="easyui-validatebox" type="text" name="value" data-options="required:true"></input></td>
			</tr>
			<tr>			   
				<td>备注:</td>
				<td><input  type="text" name="memo"  data-options="required:true"></input></td>				
			</tr>
			<tr>
			<td>tag:</td>
				<td><input  type="text" name="tag" data-options="required:true"></input></td>
			</tr>
			<tr>
			<tr>
				<td><a href="javascript:void(0)"
					style="vertical-align: middle;" class="easyui-linkbutton"
					onclick="submitForm()">添加</a></td>
				<td><a href="javascript:void(0)"
					style="vertical-align: middle;" class="easyui-linkbutton"
					onclick="back();">返回</a></td>	
			</tr>
		</table>

	</form>
</body>
</html>