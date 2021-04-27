<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta charset=UTF-8>
	<title>错误信息</title>
	<link rel="stylesheet" type="text/css" href="css/message.css">
	<link rel="icon" type="image/x-ico" href="images/stu.ico">
</head>
<body>
	<main>
		<%
			String login_info = (String) request.getAttribute("login_info");
			String register_info = (String) request.getAttribute("register_info");
		%>
		<div class="message">
			<div class="left">
				<%
					if (login_info != null) {
				%>
                <h3 style="color: red"><%=login_info%></h3>
                <%
					}
                %>
				<%
					if (register_info != null) {
				%>
				<h3 style="color: red"><%=register_info%></h3>
				<%
					}
				%>
                <%
					if (login_info == null && register_info == null) {
                %>
				<h3 style="color: orange">对不起！您还未登录!</h3>
				<%
					}
				%>
			</div>
			<div class="right">
				<%
					if (register_info != null) {
				%>
				<a class="relogin" href="register.html" style="color: deepskyblue">重新注册></a>
				<%
					}
					else {
				%>
				<a class="relogin" href="login.html" style="color: deepskyblue">重新登录></a>
				<%
					}
				%>
			</div>
		</div>
	</main>
	
	<footer>
		<div class="info">
				<ul>
					<a href="#"><li>学生信息管理系统</li></a>
					<a href="#"><li>帮助与反馈</li></a>
					<a href="#"><li>联系我们</li></a>
				</ul>
			</div>
			<div class="copyright">
				&copy; Copyright. All rights reserved. Design by <a href="http://www.github.com/cs-scx/">Scx</a>
			</div>
	</footer>
</body>
</html>