package com.github.kingschan1204.ssh.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by kingschan on 2017/4/14.
 */
@Controller
public class IndexController {

    private Logger log = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/")
    public String index(Model mo) {
        log.debug("test");
        mo.addAttribute("name","kingschan");
        return "/index/index";
    }
}
