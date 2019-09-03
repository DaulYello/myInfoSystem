package com.personal.info.myInfoSystem.modular.system.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 通用控制器
 *
 */
@Controller
@RequestMapping("/system")
@Slf4j
public class SystemController {

    @RequestMapping("/console")
    public String console(){
        return "/modular/frame/console.html";
    }
}
