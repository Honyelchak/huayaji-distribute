layui.use(['form','layer','jquery','laypage','table'],function() {
    var table = layui.table;
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;

    var tableInstance = table.render({
        elem: '#lay_table_news'
        , url: '/product/getAll'
        , cols: [[
            {checkbox: true, fixed: true}
            , {field: 'id', title: '产品编号', width: 120, sort: true, fixed: true}
            , {field: 'type', title: '产品类型', width: 120, sort: true, edit: true}
            , {field: 'name', title: '产品名称', width: 120, sort: true}
            , {field: 'price', title: '产品单价(元)', width: 160}
            , {field: 'distributeType', title: '配送类型', width: 160}
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
                var url = "/product/delete?id=" + data.id;
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
        ,reload: function(){
            console.log("success reload");
            table.reload('testReload');
        }
        ,update: function(data){
            var that = this;
            that["data"] = data;
            //that.data = data;
            layer.open({
                type: 2 //此处以iframe举例
                ,title: '修改产品信息'
                ,area: ['600px', '500px']
                ,shade: 0.5
                ,maxmin: true
                ,data1:data
                ,content: "page/product/productUpdate.html"
                ,success: function(layero, index){
                    var body = layer.getChildFrame('body',index);
                    var p = that["data"];
                    console.log(p);
                    for(var key in p){
                        body.contents().find("#" + key).val(p[key]);
                    }
                }
                ,end: function () {
                    var handle_status = $("#handle_status").val();
                    console.log($("#handle_status").val());
                    active.reload();
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
        },
        delete: function(id){

        }
    };

    $('.demoTable .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    //添加会员
    $(".newsAdd_btn").click(function(){
        var index = layui.layer.open({
            title : "添加产品",
            type : 2,
            content : "productAdd.html",
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

});