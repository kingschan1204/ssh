
// 确定绑定完成后再绑定事件
onload = function(){


// 提交按钮事件
$("#sure").click(function() {
    $.ajax({
        type: "POST",
        url: "submit",
        dataType: "json",
        data: $("#form").serialize(),
        success: function(data) {
            swal('提交成功', '', 'success');
            setTimeout("reload()",1000);
        },
        error: function (data) {
            swal('提交失败', '请确认网络是否通畅', 'error');
        }
    });
});

function reload() {
    // 刷新网页
    window.location.href='';
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
    if ($("input[type='checkbox']:checked").length != 1) {
        swal('操作失败!', '选择一条需要修改的行', 'error');
        return;
    }
    // 根据选中的id异步请求获得用户数据
    var $id = $("input[type='checkbox']:checked").val();
    $.ajax({
        type : "POST",
        url : "getUserById",
        dataType : 'json',
        data : {"id" : $id},
        success : function(data) {
            // 填充数据
            $("#id").val(data.id);
            $("#username").val(data.username);
            $("#password").val(data.password);
            $("input:radio[name=sex]")[0].checked = data.age;
            $("input:radio[name=sex]")[1].checked = !data.age;
            $("#age").val(data.age);
            $("#email").val(data.email);
            $("#birthday").val(data.birthday);
            debugger;
            $("#remark").val(data.remark);
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
            $("input:checkbox:checked").each(function(i,k){
                var itemId=$(this).val();
                ids[i]=itemId;
            });
            ids = ids;
            debugger;
            // 删除的方法
            $.ajax({
                type : "POST",
                url : "delete",
                dataType : 'json',
                data : {ids : ids},
                success : function(data) {
                    swal('操作成功!', '以删除选中的行', 'success');
                    setTimeout("reload()",1000);
                }
            });

        });
    } else {
        swal('操作失败!', '先选择需要删除的行', 'error');
    }
})

// 重置表单的方法
function formReset() {
    /*$("#id").val("");
     $("#username").val("");
     $("#password").val("");
     $("input:radio[name=sex]")[0].checked = true;
     $("#age").val("");
     $("#email").val("");
     $("#birthday").val("");
     $("#remark").val("");*/

    /*

     注意!这样reset表单是不会重置隐藏字段的!!

     */
    $('#form')[0].reset();

    //alert("隐藏字段id="+$("#id").val());
    // 手动重置
    $("#id").val("");
}

/**
 * 表单验证
 */
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
            message: 'The username is not valid',
            validators: {
                notEmpty: {/*非空提示*/
                    message: '用户名不能为空'
                },
                stringLength: {/*长度提示*/
                    min: 6,
                    max: 30,
                    message: '用户名长度必须在6到30之间'
                }
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
        birthday : {
            validators : {
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