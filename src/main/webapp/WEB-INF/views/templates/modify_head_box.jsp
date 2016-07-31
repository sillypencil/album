<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
        <form action="/user/upload/avatar" method="post" enctype="multipart/form-data" id="modify_head_form">
			<img src="<s:url value="/resources/images"/>/${result.user.avatarPath}" alt=""
			    id='current_head'>
			<input type="file" name="avatar" />
			<input type="submit" value="提交" />
		</form>
