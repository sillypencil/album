<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<meta charset='utf-8'>
	<link rel="stylesheet" href="<s:url value="/resources"/>/css/photo.css">
	<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="<s:url value="/resources"/>/js/imagesloaded.pkgd.min.js"></script>
    <script src="<s:url value="/resources"/>/js/masonry.pkgd.min.js"></script>
    <script src="<s:url value="/resources"/>/js/waterfall.js"></script>
</head>
<body>
	<div id='top'>
	    <jsp:include page = "templates/top.jsp" flush="true"/>
	</div>

	<div id='main_container'>
		<div id='next_btn'></div>
		<div id='pre_btn'></div>
		<a href="javascript:void(0);" id='left_arrow'></a>
		<a href="javascript:void(0);" id='right_arrow'></a>
		<div class='left'>
			<div id='show_photo'>
				<img id="imageContent" data-id="${solImage.id}"
				    src="<s:url value="/resources/images"/>/${solImage.imagePath}">
				<div class='btns'>
					<c:if test="${!isMe}">
					<a href="javascript:void(0);" id='collect' role="dialog" data-toggle="modal"
					    data-target="#submitCollectForm">采集</a>
					<a href="javascript:void(0);" id='like'>喜欢</a>
					</c:if>
					<c:if test="${isMe}">
					<a href="javascript:void(0);" id='move' role="dialog" data-toggle="modal"
					    data-target="#changeAlbumForm">挪窝</a>
					<a href="javascript:void();" id='remove'>删除</a>
					</c:if>
				</div>
			</div>

			<div id='add_comment'>
				<div id='info'>
					<a href="../../album/${solImage.id}">
						<img src="../../resources/images/top/me_btn.png" alt="">
					</a>
					<span id='nickname'>昵称</span>
					<span id='time'>YYYY/MM/DD</span>
				</div>
				<div id='my_comment'>
					<form action="/comment/add/${solImage.id}" id="comment_form" method="post">
						<textarea name="content" id="" cols="30" rows="5">
						</textarea>
						<input type="text" value="${solImage.id}" style="display:none" />
						<input type="submit" value="评论" />
					</form>
				</div>
			</div>

			<div id='comments'>
				<ul id="comments_ul">
				<c:forEach var="comment" items="${comments}">
					<li>
						<p>${comment.content}</p>
						<!--
						<a href="javascript:void(0);" id="reply_btn">回复</a>
						-->
					</li>
                </c:forEach>
				</ul>
			</div>
		</div>

		<div class='right'>
			<!--相关推荐或者其他什么的，暂时没想到要放什么东西-->
			<!--
			<div id='album_info'>
				<img src="" alt="">
				<div>
					<h1></h1>
					<h2></h2>
				</div>
			</div>
			-->
			<div id='waterfall'>
				<ul>
				    <c:forEach var="image" items="${images}">
					<li><a href="<c:url value="/image/get"/>/${image.id}" data-id="${image.id}">
					    <img src="<s:url value="/resources/images/compress"/>/${image.imagePath}" alt="">
					</a></li>
					</c:forEach>
				</ul>
			</div>

		</div>
	</div>
	<div>
	    <form action="" method="post" class="modal fade" id="submitCollectForm" role="dialog"
	        aria-labelledby="AlbumLabel">
	        <div class="modal-dialog modal-sm">
	        <div class="modal-content">
	        <div class="radio">
              <label>
                <input type="radio" name="permission" value="ALL">
                全站可见
              </label>
            </div>

	        <div class="radio">
              <label>
                <input type="radio" name="permission" value="FRIEND">
                仅好友可见
              </label>
            </div>

	        <div class="radio">
              <label>
                <input type="radio" name="permission" value="PRIVATE">
                仅个人可见
              </label>
            </div>

                <select name="ownAlbumId" class="form-control input-sm">
                <c:forEach var="album" items="${albums}">
                  <option value="${album.id}">${album.name}</option>
                 </c:forEach>
                </select>
            <br/>
            <input class="btn btn-default" type="submit" value="采集"/>
            </div>
            </div>
            </div>
	    </form>
	</div>
	<div>
	    <form action="" method="post" class="modal fade" id="changeAlbumForm" role="dialog"
	        aria-labelledby="AlbumLabel">
	        <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <select name="albumId" class="form-control input-sm">
                    <c:forEach var="album" items="${albums}">
                      <option value="${album.id}">${album.name}</option>
                     </c:forEach>
                    </select>

                    <br/>
                    <input class="btn btn-default" type="submit" value="挪窝"/>
                </div>
            </div>
            </div>
	    </div>
	</div>
	<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="<s:url value="/resources"/>/js/photo.js"></script>
    <script src="<s:url value="/resources"/>/js/photo_bing.js"></script>
</body>
</html>