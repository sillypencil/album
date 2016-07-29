$(window).ready(function(){
	//瀑布流列表自适应居中
	var item_list=document.getElementById('waterfall').getElementsByTagName('ul')[0];
	waterfall(item_list, 'li',0);

    var imageId = $("#imageContent").attr("data-id");

//评论提交表单
	$("#comment_form").submit(function(e){
		stopDefault(e);

		$.ajax({
			type: "post",
			url: "/comment/add/" + imageId,
			data: $("#comment_form").serialize(),
			dataType: "json",
			success: function(data){
                // console.log(data);
                $("#comment_form")[0].reset();
                var container=document.createElement("li");
                var text=document.createElement("p");

                $(text).html(data.content);
                $(container).append(text);
                $("#comments_ul").append(container);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){

			}
        });
	});

	//图片切换
	$("#left_arrow").click(function(){
		var dataId="", dataSrc="";
		 $("#waterfall>ul>li").each(function(){
            // console.log("data-id= "+$(this).children("a").attr("data-id"));
            if($(this).children("a").attr("data-id")==$("#imageContent").attr("data-id")){
                // console.log("target-id = "+$(this).next().children("a").attr("data-id"));
                if($(this).prev().children("a").attr("data-id")){
                    dataId = $(this).prev().children("a").attr("data-id");
                    dataSrc = $(this).prev().children("a").children("img").attr("src");
                }
                else{
                    alert("已经是第一张了");
                }
                // $(this).remove();
                
            }
            
            // console.log("imageContent: "+ $("#imageContent").attr("data-id"));
        });
		 if(dataId){
          	$("#imageContent").attr("data-id", dataId).attr("src",dataSrc);

            }
	});

	$("#right_arrow").click(function(){
		var dataId="", dataSrc="";
		 $("#waterfall>ul>li").each(function(){
            // console.log("data-id= "+$(this).children("a").attr("data-id"));
            if($(this).children("a").attr("data-id")==$("#imageContent").attr("data-id")){
                // console.log("target-id = "+$(this).next().children("a").attr("data-id"));
                if($(this).next().children("a").attr("data-id")){
                    dataId = $(this).next().children("a").attr("data-id");
                    dataSrc = $(this).next().children("a").children("img").attr("src");
                }
                else{
                    alert("已经是最后一张了");
                }
                // $(this).remove();
                
            }
            
            // console.log("imageContent: "+ $("#imageContent").attr("data-id"));
        });
		 if(dataId){
          	$("#imageContent").attr("data-id", dataId).attr("src",dataSrc);

            }
	});
});

