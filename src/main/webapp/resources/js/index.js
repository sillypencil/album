//阻止冒泡时间函数 stopPropagation( e )
function stopPropagation( e ) {
    e = e || window.event;
    if(e.stopPropagation) { //W3C阻止冒泡方法
        e.stopPropagation();
    } else {
        e.cancelBubble = true; //IE阻止冒泡方法
    }
}
//阻止默认行为 stopDefault( e )
function stopDefault( e ) {
     if ( e && e.preventDefault )
        e.preventDefault();
    else
        window.event.returnValue = false;

    return false;
}

$(document).ready(function(){	//文档加载完后执行的函数
	//登录注册按钮点击事件
	$("#login_btn").click(function(){
		$("#login_box").show().click(function(e){
			event.stopPropagation();
		}).focus(function(){
			alert('focus');
		});
		event.stopPropagation();
	});

	$("#register_btn").click(function(){
		$("#register_box").show().click(function(){
			event.stopPropagation();
		});
		event.stopPropagation();
	});

//登录注册表单验证
	var $loginForm=document.getElementById('login_form');
	$($loginForm).submit(function(e){
	});

	var $registerForm=document.getElementById('register_form');
	$($registerForm).submit(function(e){
	});


}).click(function(){	//文档被点击时执行
	//隐藏登录和注册框
	$("#login_box").hide();
	$("#register_box").hide();
}).keyup(function(event){		//键盘输入事件
	if(event.keyCode===27){		//Esc键
		//隐藏登陆框和注册框
		$("#login_box").hide();
		$("#register_box").hide();
	}
});

