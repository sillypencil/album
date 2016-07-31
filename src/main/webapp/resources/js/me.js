/*---------------页面加载完事件---------------*/
$(window).ready(function(){
	//...
	if(!document.getElementById("waterfall")){
		var waterfallBox = document.createElement("div");
		waterfallBox.setAttribute("id", "waterfall");
		var allCollectUl = document.createElement("ul");
		var addingLi = addLi("", "adding_photo_btn");
		$(allCollectUl).attr("id", "all_collect");
		$(allCollectUl).append(addingLi);
		$(waterfallBox).append(allCollectUl);
		document.body.appendChild(waterfallBox);
		$(waterfallBox).css("display","none");
	}
	//..
		if(!document.getElementById("album")){
		var albumBox = document.createElement("div");
		albumBox.setAttribute("id", "album");
		var albumUl = document.createElement("ul");
		var addingLi = addLi("adding_album_btn");
		$(albumUl).attr("id", "album_list");
		$(albumUl).append(addingLi);
		$(albumBox).append(albumUl);
		document.body.appendChild(albumBox);
		$(albumBox).css("display", "none");
	}
	//...

	//瀑布流列表自适应居中
	var item_list=$("#all_collect");
	waterfall(item_list, 'li',0);
	setContainerWidth(item_list, 220, 1200);

	var userId = $("#top").attr("data-id");

	//粉丝
	$("#see_fans_btn").click(function(){
		switchBlock("#fans");
		$.ajax({
			type: "get",
			url:"/user/get/" + userId + "/followers",
			dataType: "json",
			success: function(data){
				var fansList=[];
			    for(i in data){
			    	var fans = createMan(data[i].user.id,
			    		data[i].user.avatarPath,
			    		data[i].user.nickname,
			    		data[i].follow);
			   	fansList.push(fans);
			    }
			    $("#fans_list").children("*").remove();;
			    $("#fans_list").append(fansList);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
                console.log("error" + errorThrown);
			}
		});
	});

	//关注
	$("#see_follow_btn").click(function(){
		switchBlock("#follow");
		$.ajax({
			type: "get",
			url:"/user/get/" + userId + "/leaders",
			dataType: "json",
			success: function(data){
			   var leaderList=[];
			    for(i in data){
			    	var leader = createMan(data[i].user.id,
			    		data[i].user.avatarPath,
			    		data[i].user.nickname,
			    		data[i].follow);
			   	leaderList.push(leader);
			    }
			    $("#follow_list").children("*").remove().append(leaderList);
			    $("#follow_list").append(leaderList);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){

			}
		});
	});
	//关注按钮
    $(document).on("click",".follow-btn",function(){
    	var btn=this;
        var id = $(this).attr("data-id");
        $.ajax({
            type: "put",
            url: "/user/follow",
            data: {
                leaderId: id
            },
            success: function(data, textStatus){
				btn.className="cancel-follow-btn";
				btn.innerHTML="取消关注";
            },
            errorThrown: function(XMLHttpRequest, textStatus, errorThrown){
            }
        });
    });

	//	取关按钮
    $(document).on("click",".cancel-follow-btn",function(){
    	var btn=this;
        var id = $(this).attr("data-id");
        $.ajax({
            type: "put",
            url: "/user/follow/cancel",
            data: {
                leaderId: id
            },
            success: function(data, textStatus){
                console.log(textStatus);
                btn.className="follow-btn";
                btn.innerHTML="+关注";
            },
            errorThrown: function(XMLHttpRequest, textStatus, errorThrown){
            }
        });
    });

	// 相册
	$("#personal_info").on("click", "#album_btn", function(){
		console.log(userId);
		switchBlock("#album");
		$.ajax({
			type: "get",
			url:"/album/" + userId + "/ajax",
			dataType: "json",
			success: function(data){
				console.log("data: " + data);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				console.log("XMLHttpRequest: "+XMLHttpRequest+" textStatus: "+textStatus+" errorThrown: "+errorThrown);
				// alert("请求出错，请稍后再试");
			}
		});
	});

	//图片
	$("#personal_info").on("click", "#image_btn", function(){
		switchBlock("#waterfall");
		$.ajax({
			type: "get",
			url:"/image/images/" + 14 + "/ajax",
			dataType: "json",
			success: function(data, textStatus){
				var imgList = [];
				imgList.push(addLi("", "adding_photo_btn"));
//				console.log("like data: "+ data.images);
				for(i in data.images){
//				console.log(data.images[i])
				var item = creatImgItem(data.images[i].id, data.images[i].imagePath);
				imgList.push(item);
				}

				$(item_list).masonry("remove", $(item_list).children("*")).append($(imgList)).masonry("appended",$(imgList), true).masonry("layout");
				$("#image_btn").attr("id","album_btn").html("相册");
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				console.log("XMLHttpRequest: "+XMLHttpRequest+" textStatus: "+textStatus+" errorThrown: "+errorThrown);
			}
		});
	});


	// 采集
	$("#collect_btn").click(function(){
		switchBlock("#waterfall");
		$.ajax({
			type: "get",
			url:"/image/collectImages/" + userId + "/ajax",
			dataType: "json",
			success: function(data){
				var imgList = [];
				imgList.push(addLi("", "adding_photo_btn"));
				for(i in data.images){
				var item = creatImgItem(data.images[i].id, data.images[i].imagePath);
				imgList.push(item);
				}

				$(item_list).masonry("remove", $(item_list).children("*")).append($(imgList)).masonry("appended",$(imgList), true).masonry("layout");
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				console.log("XMLHttpRequest: "+XMLHttpRequest+" textStatus: "+textStatus+" errorThrown: "+errorThrown);

			}
		});
	});

	//喜欢
	$("#like_btn").click(function(){
		switchBlock("#waterfall");
		$.ajax({
			type: "get",
			url:"/image/favorImages/" + userId + "/ajax",
			dataType: "json",
			success: function(data){
				var imgList = [];
				imgList.push(addLi("", "adding_photo_btn"));
				for(i in data.images){
				var item = creatImgItem(data.images[i].id, data.images[i].imagePath);
				imgList.push(item);
				}

				$(item_list).masonry("remove", $(item_list).children("*")).append($(imgList)).masonry("appended",$(imgList), true).masonry("layout");
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				console.log("XMLHttpRequest: "+XMLHttpRequest+" textStatus: "+textStatus+" errorThrown: "+errorThrown);

			}
		});
	});

	//账号设置
	$("#modify_info_btn").click(function(){
		$("#modify_info_box").show();
		$("#modify_head_btn").click(function(){
			$("#modify_head_box").show();
		});
		$("#cancel_modify_btn").click(function(e){
			stopDefault(e);
			$("#modify_info_box").hide();
		})
	});

	//修改密码下拉框
	$("#modify_psw").click(function(){
		$("#modify_psw_box").toggle(300);
	});
	//修改资料表单提交
	$("#modify_info_form").submit(function(e){
		$.ajax({
			type: "put",
			url: "/user/modify/info",
			data: $("#modify_info_form").serialize(),
			dataType: "json",
			success: function(data, textStatus, jqXHR){
			    if(textStatus === 'nocontent'){
			        console.log("success");
			        $("#modify_info_box").hide();
			    }
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				console.log("XMLHttpRequest: "+XMLHttpRequest+" textStatus: "+textStatus+" errorThrown: "+errorThrown);

			}
		});

		//阻止默认事件
		stopDefault(e)
	});
//	//修改头像提交
//	$("#modify_head_form").submit(function(){
//		stopDefault(e);
//		$.ajax({
//			type: "post",
//			url: "",
//			data: "",
//			dataType: "",
//			success: function(){
//
//			},
//			error: function(XMLHttpRequest, textStatus, errorThrown){
//
//			}
//		})
//	});

	//创建相册
	$("#adding_album_btn").click(function(){
		$("#create_album_box").show();
	});
	//关闭创建相册
	$("#cancel_album_btn").click(function(){
		$("#create_album_form")[0].reset();
		$("#create_album_box").hide();
	})

	//创建相册表单提交
	$("#create_album_form").submit(function(e){
		//阻止默认事件
		stopDefault(e);
		$.ajax({
			type: "post",
			url: "/album/create",
			data: $("#create_album_form").serialize(),
			dataType: "json",
			success: function(data, textStatus, jqXHR){
			    console.log(data);
			    var container=document.createElement("li");
				var albumLink=document.createElement("a");
				var albumName=document.createElement("span");
				var albumDescription=document.createElement("p");
				var deleteBtn=document.createElement("a");

				$(albumLink).attr("href","/album/"+data.id+"/images").html("<img src=resources/images/" + data.cover +"/>");
				$(albumName).html(data.name);
				$(albumDescription).html(data.description);
				$(deleteBtn).addClass("deletBtn").attr("href", "javascript:void(0);");
				$(container).append(albumLink).append(albumName).append(albumDescription).append(deleteBtn);
				$("#album_list").append(container);

			},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				console.log("XMLHttpRequest: "+XMLHttpRequest+" textStatus: "+textStatus+" errorThrown: "+errorThrown);

			}
		});


	});

	// 删除相册
	$("#delete_album_btn").click(function(){
		var deleteConfirm = confirm("确定删除？");
		var id = $(this).attr("data-id");
		if(deleteConfirm==true){
			$.ajax({
				type: "post",
				url: "",
				data: "",		//这里应该是传相册的album，暂时还没获取
				
				dataType: "",
				success: function(data){

				},
				error: function(){

				}
			});
		}
	});

	// 添加采集
	$(document).on("click", ".adding_photo_btn", function(){
		$("#image_process_box").show();
	})
	//processs
	//取消添加采集
	$("#cancel_upload_btn").click(function(){
		$("#image_upload_form").reset();
		$("#image_process_box").hide();
	})
	
//	$("#image_upload_form").submit(function(e){
//
//		//ajax提交
//		$.ajax({
//			type: "post",
//			url: "/image/upload",
//			data: $("#image_upload_form").serialize(),
//			contentType:"multipart/form-data",
//			dataType: "json",
//			success: function(data, textStatus){
//                console.log("data " + data);
//			},
//			error: function(XMLHttpRequest, textStatus, errorThrown){
//                console.log("error " + errorThrown);
//			}
//		});
//		//最后阻止跳转
//		stopDefault(e);
//	})

  	//
  	$("#close_btn").click(function(){
  	    $("#image_process_box").hide();
  	});


}).keyup(function(event){		//键盘输入事件
   	if(event.keyCode===27){		//Esc键
   		//隐藏登陆框和注册框
   		hideBox();
   	}
   });

