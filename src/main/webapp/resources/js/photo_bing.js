$(document).ready(function(){

    var imageId = $("#imageContent").attr("data-id");

    $("#like").click(function(){
        $.ajax({
           type: "put",
           url: "/image/favor",
           data: {
            imageId: imageId
           },
           dataType: "json",
           success: function(data, textStatus, jqXHR){
                console.log("status" + textStatus);
                $("#like").html("已喜欢");
           },
           error: function(XMLHttpRequest, textStatus, errorThrown){
                console.log("XMLHttpRequest: "+XMLHttpRequest+" textStatus: "+textStatus+" errorThrown: "+errorThrown);
           }
        });
    });

    $("#remove").click(function(){
        $.ajax({
            type: "post",
            url: "/image/remove",
            data: {
                id: imageId
            },
            success: function(data, textStatus, jqXHR){
                // console.log("status "+ textStatus);
                // console.log(data);
                // for(var attr in data ){
                //     console.log(attr+": "+data[i]);
                // }
                $("#waterfall>ul>li").each(function(){
                    console.log("data-id= "+$(this).children("a").attr("data-id"));
                    if($(this).children("a").attr("data-id")==$("#imageContent").attr("data-id")){
                        console.log("target-id = "+$(this).next().children("a").attr("data-id"));
                        if($(this).next().children("a").attr("data-id")){
                            var dataId = $(this).next().children("a").attr("data-id");
                        }
                        else{
                            var dataId = $(this).prev().children("a").attr("data-id");
                        }
                        if($(this).next().children("a").children("img").attr("src")){
                            var dataSrc = $(this).next().children("a").children("img").attr("src");
                        }
                        else{
                            var dataSrc = $(this).prev().children("a").children("img").attr("src");
                        }
                        // $(this).remove();
                        
                    }
                    $("#imageContent").attr("data-id", dataId).attr("src",dataSrc);
                    console.log("imageContent: "+ $("#imageContent").attr("data-id"));
                });
                
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                console.log("XMLHttpRequest: "+XMLHttpRequest+" textStatus: "+textStatus+" errorThrown: "+errorThrown);
            }
        });
    });

    $("#submitCollectForm").submit(function(e){

		stopDefault(e);

        $.ajax({
            type: "post",
            url: "/image/collect/" + imageId,
            data: $("#submitCollectForm").serialize(),
            success: function(data, textStatus, jqXHR){
                console.log("status "+ textStatus);
                $("#submitCollectForm").modal('hide');
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                console.log("XMLHttpRequest: "+XMLHttpRequest+" textStatus: "+textStatus+" errorThrown: "+errorThrown);
            }
        });

    });

    $("#changeAlbumForm").submit(function(e){
		stopDefault(e);
        $.ajax({
            type: "put",
            url: "/image/change/album/" + imageId,
            data: $("#changeAlbumForm").serialize(),
            success: function(data, textStatus, jqXHR){
                console.log("status "+ textStatus);
                $("#changeAlbumForm").modal('hide');
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                console.log("XMLHttpRequest: "+XMLHttpRequest+" textStatus: "+textStatus+" errorThrown: "+errorThrown);
            }
        });

    });
});

//阻止默认行为 stopDefault( e )
function stopDefault( e ) {
     if ( e && e.preventDefault )
        e.preventDefault();
    else
        window.event.returnValue = false;

    return false;
}
