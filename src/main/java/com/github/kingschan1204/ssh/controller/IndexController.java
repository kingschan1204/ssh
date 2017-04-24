package com.github.kingschan1204.ssh.controller;

import com.github.kingschan1204.ssh.model.po.SshUsersEntity;
import com.github.kingschan1204.ssh.model.vo.UserVo;
import com.github.kingschan1204.ssh.services.UserService;
import java.sql.Timestamp;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ResponseBody
    public Map submit(UserVo userVo) {

        /*SshUsersEntity user = new SshUsersEntity();
        user.setId(id);
        user.setAge(age);
        user.setBirthday(Timestamp.valueOf(birthday + " 00:00:00"));
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setSex(sex);
        user.setRemark(remark);*/
        userVo=userVo;

        userServ.saveUser(userVo);
        return new HashMap();
    }

    @ResponseBody
    @RequestMapping("/getUserById")
    public UserVo getUserById(@RequestParam(value = "id") Integer id) {
        UserVo vo = userServ.getUser(id);
        return vo;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Map delete(@RequestParam(value = "ids[]") Integer[]  ids) {
        userServ.deleteByIds(ids);
        return new HashMap();
    }
}
