
$(window).ready(function(){
	//瀑布流列表自适应居中
	var item_list=document.getElementById('waterfall').getElementsByTagName('ul')[0];
	waterfall(item_list, 'li',0);
	setContainerWidth(item_list, 220, 1200);
});

$(window).resize(function(){	//改变窗口大小时，瀑布流列表自适应居中
	var item_list=document.getElementById('waterfall').getElementsByTagName('ul')[0];
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