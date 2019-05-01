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
    form.on("submit(addProduct)",function(data){
        /*console.log($("#phone").val());
        $("#user_id").val($("#phone").val());*/
        //var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            url:'/product/add',
            type: "post",
            dataType: "json",//预期服务器返回的数据类型
            data: $('#add_form').serialize(),
            success: function(res) {
                console.log(res);
                if(res["res"]=='ok'){
                    top.layer.msg("产品添加成功！");
                    //layer.msg("修改成功！",{time:3000});
                    parent.$("#handle_status").val("ok");　　//给A页的 id=handle_status 的元素赋值
                    console.log(parent);
                    parent.$(".layui-layer-close1").trigger('click'); //选中A页关闭iframe窗口
                }else{
                    top.layer.msg("产品添加失败！");
                    parent.$("#handle_status").val("no");
                    //layer.msg("修改失败！",{time:3000});
                }
                //top.layer.close(index);
                layer.closeAll("iframe");

                parent.location.reload();
            },
            error : function(e) {
                console.log(e);
            }
        });

        return false;
    })

})
