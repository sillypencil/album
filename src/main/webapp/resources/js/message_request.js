//向后台发送请求，确认是否有系统消息
//成功请求并且返回数据不为空的话，则执行提示消息函数
function requestMsg(){
	$.ajax({
		type: "put",
		url: "/notify/get",
		dataType: "json",
		success: function(data){
//			showMsg(data);
            console.log(data);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown){
		
		}
	});
}


function showMsg(data){
	if(data!=null){
		//在头部显示消息数，并且在消息框添加具体的消息
		//等确定传过来的json格式，再具体写..

	}
	else{
		//什么都不做
	}
}