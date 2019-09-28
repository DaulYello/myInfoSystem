package com.personal.info.myInfoSystem.core.common.constant.dictmap;

import com.personal.info.myInfoSystem.core.common.constant.dictmap.base.AbstractDictMap;

/**
 * 用户字典
 * @author huangshuang
 * @date 2019-09-28 14:20
 */
public class UserDict extends AbstractDictMap {

    @Override
    public void init() {
        put("userId", "账号");
        put("avatar", "头像");
        put("account", "账号");
        put("name", "名字");
        put("birthday", "生日");
        put("sex", "性别");
        put("email", "电子邮件");
        put("phone", "电话");
        put("roleId", "角色名称");
        put("deptId", "部门名称");
        put("roleIds", "角色名称集合");
    }

    @Override
    protected void initBeWrapped() {
        putFieldWrapperMethodName("sex", "getSexName");
        putFieldWrapperMethodName("deptId", "getDeptName");
        putFieldWrapperMethodName("roleId", "getSingleRoleName");
        putFieldWrapperMethodName("userId", "getUserAccountById");
        putFieldWrapperMethodName("roleIds", "getRoleName");
    }
}
