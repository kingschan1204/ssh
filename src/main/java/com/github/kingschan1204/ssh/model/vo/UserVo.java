package com.github.kingschan1204.ssh.model.vo;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.*;
import java.sql.Timestamp;

/**
 * Created by kingschan on 2017/4/18.
 */
public class UserVo {

    private Integer id;
    @NotEmpty(message = "用户编号不能为空")
    @Length(min = 3, max = 30,message = "用户名长度应在3到30位")
    private String username;
    @NotEmpty(message = "密码不能为空")
    @Pattern(regexp = "[a-zA-Z][a-zA-Z0-9]{1,15}",message = "密码只能包括数字和英文字母")
    @Length(min = 6, max = 15,message = "密码长度应在6到15位")
    private String password;
    @NotEmpty(message = "未选择性别")
    @Pattern(regexp = "['男'|'女']",message = "提交信息有误,性别只能为\"男\"或\"女\"")
    private String sex;
    @NotNull(message = "年纪不能为空")
    @Min(value = 1,message = "年纪应在1-120内")
    @Max(value = 120,message = "年纪应在1-120内")
    private Integer age;
    @Email(message = "邮箱格式验证不通过")
    private String email;
    // 正则来验证yyyy-MM-dd格式的日期字符串
    @Pattern(message = "生日验证不通过",regexp = "((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))")
    private String birthday;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public  String getSex() { return sex;}

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    private String remark;

}
