layui.config({
	base : "js/"
}).use(['form','layer'],function(){
		var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		$ = layui.jquery;
	//video背景
	$(window).resize(function(){
		if($(".video-player").width() > $(window).width()){
			$(".video-player").css({"height":$(window).height(),"width":"auto","left":-($(".video-player").width()-$(window).width())/2});
		}else{
			$(".video-player").css({"width":$(window).width(),"height":"auto","left":-($(".video-player").width()-$(window).width())/2});
		}
	}).resize();
	
	//登录按钮事件
    $("body").on('click','#submit-btn',function(){
        $.ajax({
            url:'/admin/login',
            type: "post",
            dataType: "json",//预期服务器返回的数据类型
            contentType : "application/x-www-form-urlencoded",
            /*contentType: 'application/json',*/
            /*data: JSON.stringify(data1),*/
            data: $('#form-login').serialize(),
            success: function(res) {
                if(res["code"]=='0'){
                    layer.msg("登录成功！",{time:3000});
                    //window.location.href = "https://www.baidu.com";
                    window.location.href = "../../index.html";
                }else{
                    layer.msg("登录失败！",{time:3000});
                }
            },
            error : function(e) {
                console.log(e);
            }
        });
		return false;
	});
});
