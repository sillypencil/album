<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
		<div class='left'>
			<div class='head-container'>
				<img src="<s:url value="/resources/images"/>/<c:out value="${result.user.avatarPath}"/>"
				 alt=""/>
				<span id='username'>${result.user.nickname}</span>
			</div>
			<div class="num-container">
				<a href="javascript:void(0);" id="see_fans_btn">
					<span class='num'>${result.user.fansNum}</span>
					<span class='num-title' >粉丝</span>
				</a>
				<a href="javascript:void(0);" id="see_follow_btn">
					<span class="num">${result.user.followNum}</span>
					<span class="num-title" >关注</span>
				</a>
			</div>
		</div>


		<div class='right'>
			<a href="javascript:void(0);" id="modify_info_btn">账号设置</a>
			<c:if test="${inAlbum}">
			<a href="javascript:void(0);" id="delete_album_btn" data-id="${album.id}">删除相册</a>
			</c:if>
		</div>
		<div id='personal_info_footer'>
			<div class="nav">
                <c:if test="${isImage}">
				<a href="javascript:void();" id='album_btn'>相册</a>
				</c:if>
                <c:if test="${!isImage}">
				<a href="javascript:void(0);" id='image_btn'>图片</a>
				</c:if>
				<a href="javascript:void(0);" id='collect_btn'>采集</a>
				<a href="javascript:void(0);" id='like_btn'>喜欢</a>
			</div>
			<c:if test="${inAlbum}">
			<span id='album_name'>${album.name}</span>
			</c:if>
		</div>
