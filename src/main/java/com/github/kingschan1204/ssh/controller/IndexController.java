package com.github.kingschan1204.ssh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kingschan1204.ssh.model.vo.UserVo;
import com.github.kingschan1204.ssh.services.UserService;

import java.io.IOException;
import java.io.PrintWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
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
        /*log.debug("test");
        try {
          Page<UserVo> p= userServ.getUsers(null==page?1:page,10,username,email);
            mo.addAttribute("page",p);
            mo.addAttribute("username",username);
            mo.addAttribute("email",email);
        }catch (Exception ex){
            ex.printStackTrace();
        }
       return "/font/index/index";*/
        return "/font/index/table";
    }

    @RequestMapping("/submit")
    @ResponseBody
    public Map submit(UserVo userVo) {
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

    @ResponseBody
    @RequestMapping("/findAll")
    public List<UserVo> findAll(String username,String email) {
        List<UserVo> users = userServ.getAllUsers(username,email);
        return users;
    }
}
