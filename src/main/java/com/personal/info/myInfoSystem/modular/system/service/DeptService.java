package com.personal.info.myInfoSystem.modular.system.service;

import com.personal.info.myInfoSystem.core.node.ZTreeNode;
import com.personal.info.myInfoSystem.modular.system.mapper.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptService  {

    @Autowired
    private DeptMapper deptMapper;

    public List<ZTreeNode> getDeptTree() {
        return deptMapper.getDeptTree();
    }
}
