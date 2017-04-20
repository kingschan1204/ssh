package com.github.kingschan1204.ssh.controller;

import com.github.kingschan1204.ssh.model.po.SshUsersEntity;
import com.github.kingschan1204.ssh.model.vo.UserVo;
import com.github.kingschan1204.ssh.services.UserService;
import java.sql.Timestamp;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

/**
 * Created by kingschan on 2017/4/14.
 *
 */
@Controller
public class IndexController {

    private Logger log = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private UserService userServ;

    @RequestMapping("/")
    public String index(Model mo,Integer page,String username,String email) {
        log.debug("test");
        try {
          Page<UserVo> p= userServ.getUsers(null==page?1:page,10,username,email);
            mo.addAttribute("page",p);
            mo.addAttribute("username",username);
            mo.addAttribute("email",email);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "/font/index/index";
    }

    @RequestMapping("/submit")
    public String submit(@RequestParam(value="id") Integer id,
                         @RequestParam(value="username") String username,
                         @RequestParam(value="password") String password,
                         @RequestParam(value="sex") boolean sex,
                         @RequestParam(value="age") Integer age,
                         @RequestParam(value="email") String email,
                         @RequestParam(value="birthday") String birthday,
                         @RequestParam(value="remark") String remark) {

        SshUsersEntity user = new SshUsersEntity();
        user.setId(id);
        user.setAge(age);
        user.setBirthday(Timestamp.valueOf(birthday + " 00:00:00"));
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setSex(sex);
        user.setRemark(remark);
        userServ.saveUser(user);
        return "/font/index/index";
    }

    @ResponseBody
    @RequestMapping("/getUserById")
    public UserVo getUserById(@RequestParam(value = "id") Integer id) {
        UserVo vo = userServ.getUser(id);
        return vo;
    }
}
