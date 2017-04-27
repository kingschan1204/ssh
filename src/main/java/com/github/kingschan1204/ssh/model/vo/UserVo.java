package com.github.kingschan1204.ssh.model.vo;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
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
    private String sex;
    @Min(1)
    @Max(100)
    private Integer age;
    @Email
    private String email;
    @Past
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
