<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<c:forEach var="album" items="${result.albums}">
<li>
	<a href="<c:url value="/album/${album.id}/images"/>">
		<img
		    src="<s:url value="/resources/images/compress"/>/<c:out value="${album.cover.imagePath}"/>"/>
	</a>
	<span>
	    ${album.name}
	</span>
	<p>${album.description}</p>
	<a href="javascript:void(0);" class='deletBtn'></a>
</li>
</c:forEach>