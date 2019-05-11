layui.config({
	base : "js/"
}).use(['form','layer','jquery','layedit','laydate'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		layedit = layui.layedit,
		laydate = layui.laydate,
		$ = layui.jquery;

	//创建一个编辑器
 	var editIndex = layedit.build('news_content');
 	var addNewsArray = [],addNews;
 	form.on("submit(addNews)",function(data){
 		//是否添加过信息
	 	if(window.sessionStorage.getItem("addNews")){
	 		addNewsArray = JSON.parse(window.sessionStorage.getItem("addNews"));
	 	}
	 	//显示、审核状态
 		// var isShow = data.field.show=="on" ? "checked" : "",
 		// 	newsStatus = data.field.shenhe=="on" ? "审核通过" : "待审核";

 		addNews = '{"user_id":"'+$("#phone").val()+'",';
 		addNews += '"product_id":"'+$("#productType").val()+'",';
 		addNews += '"totalMoney":"'+$("#totalMoney").val()+'",';
 		addNews += '"count":"'+$("#count").val()+'",';
 		addNews += '"orderTime":"'+$(".orderTime").val()+'",';
        addNews += '"distributeTime":"'+$(".distributeTime").val()+'",';
        addNews += '"distributeType":"'+$(".distributeType").val() +'"}';
 		addNewsArray.unshift(JSON.parse(addNews));
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        window.sessionStorage.setItem("addOrder",JSON.stringify(addNewsArray));

        $.ajax({
            type: "get",
            url: "/order/add",
            data:{"userId":$("#phone").val(),"productId":$('#productType option:selected') .val(),"totalMoney":$("#totalMoney").val(),"count":$("#count").val(),"orderTime":$("#orderTime").val(),"distributeTime":$("#distributeTime").val(),"distributeType":$('#distributeType option:selected') .val()},
            dataType: "json",
            async: false,
            success: function (data) {
				if(data.code=="0"){
                setTimeout(function(){
                    top.layer.close(index);
                    top.layer.msg("添加成功！");
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                },2000);
                }
                else{
                    setTimeout(function(){
                        top.layer.close(index);
                        top.layer.msg("添加失败！");
                        layer.closeAll("iframe");
                        //刷新父页面
                        parent.location.reload();
                    },2000);
				}
            }
        });

 		//弹出loading


 		return false;
 	})
	
})

function change() {
	var phone =document.getElementById("phone").value;

	console.log(phone);
    $.ajax({
        type: "get",
        url: "/user/getUserById",
        data: {"phone":phone},
        dataType: "json",
        async: false,
        success: function (data) {
            top.layer.msg(data.msg);
        }
    });

}

$(document).ready(function (){

    $.ajax({
        type: "get",
        url: "/product/getAll",
        data: null,
        dataType: "json",
        async: false,
        success: function (data) {
            var list=data.data;
            var select =document.getElementById("productType");
            for (var i=0;i<list.length;i++)
			{
				var option=document.createElement("option");
				option.setAttribute("value",list[i].id);
                option.innerHTML=list[i].name;
                select.appendChild(option);
			}
        }
    });

});
