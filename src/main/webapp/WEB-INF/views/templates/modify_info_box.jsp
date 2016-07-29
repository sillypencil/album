<%@ page contentType="text/html;charset=UTF-8" language="java" %>
		<form action="/user/modify/info" id="modify_info_form" method="post">
			<a href="javascript:void(0);" id="modify_head_btn" alt="修改头像"><img src="/resources/images/${result.user.avatarPath}" alt="修改头像"/></a>
			<h3 id="show_account">${result.user.account}</h3>
			<p>
				<span class="left">修改昵称</span><input type="text" name="nickname" value="${result.user.nickname}">
			</p>
			<p id="modify_psw"><span class="left">修改密码</span></p>
			<div id="modify_psw_box">
				<p><span class="left">旧密码</span><input type="password" name="oldPassword"></p>
				<p><span class="left">新密码</span><input type="password" name="newPassword"></p>
				<p><span class="left">确认密码</span><input type="password"></p>
			</div>
			<button id="cancel_modify_btn">取消</button>
			<input type="submit">
		</form>
