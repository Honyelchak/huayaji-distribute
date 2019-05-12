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
            , {field: 'id', title: '电话', width: 120, sort: true, fixed: true}
            , {field: 'name', title: '昵称', width: 100, sort: true}
            , {field: 'real_name', title: '真实姓名', width: 100, sort: true, edit: true}
            /*, {field: 'address.id', title: '地址编号', style:"display:none;", width: 100, templet:'<div>{{d.address.id}}</div>'}*/
            , {field: 'province', title: '省', width: 80}
            , {field: 'city', title: '市', width: 80}
            , {field: 'county', title: '县', width: 80}
            , {field: 'detailAddress', title: '详细地址'}
            , {field: 'apartment', title: '小区名', width: 100}
            , {field: 'buildingNo', title: '单元', width: 100}
            , {field: 'houseNo', title: '房间号', width: 100}
            /*, {field: 'address.province', title: '省', width: 80, templet:'<div>{{d.address.province}}</div>'}
            , {field: 'address.city', title: '市', width: 80, templet:'<div>{{d.address.city}}</div>'}
            , {field: 'address.county', title: '县', width: 80, templet:'<div>{{d.address.county}}</div>'}
            , {field: 'address.detailAddress', title: '详细地址', width: 100, templet:'<div>{{d.address.detailAddress}}</div>'}
            , {field: 'address.apartment', title: '小区名', width: 100, templet:'<div>{{d.address.apartment}}</div>'}
            , {field: 'address.buildingNo', title: '单元', width: 100, templet:'<div>{{d.address.buildingNo}}</div>'}
            , {field: 'address.houseNo', title: '房间号', width: 100, templet:'<div>{{d.address.houseNo}}</div>'}*/
            , {field: 'sex', title: '性别', width: 70}
            , {field: 'age', title: '年龄', width: 80}
            , {field: 'service_no', title: '客服微信号', width: 100}
            , {field: 'distribute_balance', title: '余额', width: 100}
            , {field: 'note', title: '备注', width: 100}
            , {field: 'right', title: '操作', width: 177, toolbar: "#barDemo"}
        ]]
        , id: 'testReload'
        , page: true
        , height: 500
        /*, done: function(){
            $("[data-field='address.id']").hide();
        }*/
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
                var url = "/user/delete?id=" + data.id;
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
                ,content: "page/user/userUpdate.html"
                ,success: function(layero, index){
                    var body = layer.getChildFrame('body',index);
                    var p = that["data"];
                    console.log(p);
                    for(var key in p){
                        if (key == 'address') {
                            var address = p[key];
                            for(var key1 in address) {
                                body.contents().find("#" + key + "-" + key1).val(address[key1]);
                            }
                        } else {
                            body.contents().find("#" + key).val(p[key]);
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
            title : "添加用户",
            type : 2,
            content : "userAdd.html",
            success : function(layero, index){
                setTimeout(function(){
                    layui.layer.tips('点击此处返回会员列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            },
            end: function(){
                console.log("用户添加完成，表格重新加载！");
                active.reload();
            }
        })
        //改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
        $(window).resize(function(){
            layui.layer.full(index);
        })
        layui.layer.full(index);
    })

});