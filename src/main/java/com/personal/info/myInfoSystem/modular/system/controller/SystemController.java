package com.personal.info.myInfoSystem.modular.system.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.exception.enums.CoreExceptionEnum;
import com.personal.info.myInfoSystem.core.common.constant.DefaultAvatar;
import com.personal.info.myInfoSystem.core.common.constant.factory.ConstantFactory;
import com.personal.info.myInfoSystem.core.log.LogObjectHolder;
import com.personal.info.myInfoSystem.core.shiro.ShiroKit;
import com.personal.info.myInfoSystem.core.shiro.ShiroUser;
import com.personal.info.myInfoSystem.modular.system.entity.FileInfo;
import com.personal.info.myInfoSystem.modular.system.entity.User;
import com.personal.info.myInfoSystem.modular.system.factory.UserFactory;
import com.personal.info.myInfoSystem.modular.system.model.SystemHardwareInfo;
import com.personal.info.myInfoSystem.modular.system.service.FileInfoService;
import com.personal.info.myInfoSystem.modular.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用控制器
 *
 */
@Controller
@RequestMapping("/system")
@Slf4j
public class SystemController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileInfoService fileInfoService;

    @RequestMapping("/console")
    public String console(){
        return "/modular/frame/console.html";
    }

    /**
     * 通用的树列表选择器
     *
     * @author huangshuang
     * @Date 2019/09/10
     */
    @RequestMapping("/commonTree")
    public String deptTreeList(@RequestParam("formName") String formName,
                               @RequestParam("formId") String formId,
                               @RequestParam("treeUrl") String treeUrl, Model model) {

        if (ToolUtil.isOneEmpty(formName, formId, treeUrl)) {
            throw new RequestEmptyException("请求数据不完整！");
        }

        try {
            model.addAttribute("formName", URLDecoder.decode(formName, "UTF-8"));
            model.addAttribute("formId", URLDecoder.decode(formId, "UTF-8"));
            model.addAttribute("treeUrl", URLDecoder.decode(treeUrl, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RequestEmptyException("请求数据不完整！");
        }

        return "/common/tree_dlg.html";
    }

    @RequestMapping("/user_info")
    public String getUserIfon(Model model){

        Long userId = ShiroKit.getUserNotNull().getId();
        User user= userService.getById(userId);
        /*model.addAllAttributes(BeanUtil.beanToMap(user));*/
        model.addAttribute("roleName",ConstantFactory.me().getRoleName(user.getRoleId()));
        model.addAttribute("deptName",ConstantFactory.me().getDeptName(user.getDeptId()));
        LogObjectHolder.me().set(user);//将对象临时保存
        return "/modular/frame/user_info.html";
    }

    @RequestMapping("/currentUserInfo")
    @ResponseBody
    public ResponseData getcurrentUserInfo(){

        ShiroUser currentUser = ShiroKit.getUserNotNull();
        if (null == currentUser){
            throw new ServiceException(CoreExceptionEnum.NO_CURRENT_USER);
        }
        User user= userService.getById(currentUser.getId());
        Map<String,Object> map = UserFactory.removeUnSafeFields(user);
        HashMap<String,Object> hashMap = CollectionUtil.newHashMap();
        hashMap.putAll(map);
        hashMap.put("roleNam",ConstantFactory.me().getRoleName(user.getRoleId()));
        hashMap.put("deptNam",ConstantFactory.me().getDeptName(user.getDeptId()));
        return ResponseData.success(hashMap);
    }


    /**
     * 头像预览
     */
    @RequestMapping("/previewAvatar")
    @ResponseBody
    public ResponseData previewAvatar(HttpServletResponse response){
        ShiroUser currentUser = ShiroKit.getUserNotNull();
        if (null == currentUser){
            throw new ServiceException(CoreExceptionEnum.NO_CURRENT_USER);
        }
        //获取当前用户的头像id
        User user = userService.getById(currentUser.getId());
        String avatar = user.getAvatar();
        if (ToolUtil.isEmpty(avatar)){
            avatar = DefaultAvatar.BASE_64_AVATAR;
        } else {
            FileInfo fileInfo = fileInfoService.getById(avatar);
            if (fileInfo == null) {
                avatar = DefaultAvatar.BASE_64_AVATAR;
            } else {
                avatar = fileInfo.getFileData();
            }
        }
        //输出图片的文件流
        try {
            response.setContentType("image/jpeg");
            byte[] decode = Base64.decode(avatar);
            response.getOutputStream().write(decode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 上传头像
     */
    @RequestMapping("/uploadAvatar")
    @ResponseBody
    public Object uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {

        if (ToolUtil.isEmpty(file)){
            throw new RequestEmptyException("请求头像为空!");
        }


        BASE64Encoder encoder = new BASE64Encoder();

        String avatar = file.getOriginalFilename()+","+encoder.encode(file.getBytes());

        avatar = avatar.substring(avatar.indexOf(",") + 1);

        fileInfoService.uploadAvatar(avatar);

        return SUCCESS_TIP;
    }

    /**
     * 系统硬件信息页面
     *
     * @author huangshuang
     * @Date 2019/12/24 22:43
     */
    @RequestMapping("/systemInfo")
    public String systemInfo(Model model) {

        SystemHardwareInfo systemHardwareInfo = new SystemHardwareInfo();
        systemHardwareInfo.copyTo();

        model.addAttribute("server", systemHardwareInfo);

        return "/modular/frame/systemInfo.html";
    }



}
