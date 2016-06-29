<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	response.setStatus(200);
%>

<!DOCTYPE html>
<html>


<body>
	<div class="mid clearfix" style="margin-top: 100px;">
		<div class="error_page">
			<div class="error_title">
				<h2>错误403</h2>
			</div>
			<div class="error_content clearfix">
				<div class="error_left fl">
					<%-- <img src="${ctx}/static/images/error_403.png" width="269"
						height="285" border="0" /> --%>
				</div>
				<div class="error_right fl">
					<div class="error_sorry">
						<h1>sorry 你访问的页面需要更高权限</h1>
						<p>您可以通过以下方式继续访问......</p>
					</div>
					<div class="error_back">
						<ul class="clearfix">
							<li><a href="${ctx}">返回首页</a></li>
							<li class="on"><a href="javascript:history.back();">返回上一页</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>