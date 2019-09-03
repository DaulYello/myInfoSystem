package com.personal.info.myInfoSystem.modular.system.controller;

import com.personal.info.myInfoSystem.core.common.constant.page.LayuiPageInfo;
import com.personal.info.myInfoSystem.core.node.ZTreeNode;
import com.personal.info.myInfoSystem.modular.system.service.MenuService;
import com.personal.info.myInfoSystem.modular.system.warpper.MenuWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 菜单控制器
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

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
    public List<ZTreeNode> selectMenuTreeList(){
        List<ZTreeNode> treeList = menuService.menuTreeList();
        treeList.add(ZTreeNode.createParent());
        return treeList;
    }

    @RequestMapping("/listTree")
    public Object listTree(@RequestParam(required = false) String menuName,
                           @RequestParam(required = false) String level){
        List<Map<String, Object>> menus = this.menuService.selectMenuTree(menuName, level);
        List<Map<String, Object>> menusWrap = new MenuWrapper(menus).wrap();

        LayuiPageInfo result = new LayuiPageInfo();
        result.setData(menusWrap);
        return result;
    }



}
