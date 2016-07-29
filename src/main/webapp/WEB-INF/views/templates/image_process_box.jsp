<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
			<h1>上传采集</h1>
		<a href="#" id="close_btn">x</a>
		<form action="/image/upload" method="post" id="image_upload_form" enctype="multipart/form-data">
			<div id="select_pic">
				<img src="../../resources/images/pic/11.jpg" title="更换图片">
				<input type="file" name="image"/>
			</div>
			<div id="image_process_option">
				<select name="ownAlbumId">
					<option value=null>请选择相册</option>
					<c:forEach var="album" items="${albums}">
                        <option value="${album.id}">${album.name}</option>
					</c:forEach>
				</select>
				<p>
				    <label><input type="radio" name="permission" value="ALL"/>全站可见</label>
				    <label><input type="radio" name="permission" value="FRIEND"/>仅好友可见</label>
				    <label><input type="radio" name="permission" value="PRIVATE"/>自己可见</label>
				</p>

				<div id="image_beautify_box">
					<p>一键美图</p>
					<ul id="beaautify_btns">
						<li>
				    <label><input type="radio" name="filterType" value="DEFAULT"/>默认</label>
						</li>
						<li>
				    <label><input type="radio" name="filterType" value="AUTO"/>自动</label>
						</li>
						<li>
				    <label><input type="radio" name="filterType" value="PERSON"/>人物</label>
						</li>
						<li>
				    <label><input type="radio" name="filterType" value="FOOD"/>食物</label>
						</li>
						<li>
				    <label><input type="radio" name="filterType" value="STATIC"/>静止</label>
						</li>
						<li>
				    <label><input type="radio" name="filterType" value="VIEW"/>景物</label>
						</li>
					</ul>
				</div>
			</div>
			<p>
				<input type="button" value="取消" id="cancel_upload_btn">
				<input type="submit" value="采下来">
			</p>
		</form>
