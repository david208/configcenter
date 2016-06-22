<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div style="padding: 10px 30px 10px 30px 10px 30px">
	<form id="nodeSaveForm" method="post">
		<input type="hidden" name="parUrl" id="parUrl" value="${parUrl}">
		<table>
			<tr>
				<td>节点名称:</td>
				<td><textarea rows="4" cols="30" class="easyui-validatebox"
						name="nodeName" id="nodeName" data-options="required:true"></textarea>
				</td>
				
			</tr>
			<tr>
				<td>节点值:</td>
				<td><textarea rows="4" cols="30" class="easyui-validatebox"
						name="nodeValue" id="nodeValue" data-options="required:true"></textarea>
				</td>
			</tr>
			
			<tr>
				<td>用户:</td>
				<td>
					<input class="easyui-combobox" id="role" name="role" url='${ctx}/test/getAllUser'
									data-options="valueField:'roleName', textField:'roleName',multiple:true,panelHeight:'auto'" >
					
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
$(function() {
	//loadDictionaryData();
});
function loadDictionaryData() {
	comboboxUtil.setComboboxByUrl("role", "${ctx}/test/getAllUser", "roleCode", "roleName", 150, true);
}


	function submitForm() {
		var parUrl=$("#parUrl").val();
		var nodeName=$("#nodeName").val();
		var nodeValue=$("#nodeValue").val();
		var roles =$('#role').combobox('getValues');
		var role="";
		$.each(roles,function(n,value) {  
        	role=role+value+",";
         });  
		$.ajax({
			url : '${ctx}/test/save',
			dataType : 'json',
			data:{parUrl:parUrl,nodeUrl:nodeName,nodeValue:nodeValue,role:role},
			success : function(data) {
				if (data.code == 0) {
					$.messager.show({
						title : '添加',
						msg : '添加成功',
						timeout : 5000,
						showType : 'slide'
					});
					$('#createNode').dialog('close');
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