<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<title>Album</title>
	<link rel="stylesheet" href="<s:url value="/resources"/>/css/index.css">
	<script src="<s:url value="/resources"/>/js/jquery-2.1.3.min.js"></script>
</head>
<body>
	<div id='login_register'>
		<a href="javascript:void(0);" id='register_btn'>注册</a>
		<a href="javascript:void(0);" id='login_btn'>登陆</a>
	</div>
	<c:if test="${!empty message}">
	    ${message}
	</c:if>

	<div id='login_box'>
		<form action="<c:url value="/user/login"/>" method="post" id='login_form' name='login_form'>
		<table>
			<tr>
				<td class="left">账号</td>
				<td class="right">
					<input type="text" name='account' id='account'>
				</td>
			</tr>
			<tr>
				<td class="left">密码</td>
				<td class="right">
					<input type="password" name="password" id="password">
				</td>
			</tr>
		</table>
		<input type="submit" value='登陆'>
	</form>
	</div>

	<div id='register_box'>
		<!-- @import register.html -->
		<form action="<c:url value="/user/register"/>" method='post' id='register_form' name='register_form'>
		<table>
			<tr>
				<td class="left">账号</td>
				<td class="right">
					<input type="text" name='account' id='account'>
				</td>
			</tr>
			<tr>
				<td class="left">昵称</td>
				<td class="right">
					<input type="text" name='nickname' id='nickname'>
				</td>
			</tr>
			<tr>
				<td class="left">密码</td>
				<td class="right">
					<input type="password" name="password" id="password">
				</td>
			</tr>
			<tr>
				<td class="left">确认密码</td>
				<td class="right">
					<input type="password" name="conform_password" id="conform_password">
				</td>
			</tr>
		</table>
		<input type="submit" value='注册'>
	</form>
	</div>
	<script src="<s:url value="/resources"/>/js/index.js"></script>
	<script src="<s:url value="/resources"/>/js/check_form.js"></script>
</body>
</html>