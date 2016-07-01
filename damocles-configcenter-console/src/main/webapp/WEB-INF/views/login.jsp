<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<TITLE>登录</TITLE>
<META content="text/html; charset=GBK" http-equiv=Content-Type>

<STYLE type=text/css>
BODY {
	PADDING-BOTTOM: 0px;
	MARGIN: 0px;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	BACKGROUND: url(static/styles/loginImg/back-login-bg.jpg) #dbf3ff
		no-repeat center top;
	FONT-SIZE: 12px;
	PADDING-TOP: 0px
}

HTML {
	PADDING-BOTTOM: 0px;
	MARGIN: 0px;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	BACKGROUND: url(static/styles/loginImg/back-login-bg.jpg) #dbf3ff
		no-repeat center top;
	FONT-SIZE: 12px;
	PADDING-TOP: 0px
}

IMG {
	BORDER-BOTTOM: 0px;
	BORDER-LEFT: 0px;
	BORDER-TOP: 0px;
	BORDER-RIGHT: 0px
}

A {
	TEXT-DECORATION: none
}

H2 {
	PADDING-BOTTOM: 0px;
	LINE-HEIGHT: 16px;
	MARGIN: 0px;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	FONT-SIZE: 14px;
	FONT-WEIGHT: bold;
	PADDING-TOP: 0px
}

H3 {
	PADDING-BOTTOM: 0px;
	LINE-HEIGHT: 16px;
	MARGIN: 0px;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	FONT-SIZE: 14px;
	FONT-WEIGHT: bold;
	PADDING-TOP: 0px
}

H4 {
	PADDING-BOTTOM: 0px;
	LINE-HEIGHT: 16px;
	MARGIN: 0px;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	FONT-SIZE: 14px;
	FONT-WEIGHT: bold;
	PADDING-TOP: 0px
}

H5 {
	PADDING-BOTTOM: 0px;
	LINE-HEIGHT: 16px;
	MARGIN: 0px;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	FONT-SIZE: 14px;
	FONT-WEIGHT: bold;
	PADDING-TOP: 0px
}

H6 {
	PADDING-BOTTOM: 0px;
	LINE-HEIGHT: 16px;
	MARGIN: 0px;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	FONT-SIZE: 14px;
	FONT-WEIGHT: bold;
	PADDING-TOP: 0px
}

UL {
	PADDING-BOTTOM: 0px;
	LIST-STYLE-TYPE: none;
	MARGIN: 0px;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	LIST-STYLE-IMAGE: none;
	PADDING-TOP: 0px
}

LI {
	PADDING-BOTTOM: 0px;
	LIST-STYLE-TYPE: none;
	MARGIN: 0px;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	LIST-STYLE-IMAGE: none;
	PADDING-TOP: 0px
}

.tol {
	ZOOM: 1;
	OVERFLOW: hidden
}

#back_login_container {
	PADDING-BOTTOM: 0px;
	MARGIN: 0px auto;
	PADDING-LEFT: 0px;
	WIDTH: 529px;
	PADDING-RIGHT: 0px;
	HEIGHT: 376px;
	PADDING-TOP: 156px
}

#back_login_head {
	WIDTH: 529px;
	HEIGHT: 126px
}

#back_login_left {
	WIDTH: 194px;
	FLOAT: left;
	HEIGHT: 250px
}

#back_login_right {
	WIDTH: 335px;
	BACKGROUND: url(static/styles/loginImg/back-login-right-bg.jpg)
		no-repeat;
	FLOAT: left;
	HEIGHT: 250px
}

#back_login_right UL {
	
}

#back_login_right UL LI {
	PADDING-BOTTOM: 12px;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	PADDING-TOP: 0px
}

#back_login_right LABEL {
	TEXT-ALIGN: right;
	PADDING-BOTTOM: 0px;
	PADDING-LEFT: 0px;
	WIDTH: 75px;
	PADDING-RIGHT: 8px;
	FLOAT: left;
	COLOR: #234692;
	FONT-SIZE: 14px;
	VERTICAL-ALIGN: middle;
	FONT-WEIGHT: bold;
	PADDING-TOP: 5px
}

#back_login_right UL LI INPUT {
	BORDER-LEFT: #585858 1px solid;
	WIDTH: 181px;
	BACKGROUND: #fff;
	HEIGHT: 19px;
	VERTICAL-ALIGN: middle;
	BORDER-TOP: #585858 1px solid
}

#back_login_right INPUT.yz {
	WIDTH: 46px;
	MARGIN-RIGHT: 8px
}

