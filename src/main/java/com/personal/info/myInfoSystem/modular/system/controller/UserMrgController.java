package com.personal.info.myInfoSystem.modular.system.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mgr")
public class UserMrgController extends BaseController {

    private static final String PREFIX = "/modular/system/user/";

    /**
     * 管理员列表
     * @return
     */
    @RequestMapping("")
    public String index(){
        return PREFIX+"user.html";
    }
}
