<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<meta charset='utf-8'>
	<link rel="stylesheet" href="<s:url value="/resources"/>/css/me.css">
    <script src="<s:url value="/resources"/>/js/jquery-2.1.3.min.js"></script>
    <script src="<s:url value="/resources"/>/js/imagesloaded.pkgd.min.js"></script>
    <script src="<s:url value="/resources"/>/js/masonry.pkgd.min.js"></script>
    <script src="<s:url value="/resources"/>/js/waterfall.js"></script>
</head>
<body>	
	<div id='top' data-id="${result.user.id}">
	    <jsp:include page = "templates/top.jsp" flush="true"/>
	</div>
	<div id='personal_info'>
	    <jsp:include page = "templates/personal_info.jsp" flush="true"/>
	</div>

    <c:if test="${!isImages}">
	<div id='album'>
		<ul id="album_list">
			<li id='adding_album'>
				<a href="javascript:void(0);" id='adding_album_btn'></a>
			</li>
            <jsp:include page = "templates/album_item.jsp" flush="true"/>
		</ul>
	</div>
	</c:if>

    <c:if test="${isImages}">
	<div id='waterfall'>
		<ul id='all_collect'>
			<li class='adding'>
				<a href="javascript:void(0);" id='adding_photo_btn'></a>
			</li>
			<!-- @import photo_item.html -->
	    <jsp:include page = "templates/photo_item.jsp" flush="true"/>
		</ul>
	</div>
	</c:if>

	<div id='fans'>
		<p>${result.user.nickname}<!--昵称-->的所有粉丝</p>
		<ul id="fans_list">
		</ul>
            <jsp:include page = "templates/fans_list_item.jsp" flush="true"/>
		</ul>
	</div>

	<div id="follow">
		<p>${result.user.nickname}<!--昵称-->的所有关注</p>
		<ul id="follow_list">
            <jsp:include page = "templates/follow_list_item.jsp" flush="true"/>
		</ul>
	</div>

	<div id="upload_box">
		<!-- @import upload_box.html -->
            <jsp:include page = "templates/upload_box.jsp" flush="true"/>
	</div>

	<div id="image_process_box">
		<!-- @import image_process_box.html -->
            <jsp:include page = "templates/image_process_box.jsp" flush="true"/>
	</div>

	<div id="modify_info_box">
		<!-- @import modify_info_box.html -->
            <jsp:include page = "templates/modify_info_box.jsp" flush="true"/>
	</div>

	<div id="modify_head_box">
		<!-- @import modify_head_box.html -->
            <jsp:include page = "templates/modify_head_box.jsp" flush="true"/>
	</div>

	<div id="create_album_box">
		<!-- @import creat_album_box.html -->
            <jsp:include page = "templates/create_album_box.jsp" flush="true"/>
	</div>

	<div id='footer'>

	</div>
    <script src="<s:url value="/resources"/>/js/me.js"></script>
    <script src="<s:url value="/resources"/>/js/message_request.js"></script>
</body>
</html>