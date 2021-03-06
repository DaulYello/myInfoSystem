package com.personal.info.myInfoSystem.modular.system.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.personal.info.myInfoSystem.core.shiro.ShiroKit;
import com.personal.info.myInfoSystem.core.shiro.ShiroUser;
import com.personal.info.myInfoSystem.modular.system.service.UserService;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static cn.stylefeng.roses.core.util.HttpContext.getIp;

/**
 * 登录控制器
 * @author xxx
 */
@RestController
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 点击登录执行的动作
     *
     * @author fengshuonan
     * @Date 2018/12/23 5:42 PM
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginVali() {

        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();
        String remember = super.getPara("remember");



        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());

        //如果开启了记住我功能
        if ("on".equals(remember)) {
            token.setRememberMe(true);
        } else {
            token.setRememberMe(false);
        }

        //执行shiro登录操作,开始执行shiro的认证流程(通过令牌token与项目currentUser的登录login关系)
        currentUser.login(token);

        //登录成功，记录登录日志
        ShiroUser shiroUser = ShiroKit.getUserNotNull();
        //LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag", true);

        return REDIRECT + "/";
    }
}
