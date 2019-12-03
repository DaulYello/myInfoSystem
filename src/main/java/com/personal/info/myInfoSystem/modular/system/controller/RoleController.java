package com.personal.info.myInfoSystem.modular.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.personal.info.myInfoSystem.core.common.annotation.BussinessLog;
import com.personal.info.myInfoSystem.core.common.annotation.Permission;
import com.personal.info.myInfoSystem.core.common.constant.Const;
import com.personal.info.myInfoSystem.core.common.constant.dictmap.RoleDict;
import com.personal.info.myInfoSystem.core.common.constant.factory.ConstantFactory;
import com.personal.info.myInfoSystem.core.common.constant.page.PageFactory;
import com.personal.info.myInfoSystem.core.common.exception.BizExceptionEnum;
import com.personal.info.myInfoSystem.core.log.LogObjectHolder;
import com.personal.info.myInfoSystem.core.node.ZTreeNode;
import com.personal.info.myInfoSystem.core.shiro.ShiroKit;
import com.personal.info.myInfoSystem.modular.system.entity.Role;
import com.personal.info.myInfoSystem.modular.system.entity.User;
import com.personal.info.myInfoSystem.modular.system.service.RoleService;
import com.personal.info.myInfoSystem.modular.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    private static final String PREFIX = "/modular/system/role/";

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @RequestMapping("")
    public String index(){
        return PREFIX+"role.html";
    }

    @Permission(Const.ADMIN_NAME)
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

    @Permission(value = Const.ADMIN_NAME)
    @RequestMapping("/role_add")
    public String role_add(){
        return PREFIX+"role_add.html";
    }

    @RequestMapping("/add")
    @BussinessLog(value = "添加角色",key = "name",dict = RoleDict.class)
    @ResponseBody
    public ResponseData add(@Valid Role role, BindingResult result){

        if (result.hasErrors()){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        this.roleService.addRole(role);

        return SUCCESS_TIP;
    }

    @Permission(value = Const.ADMIN_NAME)
    @RequestMapping("/role_edit")
    public String viewRole(@RequestParam String roleId){
        if (ToolUtil.isEmpty(roleId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Role role = this.roleService.getById(roleId);

        LogObjectHolder.me().set(role);
        return PREFIX+"role_edit.html";
    }

    @RequestMapping("/roleTreeList")
    @ResponseBody
    public List<ZTreeNode> getRoleTreeList(){
        List<ZTreeNode> list = this.roleService.getRoleTreeList();
        list.add(ZTreeNode.createParent());
        return list;
    }

    @RequestMapping("/view/{roleId}")
    @ResponseBody
    public ResponseData viewRole(@PathVariable("roleId") Long roleId){
        if (ToolUtil.isEmpty(roleId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Role role = this.roleService.getById(roleId);
        Map<String,Object> map = BeanUtil.beanToMap(role);
        map.put("pName",ConstantFactory.me().getSingleRoleName(role.getPid()));
        return ResponseData.success(map);
    }

    @RequestMapping("/edit")
    @Permission
    @BussinessLog(value = "修改角色",key = "name",dict = RoleDict.class)
    @ResponseBody
    public ResponseData edit(Role role){
        if (ToolUtil.isOneEmpty(role.getName(),role.getPid(),role.getDescription())){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        role.setCreateTime(new Date());
        role.setUpdateUser(ShiroKit.getUserNotNull().getId());
        this.roleService.updateById(role);
        return SUCCESS_TIP;
    }

    @RequestMapping("roleTreeListByUserId/{userId}")
    @ResponseBody
    public List<ZTreeNode> getRoleTreeListByUserId(@PathVariable Long userId){
        User user = this.userService.getById(userId);
        String roleStr = user.getRoleId();
        if (ToolUtil.isEmpty(roleStr)){
            return this.roleService.getRoleTreeList();
        }else{
            String[] roleArray = roleStr.split(",");
            return roleService.getRoleTreeListByIds(roleArray);
        }
    }
}
