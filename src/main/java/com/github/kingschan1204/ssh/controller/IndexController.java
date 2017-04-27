package com.github.kingschan1204.ssh.controller;

import com.github.kingschan1204.ssh.model.vo.UserByPageVo;
import com.github.kingschan1204.ssh.model.vo.UserVo;
import com.github.kingschan1204.ssh.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.*;

/**
 * Created by kingschan on 2017/4/14.
 *
 */
@Controller
public class IndexController {

    private Logger log = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private UserService userServ;

    /**
     * 入口,返回表格的视图
     * @return
     */
    @RequestMapping("/")
    public String index() {
        log.debug("test");
        return "/font/index/table";
    }

    /**
     * 接收表单的数据,根据有无id来执行新增或修改
     * @param userVo
     * @return
     */
    @RequestMapping("/submit")
    @ResponseBody
    public Map submit(@Valid UserVo userVo) {
        userServ.saveUser(userVo);
        return new HashMap();
    }

    /**
     * 根据id获取一个user
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUserById")
    public UserVo getUserById(@RequestParam(value = "id") Integer id) {
        UserVo vo = userServ.getUser(id);
        return vo;
    }

    /**
     * 根据传入的id数组批量删除
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Map delete(@RequestParam(value = "ids[]") Integer[]  ids) {
        userServ.deleteByIds(ids);
        return new HashMap();
    }

    /**
     * 客户端分页
     * @param username
     * @param email
     * @return
     */
    @ResponseBody
    @RequestMapping("/findAll")
    public List<UserVo> findAll(String username,String email) {
        List<UserVo> users = userServ.getAllUsers();
        return users;
    }

    /**
     * 服务器分页
     * @param offset
     * @param limit
     * @param username
     * @param email
     * @param search
     * @param sort
     * @param order
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/server")
    public UserByPageVo serverPage(Integer offset,Integer limit,String username,String email,String search,String sort,String order) {
        Page<UserVo> p = null;
        UserByPageVo vo = new UserByPageVo();
        try{
            p= userServ.getUsers(null==offset?1:offset/limit+1,limit,username,email,sort.equals("true")?null:sort,order);
            vo.setRows(p.getContent());
            vo.setTotal(p.getTotalElements());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return vo;
    }

    @ResponseBody
    @RequestMapping("/isExist")
    public Map isExist(Integer id, String username) {
        boolean flag = userServ.usernameIsExist(id,username);
        Map vo = new HashMap();
        vo.put("valid",flag);
        return vo;
    }
}
