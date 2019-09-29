package com.personal.info.myInfoSystem.modular.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.datascope.DataScope;
import cn.stylefeng.roses.core.reqres.request.RequestData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.personal.info.myInfoSystem.core.common.annotation.BussinessLog;
import com.personal.info.myInfoSystem.core.common.annotation.Permission;
import com.personal.info.myInfoSystem.core.common.constant.Const;
import com.personal.info.myInfoSystem.core.common.constant.dictmap.RoleDict;
import com.personal.info.myInfoSystem.core.common.constant.dictmap.UserDict;
import com.personal.info.myInfoSystem.core.common.constant.factory.ConstantFactory;
import com.personal.info.myInfoSystem.core.common.constant.page.PageFactory;
import com.personal.info.myInfoSystem.core.common.exception.BizExceptionEnum;
import com.personal.info.myInfoSystem.core.log.LogObjectHolder;
import com.personal.info.myInfoSystem.core.shiro.ShiroKit;
import com.personal.info.myInfoSystem.modular.system.entity.User;
import com.personal.info.myInfoSystem.modular.system.factory.UserFactory;
import com.personal.info.myInfoSystem.modular.system.model.UserDto;
import com.personal.info.myInfoSystem.modular.system.service.UserService;
import com.personal.info.myInfoSystem.modular.system.warpper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理
 */
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


    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Object getUserInfo(@RequestParam(required = true) Long userId){

        this.userService.assertAuth(userId);
        User user = this.userService.getById(userId);
        Map<String, Object> map = UserFactory.removeUnSafeFields(user);

        HashMap<Object, Object> hashMap = CollectionUtil.newHashMap();
        hashMap.putAll(map);
        hashMap.put("roleName", ConstantFactory.me().getRoleName(user.getRoleId()));
        hashMap.put("deptName", ConstantFactory.me().getDeptName(user.getDeptId()));
        return ResponseData.success(hashMap);
    }


    @RequestMapping("/user_add")
    public String addView(){
        return PREFIX+"user_add.html";
    }

    /**
     * @Valid 和 BindingResult 是一一对应的，如果有多个@Valid，
     * 那么每个@Valid后面跟着的BindingResult就是这个@Valid的验证结果，顺序不能乱
     * @param user
     * @param result
     * @return
     */
    @Permission(Const.ADMIN_NAME)
    @BussinessLog(value = "添加管理员",key = "account",dict = UserDict.class)
    @RequestMapping("/add")
    public ResponseData add(@Valid UserDto user, BindingResult result){

        if (result.hasErrors()){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        this.userService.addUser(user);

        return SUCCESS_TIP;
    }



    @RequestMapping("/user_edit")
    public String userEdit(@RequestParam(required = true) Long userId){

        User user = this.userService.getById(userId);

        //緩存要修改的對象
        LogObjectHolder.me().set(user);

        return PREFIX+"user_edit.html";
    }

    /**
     * @Valid 和 BindingResult 是一一对应的，如果有多个@Valid，
     * 那么每个@Valid后面跟着的BindingResult就是这个@Valid的验证结果，顺序不能乱
     * @param user
     * @param result
     * @return
     */
    @Permission
    @BussinessLog(value = "修改管理员",key = "account",dict = UserDict.class)
    @RequestMapping("/edit")
    public ResponseData edit(@Valid UserDto user, BindingResult result){

        if (result.hasErrors()){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        this.userService.editUser(user);

        return SUCCESS_TIP;
    }

    @Permission
    @RequestMapping("/role_assign")
    public String roleAssign(@RequestParam Long userId, Model model){
        if (ToolUtil.isEmpty(userId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        model.addAttribute("userId",userId);
        return PREFIX+"user_roleassign.html";
    }

    @Permission
    @BussinessLog(value = "角色分配",key = "userId,roleIds",dict = UserDict.class)
    @RequestMapping("/setRole")
    @ResponseBody
    public ResponseData setRole(@RequestParam Long userId,@RequestParam String roleIds){
        if (ToolUtil.isOneEmpty(userId,roleIds)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        if (userId.equals(Const.ADMIN_ID)){
            throw new ServiceException(BizExceptionEnum.CANT_CHANGE_ADMIN);
        }
        //判断当前登录用户是否有修改userId这个用户的权限
        this.userService.assertAuth(userId);
        this.userService.setRoles(userId,roleIds);
        return SUCCESS_TIP;
    }

    @Permission
    @BussinessLog(value = "重置密码",key = "userId",dict = UserDict.class)
    @RequestMapping("/reset")
    @ResponseBody
    public ResponseData reset(@RequestParam Long userId){
        if (ToolUtil.isEmpty(userId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        //判断当前登录用户是否有修改userId这个用户的权限
        this.userService.assertAuth(userId);
        User user = this.userService.getById(userId);
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(Const.DEFAULT_PWD,user.getSalt()));
        this.userService.updateById(user);
        return SUCCESS_TIP;
    }

}
