package com.personal.info.myInfoSystem.modular.system.service;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.info.myInfoSystem.core.common.constant.page.PageFactory;
import com.personal.info.myInfoSystem.modular.system.entity.Relation;
import com.personal.info.myInfoSystem.modular.system.entity.Role;
import com.personal.info.myInfoSystem.modular.system.mapper.RelationMapper;
import com.personal.info.myInfoSystem.modular.system.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
