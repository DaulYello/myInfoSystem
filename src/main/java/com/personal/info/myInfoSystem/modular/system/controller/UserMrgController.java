package com.personal.info.myInfoSystem.modular.system.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.datascope.DataScope;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.personal.info.myInfoSystem.core.common.annotation.Permission;
import com.personal.info.myInfoSystem.core.common.constant.page.PageFactory;
import com.personal.info.myInfoSystem.core.shiro.ShiroKit;
import com.personal.info.myInfoSystem.modular.system.service.UserService;
import com.personal.info.myInfoSystem.modular.system.warpper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/mgr")
public class UserMrgController extends BaseController {

    private static final String PREFIX = "/modular/system/user/";

    @Autowired
    private UserService userService;

    /**
     * 管理员列表页面
     * @return
     */
    @RequestMapping("")
    public String index(){
        return PREFIX+"user.html";
    }

    /**
     * 管理员列表
     * @return
     */
    @RequestMapping("/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(required = false) String name,
                       @RequestParam(required = false) String timeLimt,
                       @RequestParam(required = false) Long deptId){

        String beginTime = "";
        String endTime = "";
        if (ToolUtil.isNotEmpty(timeLimt)){
            String[] dates = timeLimt.split("-");
            beginTime = dates[0];
            endTime = dates[1];
        }

        if (ShiroKit.isAdmin()){
            Page<Map<String,Object>> users = userService.selectUsers(null,name,beginTime,endTime,deptId);
            Page wrapped = new UserWrapper(users).wrap();
            return PageFactory.createPageInfo(wrapped);
        }else {
            DataScope dataScope = new DataScope(ShiroKit.getDeptDataScope());
            Page<Map<String, Object>> users = userService.selectUsers(dataScope, name, beginTime, endTime, deptId);
            Page wrapped = new UserWrapper(users).wrap();
            return PageFactory.createPageInfo(wrapped);
        }
    }


}
