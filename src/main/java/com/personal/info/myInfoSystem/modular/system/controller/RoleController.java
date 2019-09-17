package com.personal.info.myInfoSystem.modular.system.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.personal.info.myInfoSystem.core.common.annotation.Permission;
import com.personal.info.myInfoSystem.core.common.constant.Const;
import com.personal.info.myInfoSystem.core.common.constant.page.PageFactory;
import com.personal.info.myInfoSystem.core.common.exception.BizExceptionEnum;
import com.personal.info.myInfoSystem.modular.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    private static final String PREFIX = "/modular/system/role/";

    @Autowired
    private RoleService roleService;

    @RequestMapping("")
    public String index(){
        return PREFIX+"role.html";
    }

    @Permission
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(value = "roleName",required = false) String roleName){
        Page<Map<String,Object>> roles = this.roleService.list(roleName);
        return PageFactory.createPageInfo(roles);
    }

    @Permission
    @RequestMapping("/role_assign/{roleId}")
    public Object roleAssign(@PathVariable("roleId") Long roleId, Model model){

        if (ToolUtil.isEmpty(roleId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        model.addAttribute("roleId",roleId);
        return PREFIX+"role_assign.html";
    }

    @Permission(Const.ADMIN_NAME)
    @RequestMapping("/setAuthority")
    @ResponseBody
    public ResponseData setAuthority(@RequestParam("roleId") Long roleId,@RequestParam("ids") String ids){

        if (ToolUtil.isOneEmpty(roleId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.roleService.setAuthority(roleId,ids);
        return SUCCESS_TIP;
    }
}
