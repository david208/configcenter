<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">

<link href="${ctx}/static/css/common.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/jquery-validation/1.11.1/validate.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/jquery-easyui-1.3.5/themes/default/easyui.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/jquery-easyui-1.3.5/themes/icon.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css">
<link href="${ctx}/static/uploadify/uploadify.css" rel="stylesheet"
	type="text/css" />
	
<script src="${ctx}/static/jquery/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.11.1/messages_bs_zh.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-easyui-1.3.5/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/js/common.js" type="text/javascript"></script>
<script src="${ctx}/static/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/uploadify/jquery.uploadify.js"></script>
<script src="${ctx}/static/fusionChart/js/FusionCharts.js" type="text/javascript"></script>
<sitemesh:head/>
</head>

<body class="easyui-layout">
	<sitemesh:body/>
</body>
</html>