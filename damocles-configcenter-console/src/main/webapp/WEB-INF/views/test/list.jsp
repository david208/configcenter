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

<script src="${ctx}/static/jquery/jquery.min.js"
	type="text/javascript"></script>
<script
	src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js"
	type="text/javascript"></script>
<script src="${ctx}/static/jquery-easyui-1.3.5/jquery.easyui.min.js"
	type="text/javascript"></script>
<script src="${ctx}/static/js/common.js" type="text/javascript"></script>
<script src="${ctx}/static/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/uploadify/jquery.uploadify.js"></script>
<script src="${ctx}/static/fusionChart/js/FusionCharts.js"
	type="text/javascript"></script>
</head>
<script type="text/javascript">
	//添加员工

	function editNode(url) {
		
		showCommonDialog('editNode', 'nodeDataGrid', '${ctx}/test/edit?nodeUrl='+url, '新增节点',
				500, 270);
	};

	function createNode(nodeUrl) {
		showCommonDialog('createNode', 'nodeDataGrid', '${ctx}/test/add?nodeUrl='
				+ nodeUrl, '新增子节点', 500, 270);
	};
	/**
	 * 标准message弹窗框
	 * @param type
	 * @param title
	 * @param msg
	 * @param okFunction
	 * @param icon
	 */
	function showMessage(type,title, msg,okFunction, icon){
		if(type == 'show'){
			$.messager.show({
				title:title,
				msg:msg,
				showType:'show'
			});
		}
		if(type == 'alert'){
			$.messager.alert(title,msg,icon);
		}
		if(type == 'confirm'){
			$.messager.confirm(title,msg, function(r){
				if (r){
					okFunction();
				}
			});
		}
		if(type == 'prompt'){
			$.messager.confirm(title,msg, function(r){
				if (r){
					okFunction();
				}
			});
		}
		
	}
	//批量删除
	function deleteFunction(nodeUrl) {
		showMessage('confirm', '确认', '请确认是否删除？', function() {
			$.ajax({
				url : '${ctx}/test/delete',
				dataType : 'json',
				data:{nodeUrl:nodeUrl},
				success : function(data) {
					if (data.code == 0) {
						$.messager.show({
							title : '删除',
							msg : '删除成功',
							timeout : 5000,
							showType : 'slide'
						});
		
						$('#nodeDataGrid').treegrid('reload');
					} else {
						$.messager.alert('Error', data.attachment);
					}
				}
			});
			
		});
	};
	function formatOpt(val, row) {
		var roleName=$("#roleName").val();
		if(roleName=="all"){
			var str="'"+val+"'";
			var link = '<a href="javascript:void(0);" onclick="createNode('
					+ str + ');">新增子节点</a>&nbsp;&nbsp;|&nbsp;&nbsp; ';
			link += '<a href="javascript:void(0);" onclick="editNode('+str+');">编辑</a>&nbsp;&nbsp;|&nbsp;&nbsp; ';
			link += '<a href="javascript:void(0);" onclick="deleteFunction('
					+str + ');">删除</a>';
			return link;
		}
		
	}

	function formatMemo(val) {
		alert(val);
		var link = '<a href="javascript:void(0);" onclick="createSubNode('
				+ val + ');">新增子节点</a>&nbsp;&nbsp;|&nbsp;&nbsp; ';
		link += '<a href="javascript:void(0);" onclick="editNode(123);">编辑</a>&nbsp;&nbsp;|&nbsp;&nbsp; ';
		link += '<a href="javascript:void(0);" onclick="deleteFunction('
				+ val  + ');">删除</a>';
		return link;
	}
</script>
<input type="hidden"  id="roleName" name="roleName" value="${roleName}" />
<table class="easyui-treegrid" id="nodeDataGrid"
	data-options="
		url:'${ctx}/test/pages',
		fit:true,
		toolbar:'#tb',
		idField:'id',
		treeField: 'name'
	">
	<thead>
		<tr>
			<th data-options="field:'name',width:200,align:'left'">名称</th>
			<th
				data-options="field:'functionCode',width:100,align:'center'">代码</th>
			<th data-options="field:'url',width:250,align:'center'">URL</th>
			<th data-options="field:'functionSum',width:250,align:'center'">描述</th>

			<th
				data-options="field:'nodeUrl',width:250,align:'center',formatter:formatOpt">操作</th>

		</tr>
	</thead>
</table>
<div id="tb" style="padding: 5px; height: auto">
	<table style="width: 100%">
		<tr>
			<td align="left"></td>
			<c:if test="${roleName=='all'}">
				<td align="right"><a href="javascript:void(0);"
					class="easyui-linkbutton" style="vertical-align: middle;"
					plain="true" onclick="createNode('');">新增主节点</a></td>
			</c:if>
			
		</tr>
	</table>
</div>