layui.use(['form','layer','jquery','laypage','table'],function() {
    var table = layui.table;
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        laypage = layui.laypage,
        $ = layui.jquery;

    table.render({
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
            , {field: 'distribute_balance', title: '余额', width: 100}
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
            form.val("content1", data);
            active.update(data);
            layer.msg('ID：' + data.id + ' 的查看操作');
        } else if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                obj.del();
                layer.close(index);
            });
        } else if (obj.event === 'edit') {
            layer.alert('编辑行：<br>' + JSON.stringify(data))
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
                ,content: "page/news/newsUpdate.html"
                ,success: function(layero, index){
                    var body = layer.getChildFrame('body',index);
                    //console.log(body);
                    console.log(layero);
                    var iframeWin = window[layero.find('iframe')[0]['name']];
                    iframeWin.input(that[data]);
                }
            });
        }
    };

    $('.demoTable .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
});