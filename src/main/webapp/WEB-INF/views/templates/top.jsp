<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<a href="<c:url value="/user/main"/>">
    <img src="<s:url value="/resources"/>/images/main/logo.png">
</a>
<div class='nav'>
	<ul>
		<li><a href="<c:url value="/user/main"/>">首页</a></li>
		<li><a href="<c:url value="/album/me"/>">相册</a></li>
		<li><a href="<c:url value="/image/me"/>">照片墙</a></li>
	</ul>
</div>

<!--已登陆-->
<div id='me_msg'>
	<a href="javascript:void();" id="msg_btn"></a>
	<a href="javascript:void();" id="me_btn"></a>
</div>
<div id='me'>
	<ul>
		<li><a href="">选项A</a></li>
		<li><a href="">选项B</a></li>
		<li><a href="">选项C</a></li>
		<li><a href="">选项D</a></li>		
	</ul>
</div>
<div id="msg">
	<ul>
		<li>XXX在<a href="">xxxx</a>下给你留言了</li>
		<li>XXX在<a href="">xxxx</a>下给你点赞了</li>
		<li>XXX收藏了你的照片<a href="">xxxx</a></li>
		<li>XXX在<a href="">xxxx</a>下给你留言了</li>
	</ul>
</div>