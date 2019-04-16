layui.use(['form','layer','jquery','laypage','table'],function() {
    var table = layui.table;
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;

    var tableInstance = table.render({
        elem: '#lay_table_news'
        , url: '/order/getAll'
        , cols: [[
            {checkbox: true, fixed: true}
            , {field: 'id', title: 'ID', width: 80, sort: true, fixed: true}
            , {field: 'user.name', title: '昵称', width: 80, sort: true}
            , {field: 'product.name', title: '产品', width: 80, sort: true, edit: true}
            , {field: 'address.province', title: '省', width: 180}
            , {field: 'address.province', title: '市', width: 180}
            , {field: 'address.province', title: '县（区）', width: 180}
            , {field: 'totalMoney', title: '总金额', width: 100}
            , {field: 'orderTime', title: '下单时间', width: 100}
            , {field: 'distributeTime', title: '首次配送时间', width: 100}
            , {field: 'distributeType', title: '配送类型', width: 100}
            , {field: 'count', title: '数量', width: 100}
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
            layer.confirm('真的删除行么', function (index) {
                obj.del();
                layer.close(index);
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
                ,content: "page/news/userUpdate.html"
                ,success: function(layero, index){
                    var body = layer.getChildFrame('body',index);
                    var p = that["data"];
                    for(var key in p){
                        body.contents().find("#" + key).val(p[key]);
                    }
                }
                ,end: function () {
                    var handle_status = $("#handle_status").val();
                    console.log($("#handle_status").val());
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