<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link type="image/x-icon" href="${ctx}/static/images/favicon.ico"
	rel="shortcut icon">

<link href="${ctx}/static/css/common.css" type="text/css"
	rel="stylesheet" />
<link href="${ctx}/static/jquery-validation/1.11.1/validate.css"
	type="text/css" rel="stylesheet" />
<link href="${ctx}/static/jquery-easyui-1.3.5/themes/default/easyui.css"
	type="text/css" rel="stylesheet" />
<link href="${ctx}/static/jquery-easyui-1.3.5/themes/icon.css" type="text/css"
	rel="stylesheet" />
<link href="${ctx}/static/My97DatePicker/skin/WdatePicker.css"
	rel="stylesheet" type="text/css">
<link href="${ctx}/static/uploadify/uploadify.css" rel="stylesheet"
	type="text/css" />

<script src="${ctx}/static/jquery/jquery-1.9.1.min.js"
	type="text/javascript"></script>
<script
	src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"
	type="text/javascript"></script>
<script src="${ctx}/static/jquery-easyui/jquery.easyui.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/js/common.js" type="text/javascript"></script>
<script src="${ctx}/static/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/uploadify/jquery.uploadify.js"></script>
<script src="${ctx}/static/fusionChart/js/FusionCharts.js"
	type="text/javascript"></script>
</head>
<div style="padding: 10px 30px 10px 30px">
	<form id="nodeSaveForm" method="post">
		<input type="hidden" name="nodeUrl" value="${nodeUrl}">
		<table>
			<tr>
				<td>节点名称:</td>
				<td><textarea rows="4" cols="30" class="easyui-validatebox"
						name="nodeName" id="nodeName" data-options="required:true">${nodeUrl}</textarea>
				</td>
				
			</tr>
			<tr>
				<td>节点值:</td>
				<td><textarea rows="4" cols="30" class="easyui-validatebox"
						name="nodeValue" id="nodeValue" data-options="required:true">${nodeValue}</textarea>
				</td>
			</tr>
		</table>
	</form>
	<div style="text-align: center;">
		<a href="javascript:void(0)" style="vertical-align: middle;"
			class="easyui-linkbutton" onclick="submitForm()">保存</a> <a
			href="javascript:void(0)" style="vertical-align: middle;"
			class="easyui-linkbutton" onclick="clearForm()">重置</a>
	</div>
</div>

<script type="text/javascript">
	function submitForm() {
		var nodeName=$("#nodeName").val();
		var nodeValue=$("#nodeValue").val();
		$.ajax({
			url : '${ctx}/test/update',
			dataType : 'json',
			data:{nodeUrl:nodeName,nodeValue:nodeValue},
			success : function(data) {
				if (data.code == 0) {
					$.messager.show({
						title : '修改',
						msg : '修改成功',
						timeout : 5000,
						showType : 'slide'
					});
					$('#editNode').dialog('close');
					$('#nodeDataGrid').treegrid('reload');
				} else {
					$.messager.alert('Error', data.attachment);
				}
			}
		});
		
		
		/* $("#nodeSaveForm").form('submit', {
			url : "${ctx}/test/update",
			data:{nodeName:nodeName,nodeValue:nodeValue},
			success : function(data) {
				eval(" var data = "+data);
				if (data.code == 0) {
					$.messager.show({
						title : '修改',
						msg : '修改成功',
						timeout : 5000,
						showType : 'slide'
					});

					$("#nodeSaveForm").form('clear');
					window.location.href = '${ctx}/test/list';
				} else {
					$.messager.alert('Error', data.attachment);
					window.location.href = '${ctx}/test/list';
				}
			}
		});  */
	};
	function clearForm() {
		$('#nodeSaveForm').form('clear');
		loadFuntionData();
	};
</script>