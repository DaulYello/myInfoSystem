package com.personal.info.myInfoSystem.modular.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.personal.info.myInfoSystem.core.common.annotation.Permission;
import com.personal.info.myInfoSystem.core.common.constant.Const;
import com.personal.info.myInfoSystem.core.common.constant.factory.ConstantFactory;
import com.personal.info.myInfoSystem.core.common.constant.page.PageInfo;
import com.personal.info.myInfoSystem.core.common.exception.BizExceptionEnum;
import com.personal.info.myInfoSystem.core.log.LogObjectHolder;
import com.personal.info.myInfoSystem.core.node.ZTreeNode;
import com.personal.info.myInfoSystem.modular.system.entity.Menu;
import com.personal.info.myInfoSystem.modular.system.model.MenuDto;
import com.personal.info.myInfoSystem.modular.system.service.MenuService;
import com.personal.info.myInfoSystem.modular.system.service.UserService;
import com.personal.info.myInfoSystem.modular.system.warpper.MenuWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 菜单控制器
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

    private static final String PREFIX = "/modular/system/menu/";

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    /**
     * 菜单列表页面
     * @return
     */
    @RequestMapping("")
    public String index(){
        return PREFIX+"/menu.html";
    }

    @RequestMapping("/selectMenuTreeList")
    @ResponseBody
    public List<ZTreeNode> selectMenuTreeList(){
        List<ZTreeNode> treeList = menuService.menuTreeList();
        treeList.add(ZTreeNode.createParent());
        return treeList;
    }

    @Permission(Const.ADMIN_NAME)
    @RequestMapping("/listTree")
    @ResponseBody
    public Object listTree(@RequestParam(required = false) String menuName,
                           @RequestParam(required = false) String level){
        List<Map<String, Object>> menus = this.menuService.selectMenuTree(menuName, level);
        List<Map<String, Object>> menusWrap = new MenuWrapper(menus).wrap();

        PageInfo result = new PageInfo();
        result.setData(menusWrap);
        return result;
    }

    @RequestMapping("/menu_add")
    public String menuAdd(){
        return PREFIX+"/menu_add.html";
    }

    @Permission(Const.ADMIN_NAME)
    @RequestMapping("/add")
    @ResponseBody
    public ResponseData add(MenuDto menu){
        this.menuService.addMenu(menu);
        return SUCCESS_TIP;
    }


    @Permission(Const.ADMIN_NAME)
    @RequestMapping("/menu_edit")
    public String menuEdit(@RequestParam(required = true) String menuId, Model model){
        if (ToolUtil.isEmpty(menuId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        //获取菜单当前信息，记录日志用
        Menu menu = this.menuService.getById(menuId);
        LogObjectHolder.me().set(menu);
        /*model.addAttribute("menuId",menuId);*/
        return PREFIX+"menu_edit.html";
    }

    @RequestMapping("/getMenuInfo")
    @ResponseBody
    public ResponseData getMenuInfo(@RequestParam(required = true) String menuId){
        if (ToolUtil.isEmpty(menuId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        Menu menu = this.menuService.getById(menuId);

        MenuDto menuDto = new MenuDto();
        BeanUtil.copyProperties(menu,menuDto);
        //设置pid和父级名称
        menuDto.setPid(ConstantFactory.me().getMenuIdByCode(menuDto.getPcode()));
        menuDto.setPcodeName(ConstantFactory.me().getMenuNameByCode(menuDto.getPcode()));
        return ResponseData.success(menuDto);
    }

    /**
     * 获取角色的菜单列表
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:54 PM
     */
    @RequestMapping(value = "/menuTreeListByRoleId/{roleId}")
    @ResponseBody
    public List<ZTreeNode> menuTreeListByRoleId(@PathVariable Long roleId) {
        List<Long> menuIds = this.menuService.getMenuIdsByRoleId(roleId);
        if (ToolUtil.isEmpty(menuIds)) {
            return this.menuService.menuTreeList();
        } else {
            return this.menuService.menuTreeListByMenuIds(menuIds);
        }
    }

    /**
     * 编辑菜单
     * @author huangshuang
     * @Date 2019/9/18 5:54 PM
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public ResponseData editMenuById(MenuDto menuDto) {

        this.menuService.updateMenu(menuDto);

        //刷新当前用户菜单
        this.userService.refreshCurrentUser();

        return SUCCESS_TIP;
    }

    /**
     * 编辑菜单
     * @author huangshuang
     * @Date 2019/9/18 5:54 PM
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData remove(@RequestParam(value = "menuId" ,required = true) Long menuId) {

        if (ToolUtil.isEmpty(menuId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        this.menuService.delMenuContainSubMenus(menuId);


        return SUCCESS_TIP;
    }


}
