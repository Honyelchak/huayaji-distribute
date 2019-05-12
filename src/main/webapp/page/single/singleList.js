layui.use(['form','layer','jquery','laypage','table'],function() {
    var table = layui.table;
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;
//添加会员
    $(".newsAdd_btn").click(function(){
        var index = layui.layer.open({
            title : "添加会员",
            type : 2,
            content : "singleAdd.html",
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回会员列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function(){
            layui.layer.full(index);
        })
        layui.layer.full(index);
    })

    var tableInstance = table.render({
        elem: '#lay_table_news'
        , url: '/single/getAll'
        , cols: [[
            {checkbox: true, fixed: true}
            , {field: 'id', title: 'ID', width: 80, sort: true, fixed: true ,edit: false,}
            , {field: 'user.name', title: '昵称', width: 80, sort: true, templet: '<div>{{d.user.name}}</div>'}
            , {field: 'product.name', title: '产品', width: 80, sort: true, edit: false, templet: '<div>{{d.product.name}}</div>'}
            , {field: 'distribute_time', title: '客户待配送日期', width: 200,  templet: "<div>{{layui.util.toDateString(d.ordertime, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
            , {field: 'distribute_data', title: '待配送数量', width: 150, }
            , {field: 'distribute_operation', title: '配送员配送操作', width: 100}
            , {field: 'receive_operation', title: '客户收货操作', width: 100}
            , {field: 'distribute_status', title: '配送状态', width: 150,templet:'<div>{{   isYes(d.distributeType) }}</div>'}
            , {field: 'right', title: '操作', width: 177, toolbar: "#barDemo"}
        ]]
        , id: 'testReload'
        , page: true
        , height: 600
    });

    table.on('checkbox(demo)', function (obj) {
        console.log(obj);
    });

    table.on('tool(demo)', function (obj) {
        var data = obj.data;
        if (obj.event === 'detail') {
            layer.alert('查看行：<br>' + JSON.stringify(data))
        } else if (obj.event === 'del') {
            layer.confirm('真的删除行么',  {btn: ['确定', '取消'], title: "提示"},function (index) {
                active.delete(data["id"]);
                obj.del();
                layer.close(index);
            });
            layer.confirm('真的删除行么', {btn: ['确定', '取消'], title: "提示"}, function () {
                var url = "/single/delete?id=" + data.id;
                $.ajax({
                    type: "get",
                    url: url,
                    data: null,
                    dataType: "json",
                    async: false,
                    success: function (data) {
                        if (data.code == '0') {
                            layer.msg('操作成功', {icon: 1});
                            active.reload();
                            /*window.setTimeout("javascript:location.href='/new'", 2000);*/
                        } else {
                            layer.msg(data.msg, {icon: 2});
                        }
                    }
                });
            });
        } else if (obj.event === 'edit') {
            active.update(data);
        }
    });



    var $ = layui.$, active = {
        getCheckData: function(){ //获取选中数据
            var checkStatus = table.checkStatus('idTest')
                ,data = checkStatus.data;
            layer.alert(JSON.stringify(data));
        }
        ,getCheckLength: function(){ //获取选中数目
            var checkStatus = table.checkStatus('idTest')
                ,data = checkStatus.data;
            layer.msg('选中了：'+ data.length + ' 个');
        }
        ,isAll: function(){ //验证是否全选
            var checkStatus = table.checkStatus('idTest');
            layer.msg(checkStatus.isAll ? '全选': '未全选')
        }
        ,reload: function () {
            console.log("success reload");
            table.reload('testReload');
        }
        ,update: function(data){
            var that = this;
            that["data"] = data;
            //that.data = data;
            layer.open({
                type: 2 //此处以iframe举例
                ,title: '修改用户信息'
                ,area: ['600px', '500px']
                ,shade: 0.5
                ,maxmin: true
                ,data1:data
                ,content: "page/single/singleUpdate.html"
                ,success: function(layero, index){
                    var body = layer.getChildFrame('body',index);
                    var p = that["data"];
                    for(var key in p){
                        if(key=="user")
                        {
                            body.contents().find("#name").val(p[key].name);
                        }
                        if(key=="product")
                        {
                            body.contents().find("#productName").val(p[key].name);
                        }
                        if(key=="address")
                        {
                            console.log(p[key].province+p[key].city+p[key].county);
                            body.contents().find("#addressProvince").val(p[key].province);
                            body.contents().find("#addressCity").val(p[key].city);
                            body.contents().find("#addressCounty").val(p[key].county);
                            body.contents().find("#detailAddress").val(p[key].detailAddress);

                        }
                        else{
                            body.contents().find("#"+key).val(p[key]);
                        }

                    }
                }
                ,end: function () {
                    var handle_status = $("#handle_status").val();
                    console.log($("#handle_status").val());
                    active.reload();
                    console.log("hello!");
                    if ( handle_status == 'ok' ) {
                        layer.msg('添加成功！',{
                            icon: 1,
                            time: 1000
                        });
                        $("#handle_status").val('');
                    } else {
                        if(handle_status.length > 0){　　//防止关闭窗口报错
                            layer.msg(handle_status,{
                                icon: 2,
                                time: 2000 //2秒关闭（如果不配置，默认是3秒）
                            });
                            $("#handle_status").val('');
                        }
                    }
                }
            });
        }
    };

    $('.demoTable .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
});
function  isYes(yes) {
    if(yes == 1 ){
        return "正在配送配送";
    }else
        if(yes==2) {
            return "已配送";
        }
        else{
        return "代配送";
    }
}