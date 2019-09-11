package com.personal.info.myInfoSystem.modular.system.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.personal.info.myInfoSystem.core.common.annotation.Permission;
import com.personal.info.myInfoSystem.core.common.constant.Const;
import com.personal.info.myInfoSystem.core.common.constant.page.PageInfo;
import com.personal.info.myInfoSystem.core.node.ZTreeNode;
import com.personal.info.myInfoSystem.modular.system.model.MenuDto;
import com.personal.info.myInfoSystem.modular.system.service.MenuService;
import com.personal.info.myInfoSystem.modular.system.warpper.MenuWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 菜单控制器
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

    private static final String PREFIX = "/modular/system/menu";

    @Autowired
    private MenuService menuService;

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



}
