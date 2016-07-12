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
	var name="'"+row.name+"'";
	return "<a href='javascript:void(0);' onclick=updateConfig("+name+")>修改</a>";			
}

function formatOpt1(opt,row){	
	debugger;
	var name="'"+row.name+"'";
	return "<a href='javascript:void(0);' onclick=deleteConfig("+name+")>删除</a>";		
}
function updateConfig(name) {
	window.location.href = "${ctx}/editProperty?system=${system}&version=${version}&env=${env}&name="+name;
}

function deleteConfig(name) {
	$.ajax({
		url : "${ctx}/deleteProperty?system=${system}&version=${version}&env=${env}&name="+name,
		dataType : 'json',
		success:function( data ){
			if(data.code == 1){
				$.messager.show({
					title:'处理成功',
					msg:'删除成功',
					timeout:5000,
					showType:'slide',
				});
			}
			else {
				$.messager.alert('Error',data.attachment);	
			}
			$("#tenderDatagridMain").datagrid('reload');
		}

	});
}

function add(){
	window.location.href = '${ctx}/addProperty?system=${system}&version=${version}&env=${env}';
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
		sortName:'tag,name',
       sortOrder:'desc,asc',
        remoteSort:false ,
        multiSort:true,
        nowrap:false
        	
	">
	<thead>
		<tr>
		<th data-options="field:'tag',width:100,align:'center',sorter:function(a,b){  
				if(null == a ){
				if (null == b )
				return 0;
				else
				   return -1 ;
				   }
				else if (null == b)
				return 1;
				return a.localeCompare(b);
			},
			styler: function(value,row,index){
				if (null != value ){
					return 'color:'+intToARGB(hashCode(value))+';';
				}
			}
			
			 ">tag</th>
			<th data-options="field:'name',width:400,align:'center'">键</th>
			<th data-options="field:'value',width:400,align:'center'">值</th>
			<th data-options="field:'memo',width:100,align:'center'">备注</th>
			
		    <th data-options="field:'opt',formatter:formatOpt,width:90,align:'center'">修改</th> 
		    <th data-options="field:'opt1',formatter:formatOpt1,width:90,align:'center'">删除</th> 
		</tr>
	</thead>

</table>

<div id ='tb'>
<table> 
   <tr>
    <td><label>TOKEN:</label></td>
    <td>
     <input value="${longToken}" style="width:300px"></input>
    </td>
    <td><a href="javascript:void(0);" class="easyui-linkbutton"
		iconCls="icon-reload" style="vertical-align: middle;"
		onclick="add();">新增</a>
    </td>
   </tr>	
</table>
<form name="searchForm" id="searchForm" method="post" enctype="multipart/form-data" 
  > 
 <p>Upload File: <input type="file" name="file" /> 
 <input onclick="uploadAndSubmit();" type="button" value="Submit" /></p> 
 </form>
 
 <div><span id="bytesRead"> 
 </span> <span id="bytesTotal"></span> 
 </div> 

</div>


<script type="text/javascript">
function uploadAndSubmit() { 
	debugger;
		$("#searchForm").form('submit', {
			url:"${ctx}/importProperties?system=${system}&version=${version}&env=${env}",
			success : function(data) {
				eval(" var data = "+data);
				if (data.code >= 0) {
				
					$.messager.show({
						title : '添加配置',
						msg :'添加配置成功',
						timeout : 5000,
						showType : 'slide'
					});
					clearForm();
				} else {
					$.messager.show({
						title : '添加黑名单',
						msg : data.attachment,
						timeout : 5000,
						showType : 'slide'
					});
				}
				$("#tenderDatagridMain").datagrid('reload');
			}
		});
	};
	function clearForm() {
		$('#searchForm').form('clear');
	};
	function back(){
		$("#tenderDatagridMain").datagrid('reload');
	}


function hashCode(str) { // java String#hashCode
    var hash = 0;
    for (var i = 0; i <  str.length; i++) {
       hash = str.charCodeAt(i) + ((hash <<  5) - hash);
    }
    return hash;
} 

function intToARGB(i){
    return ((i >> 24)& 0xFF).toString(16) + 
           ((i >> 16)& 0xFF).toString(16) + 
           ((i >> 8)& 0xFF).toString(16) + 
           (i& 0xFF).toString(16);
}
</script>
</html>