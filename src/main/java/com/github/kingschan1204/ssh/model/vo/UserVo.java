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
    @NotEmpty
    @Length(min = 3, max = 30)
    private String username;
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z][a-zA-Z0-9]{1,15}")
    @Length(min = 6, max = 15)
    private String password;
    @NotEmpty
    @Pattern(regexp = "['男'|'女']")
    private String sex;
    @NotNull
    @Min(1)
    @Max(100)
    private Integer age;
    @Email
    private String email;
    // 正则来验证yyyy-MM-dd格式的日期字符串
    @Pattern(regexp = "((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))")
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
