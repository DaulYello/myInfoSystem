package com.personal.info.myInfoSystem.modular.system.service;

import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.exception.enums.CoreExceptionEnum;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.personal.info.myInfoSystem.core.shiro.ShiroKit;
import com.personal.info.myInfoSystem.core.shiro.ShiroUser;
import com.personal.info.myInfoSystem.modular.system.entity.FileInfo;
import com.personal.info.myInfoSystem.modular.system.entity.User;
import com.personal.info.myInfoSystem.modular.system.mapper.FileInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileInfoService extends ServiceImpl<FileInfoMapper,FileInfo> {


    @Autowired
    private UserService userService;

    public void uploadAvatar(String avatar) {
        ShiroUser currentUser = ShiroKit.getUser();
        if (currentUser == null) {
            throw new ServiceException(CoreExceptionEnum.NO_CURRENT_USER);
        }

        User user = userService.getById(currentUser.getId());

        //保存文件信息
        FileInfo fileInfo = new FileInfo();
        fileInfo.setCreateUser(user.getUserId());
        fileInfo.setFileId(IdWorker.getIdStr());
        fileInfo.setFileData(avatar);
        this.save(fileInfo);

        //更新用户的头像
        user.setAvatar(fileInfo.getFileId());
        userService.updateById(user);
    }
}