/*---------------窗口大小改变----------------*/
$(window).resize(function(){	//改变窗口大小时，瀑布流列表自适应居中
	var item_list=document.getElementById('all_collect')
	setContainerWidth(item_list, 220, 1200);
});

//列表随窗口大小宽度自适应 setContainerWidth(对象，列宽，列表最大宽度)
function setContainerWidth(obj, oWidth, maxWidth){
	var windowWidth=document.body.clientWidth||window.innerWidth;
	var targetWidth=0;
	if(!maxWidth){
		maxWidth = 1360;
	}
	if(windowWidth<maxWidth){
		targetWidth=parseInt(windowWidth/oWidth)*oWidth;
	}
	else{
		targetWidth=parseInt(maxWidth/oWidth)*oWidth;
	}
	$(obj).css('width',targetWidth);
}

//各个模块之间切换
function switchBlock(obj){

	$("#album").hide();
	$("#waterfall").hide();
	$("#fans").hide();
	$("#follow").hide();
	$(obj).show();

}

function hideBox(){

	$("#upload_box").hide();
	$("#image_process_box").hide();
	$("#modify_info_box").hide();
	$("#create_album_box").hide();
}

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

//创建粉丝
function createMan(id, avatarPath, nickname, follow){
	var oLi=document.createElement("li");
	var oHeadLink=document.createElement("a");
	var oImg=document.createElement("img");
	var oNickname=document.createElement("span");
	var oBtn=document.createElement("a");
	oHeadLink.setAttribute("class","head-link");
	oHeadLink.setAttribute("href","/album/"+id);
	oImg.setAttribute("src", "/resources/images/"+avatarPath);
	oNickname.setAttribute("class", "nickname");
	oNickname.innerHTML=nickname;
	oBtn.setAttribute("data-id",id);
	oBtn.setAttribute("href", "javascript:void(0);");
	oBtn.setAttribute("data-isFollow", follow)
	if(follow){
        oBtn.setAttribute("class", "cancel-follow-btn")
		oBtn.innerHTML="取消关注";
	}
	else{
        oBtn.setAttribute("class", "follow-btn")
		oBtn.innerHTML="+关注";
	}
	// console.log("oHeadLink.href= "+oHeadLink.href);
	oHeadLink.appendChild(oImg);
	oLi.appendChild(oHeadLink);
	oLi.appendChild(oNickname);
	oLi.appendChild(oBtn);
	return oLi;
}

//创建图片项
function creatImgItem(picId, picPath){
	var container = document.createElement("li");
	var imgLink = document.createElement("a");
	var picture = document.createElement("img");

	picture.setAttribute("data-imgid", picId);
	picture.setAttribute("src", "/resources/images/"+picPath);
	imgLink.setAttribute("href", "/image/get/"+picId);

	imgLink.appendChild(picture);
	container.appendChild(imgLink);
	return container;
}

//添加第一个li
function addLi(id, className){
	var Li=document.createElement("li");
	Li.setAttribute("class", "adding");
	$(Li).html("<a href='javascript:void(0);' id='"+id+"'' class='"+className+"'></a>");
	return Li;
}