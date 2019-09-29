package com.personal.info.myInfoSystem.modular.system.service;

import cn.hutool.core.convert.Convert;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.info.myInfoSystem.core.common.constant.page.PageFactory;
import com.personal.info.myInfoSystem.core.common.exception.BizExceptionEnum;
import com.personal.info.myInfoSystem.core.node.ZTreeNode;
import com.personal.info.myInfoSystem.core.shiro.ShiroKit;
import com.personal.info.myInfoSystem.modular.system.entity.Relation;
import com.personal.info.myInfoSystem.modular.system.entity.Role;
import com.personal.info.myInfoSystem.modular.system.mapper.RelationMapper;
import com.personal.info.myInfoSystem.modular.system.mapper.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RoleService extends ServiceImpl<RoleMapper,Role> {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RelationMapper relationMapper;

    @Resource
    private UserService userService;
    /**
     * 角色列表
     * @param roleName
     * @return
     */
    public Page<Map<String,Object>> list(String roleName) {
        Page page = PageFactory.defaultPage();
        return roleMapper.selectRoles(page,roleName);
    }

    /**
     * 设置某个角色的权限
     * @param roleId
     * @param ids
     */
    @Transactional(rollbackFor = Exception.class)
    public void setAuthority(Long roleId, String ids) {

        //删除该角色的所有权限
        this.roleMapper.deleteRolesById(roleId);

        //添加新的权限
        for (Long id : Convert.toLongArray(ids.split(","))){
            Relation relation = new Relation();
            relation.setMenuId(id);
            relation.setRoleId(roleId);
            this.relationMapper.insert(relation);
        }

        //刷新当前用户权限
        this.userService.refreshCurrentUser();

    }

    @Transactional(rollbackFor = Exception.class)
    public void addRole(Role role) {

        if (ToolUtil.isOneEmpty(role.getName(),role.getDescription(),role.getPid())){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        role.setCreateUser(ShiroKit.getUserNotNull().getId());

        role.setCreateTime(new Date());

        this.save(role);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ZTreeNode> getRoleTreeList() {
        return this.roleMapper.roleTreeList();
    }

    public List<ZTreeNode> getRoleTreeListByIds(String[] roleArray) {
        return this.roleMapper.roleTreeListByRoleId(roleArray);
    }
}
