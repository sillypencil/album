<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
	<title>Album_首页</title>
	<meta charset='utf-8'>
		<link rel="stylesheet" type="text/css" href="<s:url value="/resources"/>/css/main.css">
    	<script src="<s:url value="/resources"/>/js/jquery-2.1.3.min.js"></script>
    	<script src="<s:url value="/resources"/>/js/imagesloaded.pkgd.min.js"></script>
    	<script src="<s:url value="/resources"/>/js/masonry.pkgd.min.js"></script>
    	<script src="<s:url value="/resources"/>/js/waterfall.js"></script>
</head>
<body>
	<div id='top'>
	    <jsp:include page = "templates/top.jsp" flush="true"/>
	</div>
	<div id='waterfall'>
		<ul>
            <li id='me_info'>
                <div>
                    <div>
                        <img src="<s:url value="/resources/images/compress"/>/<c:out value="${result.user.avatarPath}"/>"
                            alt="" id='head'/>
                        <span id='nickname'>
                            <c:out value="${result.user.nickname}"/>
                        </span>
                    </div>
                    <a href="<c:url value="/image/me"/>"><span class="num">
                        <c:out value="${result.user.imageNum}"/>
                    </span><span class="numTitle">照片</span></a>
                    <a href="<c:url value="/album/me"/>"><span class="num">
                        <c:out value="${result.user.albumNum}"/>
                    </span><span class="numTitle">相册</span></a>
                </div>
            </li>
            <jsp:include page = "templates/photo_item.jsp" flush="true"/>
		</ul>
		<!--<div id='loading'></div>-->
	</div>
	<script src="<s:url value="/resources"/>/js/main.js"></script>
</body>
</html>