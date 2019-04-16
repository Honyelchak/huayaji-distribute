layui.use(['form','layer','jquery','laypage','table'],function() {
    var table = layui.table;
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;

    var tableInstance = table.render({
        elem: '#lay_table_news'
        , url: '/user/getAll'
        , cols: [[
            {checkbox: true, fixed: true}
            , {field: 'id', title: 'ID', width: 80, sort: true, fixed: true}
            , {field: 'name', title: '昵称', width: 80, sort: true}
            , {field: 'real_name', title: '真实姓名', width: 80, sort: true, edit: true}
            , {field: 'phone', title: '电话', width: 180}
            , {field: 'sex', title: '性别', width: 100}
            , {field: 'age', title: '年龄', width: 100}
            , {field: 'service_no', title: '客服微信号', width: 100}
            , {field: 'distribute_balance', title: '余额', width: 100}
            , {field: 'address.id', title: '余额', width: 100}
            , {field: 'address.province', title: '余额', width: 100}
            , {field: 'note', title: '备注', width: 100}
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
                var price = $("#price").val();
                if (price == null || price == undefined) {
                    layer.msg('请输入金额', {icon: 2});
                }
                var url = "${ctx }/manage/member/Back.do?museId=" + museId + "&price=" + price;
                $.ajax({
                    type: "post",
                    url: url,
                    data: null,
                    dataType: "json",
                    async: false,
                    success: function (data) {
                        if (data.flag == 1) {
                            layer.msg('操作成功', {icon: 1});
                            window.setTimeout("javascript:location.href='${ctx }/manage/member/toPage.do'", 2000);
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
        },
        delete: function(id){

        }
    };

    $('.demoTable .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
});