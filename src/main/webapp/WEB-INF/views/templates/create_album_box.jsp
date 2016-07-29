<%@ page contentType="text/html;charset=UTF-8" language="java" %>
		<h1>创建相册</h1>
		<form action="" id="create_album_form" name="create_album_form" method="post">
			<p><span class="left">相册标题</span><input type="text" name="name" class="right"></p>
			<p>
				<span class="left">相册描述</span>
				<textarea name="description" id="album_description_textarea" cols="30" rows="10" class="right"></textarea>
				<div id="album_description_inputbox" contentEditable="true"></div>
			</p>
			<p class="submit-p"><input type="button" value="取消" id="cancel_album_btn"><input type="submit" value="保存"></p>
		</form>
