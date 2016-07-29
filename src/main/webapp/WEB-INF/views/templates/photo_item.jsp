<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<c:forEach var="image" items="${result.images}">
<li>
	<a href="<c:url value="/image/get"/>/${image.id}">
		<img src="<s:url value="/resources/images/compress"/>/${image.imagePath}"
		    data-imgID='${image.id}' alt="%s"/>
	</a>
</li>
</c:forEach>
