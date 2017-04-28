
/**
 * 搜索条件框事件
 */
$("#toolbar input").keydown(function(){
    // 当按下回车键时
    if(event.keyCode == 13){
        // 重新加载
        reload();
    }
});
/**
 * 显示载入中的图片
 * @param msg 提示信息
 */
function showLoading(msg) {
    swal({
        title: '',
        text: msg,
        width: '300px',
        imageUrl: '/www/showloading/loading.gif',
        showConfirmButton: false,
        showLoaderOnConfirm: true,
        allowOutsideClick: false
    })
}

/**
 * 关闭载入中图片
 */
function closeLoading() {
    swal.close();
}

// 提交按钮事件
$("#sure").click(function() {
    //获取表单对象
    var $bootstrapValidator = $("#form").data('bootstrapValidator');
    //手动触发验证
    $bootstrapValidator.validate();
    // 如果验证未通过,则不继续提交
    if (!$bootstrapValidator.isValid()) {
        return;
    }

    showLoading("正在提交");
    $.ajax({
        type: "POST",
        url: "submit",
        data: $("#form").serialize(),
        success: function(data) {

            closeLoading();
            $("#saveOrUpdateModal").modal('hide');
            $.notify({
                message: '提交成功'
            },{
                type: 'success',
                placement: {
                    from: "top",
                    align: "center"
                }
            });
            reload();
        },
        error: function (data) {
                swal('提交失败', '请确认网络是否通畅', 'error');
        }
    });
});

function reload() {
    // 刷新网页
    //$('#table').bootstrapTable('load', "data");
    // 表格自带的刷新方法
    $('#table').bootstrapTable('refresh',{url: "/server"});
}

// 新增按钮点击事件
$("#saveButton").click(function() {
    // 重置表单数据
    formReset();
    // 展示模态框
    $("#saveOrUpdateModal").modal('show');
});

// 修改按钮点击事件
$("#updateButton").click(function() {
    // 先判断当前选中的用户条数是否有且只有一条
    if ($("td input[type='checkbox']:checked").length != 1) {
        swal('操作失败!', '选择一条需要修改的行', 'error');
        return;
    }

    // 根据选中的id异步请求获得用户数据
    var $id = $("td input[type='checkbox']:checked").parent().next().html();
    showLoading();

    $.ajax({
        type : "POST",
        url : "getUserById",
        dataType : 'json',
        data : {"id" : $id},
        success : function(data) {
            // 重置验证器
            resetValidator();
            // 填充数据
            $("#id").val(data.id);
            $("#username").val(data.username);
            $("#password").val(data.password);
            $("#age").val(data.age);
            $("#email").val(data.email);
            $("#birthday").val(data.birthday);
            $("#remark").val(data.remark);
            closeLoading();
            // 展示模态框
            $("#saveOrUpdateModal").modal('show');
        }
    });
});

// 删除按钮点击事件
$("#deleteButton").click(function() {
    // 判断是否有选中的用户信息
    if ($("input[type='checkbox']:checked").length != 0) {
        swal({
            title : '确定要删除吗?',
            text : "删除是不可逆操作!",
            type : 'warning',
            showCancelButton : true,
            confirmButtonColor : '#3085d6',
            cancelButtonColor : '#d33',
            confirmButtonText : '确定',
            cancelButtonText : '取消'
        }).then(function() {
            var ids= new Array();
            $("td input:checkbox:checked").each(function(i) {
                    var itemId=$(this).parent().next().html();
                    ids[i]=itemId;
            });
            //ids=ids;
            //debugger;
            // 删除的方法
            $.ajax({
                type : "POST",
                url : "delete",
                data : {ids : ids},
                success : function(data) {
                    //swal('操作成功!', '以删除选中的行', 'success');
                    // setTimeout("reload()",1000);
                    $.notify({
                        message: '删除成功'
                    },{
                        type: 'success',
                        placement: {
                            from: "top",
                            align: "center"
                        },
                    });
                    reload();
                },
                error : function(data) {
                    swal('操作失败!', '请检测网络', 'error');
                }
            });

        });
    } else {
        swal('操作失败!', '先选择需要删除的行', 'error');
    }
});

// 重置表单的方法
function formReset() {

     /*----------注意!这样reset表单是不会重置隐藏字段的!!----------*/
    $('#form')[0].reset();

    // 手动重置
    $("#id").val("");

    // 重置验证器
    resetValidator();
}

function resetValidator() {
    /*-------------------重置表单所有验证规则--------------------*/
    // 获取验证器
    var validator = $('#form').data("bootstrapValidator");
    // 先销毁验证器
    validator.destroy();
    // 再重新初始化
    initValidator();
}


/**
 * 表单验证
 */
function initValidator(){

    $("#form").bootstrapValidator({
        feedbackIcons : {
            valid : 'glyphicon glyphicon-ok',
            invalid : 'glyphicon glyphicon-remove',
            validating : 'glyphicon glyphicon-refresh'
        },
        submitButtons : '#sure',
        fields : {
            // 多个重复
            username : {
                trigger : "blur",   // 设置失去焦点时才验证
                message: 'The username is not valid',
                validators: {
                    notEmpty: {/*非空提示*/
                        message: '用户名不能为空'
                    },
                    stringLength: {/*长度提示*/
                        min: 3,
                        max: 20,
                        message: '用户名长度必须在3到20之间'
                    },
                    // 实时验证用户名是否存在
                    /*remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                        url: '/isExist',//验证地址
                        message: '用户名已存在',//提示消息
                        // delay : 100,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置一秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                        type: 'POST',//请求方式
                        //自定义提交数据，默认值提交当前input value
                        data: function(validator) {
                            return {
                                id: $('#id').val(),
                                username: $('#username').val()
                            };
                        }
                    }*/
                }
            },
            password : {
                message:'密码无效',
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z][a-zA-Z0-9]{1,15}$/,
                        message: '必须以英文字母开头,且只能包含英文字母和数字'
                    },
                    stringLength: {
                        min: 6,
                        max: 15,
                        message: '密码长度必须为6到15位之间'
                    }
                }
            },
            age : {
                validators : {
                    message:'年龄无效',
                    notEmpty : {
                        message : '年龄不能为空'
                    },
                    between : {
                        min : 1,
                        max : 120,
                        message : '1-120之间'
                    }
                }
            },
            sex : {
                validators : {
                    message:'',
                    notEmpty : {
                        message : ''
                    },
                }
            },
            birthday : {
                validators : {
                    notEmpty: {
                        message: '生日必填'
                    },
                    date : {
                        min : '1897-4-13',
                        message : '生日有误,请重新选择'
                    }
                }
            },
            email : {
                validators : {
                    notEmpty : {
                        message : '邮箱不能为空'
                    },
                    emailAddress : {
                        message : '邮箱地址有误'
                    }
                }
            }
        }
    });

}

/**
 * table的额外设置
 */
$('#table').bootstrapTable({
    url: "/server",                     //请求后台的URL（*）
    queryParams: function (params) { //重写提交的数据
        return {
            // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit, // 页面大小
            offset: params.offset, // 页码数 * 页面大小
            order: params.order, // 排序方式
            sort: params.sort, //排序的字段
            username: $("#usernameSearchInput").val(),
            email: $("#emailInput").val()
        };
    },

    resizable: true,  //启动表格可拖动调整列宽效果,需要bootstrap-table-resizable插件已经其依赖的colResizable-1.5插件
    liveDrag: true,   // 表格拖动时的随动效果
    headerOnly: true  // 设定拖动表头操作,能优化点击选行的体验
});


//初始化
$(document).ready(function() {
    initValidator();
});