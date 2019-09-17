package com.personal.info.myInfoSystem.modular.system.controller;

import com.personal.info.myInfoSystem.core.node.ZTreeNode;
import com.personal.info.myInfoSystem.modular.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @RequestMapping("/tree")
    @ResponseBody
    public List<ZTreeNode> getDeptTree(){
        List<ZTreeNode> treeNodes = this.deptService.getDeptTree();
        treeNodes.add(ZTreeNode.createParent());
        return treeNodes;
    }
}