#back_login_right UL LI IMG {
	WIDTH: 51px;
	HEIGHT: 21px;
	VERTICAL-ALIGN: middle
}

#back_login_right H2 {
	PADDING-BOTTOM: 0px;
	LINE-HEIGHT: 32px;
	PADDING-LEFT: 82px;
	PADDING-RIGHT: 0px;
	COLOR: #234692;
	FONT-SIZE: 12px;
	FONT-WEIGHT: normal;
	PADDING-TOP: 9px
}

#back_login_right H2 IMG {
	WIDTH: 85px;
	HEIGHT: 32px;
	VERTICAL-ALIGN: middle;
	MARGIN-RIGHT: 10px
}

#back_login_right H2 A {
	COLOR: #234692;
	TEXT-DECORATION: underline
}

#back_login_right H3 {
	TEXT-ALIGN: right;
	PADDING-BOTTOM: 0px;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 48px;
	COLOR: #fff;
	FONT-SIZE: 12px;
	FONT-WEIGHT: normal;
	PADDING-TOP: 70px
}

#back_login_footer {
	TEXT-ALIGN: center;
	PADDING-BOTTOM: 0px;
	LINE-HEIGHT: 16px;
	MARGIN: 0px auto;
	PADDING-LEFT: 0px;
	width: 100%;
	PADDING-RIGHT: 0px;
	BACKGROUND: url(static/images/back-login-footer.jpg) no-repeat;
	HEIGHT: 110px;
	COLOR: #002d8f;
	PADDING-TOP: 8px;
	bottom:0px;
	position: absolute;
	background-size:cover;
}

.denglu {
	BORDER-LEFT: #585858 1px solid;
	WIDTH: 85px;
	BACKGROUND: #fff;
	HEIGHT: 32px;
	VERTICAL-ALIGN: middle;
	BORDER-TOP: #585858 1px solid;
	BACKGROUND: #fff;
	HEIGHT: 32px;
	VERTICAL-ALIGN: middle;
}
</STYLE>
<script type="text/javascript">
	if (top != window) {
		top.location.href = window.location.href;
	}
</script>
<script src="${ctx}/static/fusionChart/js/FusionCharts.js"
	type="text/javascript"></script>
</HEAD>
<BODY>
	<form action="${ctx}/login" method="post">
		<DIV id=back_login_container>
			<DIV id=back_login_head>
				<IMG src="${ctx}/static/styles/loginImg/back-login-top.jpg">
			</DIV>
			<DIV class=tol>
				<DIV id=back_login_left>
					<IMG src="${ctx}/static/styles/loginImg/back-login-left.jpg">
				</DIV>
				<DIV id=back_login_right>
					<UL>
						<LI><LABEL for=Name>用户名：</LABEL><INPUT tabIndex=1 size=15
							type=text name=username></LI>
						<LI><LABEL for=Password>密&nbsp;&nbsp;码：</LABEL><INPUT
							tabIndex=2 size=15 type=password name=password></LI>
					</UL>
					<H2>
						<INPUT class=denglu
							src="${ctx}/static/styles/loginImg/back-login-btn.jpg" type=image>&nbsp;
					</H2>
					<H3></H3>
				</DIV>
			</DIV>
		
		</DIV>
	<!-- 	<div id="chartDiv" align="center">
			<script type="text/javascript">
				var myChart1 = new FusionCharts(
						"${ctx}/static/Column2D.swf",
						"myChartId", "600", "350");
				myChart1
						.setDataXML("<graph baseFont='SunSim' baseFontSize='12' caption='分析' subcaption='' yAxisMinValue='51650.1' yAxisMaxValue='71118.3' xaxisname='日期' yaxisname='数量' hovercapbg='FFECAA' hovercapborder='F47E00' formatNumberScale='0' decimalPrecision='0' showvalues='1' numdivlines='10' numVdivlines='0' shownames='1' rotateNames='1'><set name='2009-10-04' value='57653' color='AFD8F8'/><set name='2009-10-05' value='57389' color='F6BD0F'/><set name='2009-10-06' value='59256' color='8BBA00'/><set name='2009-10-07' value='62762' color='FF8E46'/><set name='2009-10-08' value='63287' color='008E8E'/><set name='2009-10-09' value='60109' color='D64646'/><set name='2009-10-10' value='64653' color='8E468E'/><set name='2009-10-11' value='61687' color='588526'/></graph>");
				myChart1.render("chartDiv");
			</script>
		</div> -->
		<DIV id=back_login_footer>版权@意真（上海）金融信息服务有限公司</DIV>
	</FORM>
</BODY>
</HTML>