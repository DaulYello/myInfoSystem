package com.personal.info.myInfoSystem.modular.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mrg")
public class UserMrgController {

    private static final String PREFIX = "/modular/system/user";

    @RequestMapping("")
    public String index(){
        return PREFIX+"/user.html";
    }
}
