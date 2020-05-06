/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.personal.info.myInfoSystem.modular.api;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.alibaba.fastjson.JSONObject;
import com.lowagie.text.Document;
import com.personal.info.myInfoSystem.core.shiro.ShiroKit;
import com.personal.info.myInfoSystem.core.shiro.ShiroUser;
import com.personal.info.myInfoSystem.core.util.JwtTokenUtil;
import com.personal.info.myInfoSystem.modular.system.entity.User;
import com.personal.info.myInfoSystem.modular.system.mapper.UserMapper;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.rtf.RtfWriter2;
import com.personal.info.myInfoSystem.modular.system.entity.TextDetection;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

/**
 * 接口控制器提供
 *
 * @author stylefeng
 * @Date 2018/7/20 23:39
 */
@RestController
@RequestMapping("/myInfoApi")
public class ApiController extends BaseController {

    private static final String filepath = "/Users/huangshuang/Documents/orc处理后的文本/中寨黄姓氏修谱.doc";

    @Autowired
    private UserMapper userMapper;

    /**
     * api登录接口，通过账号密码获取token
     */
    @RequestMapping("/auth")
    public Object auth(@RequestParam("username") String username,
                       @RequestParam("password") String password) {

        //封装请求账号密码为shiro可验证的token
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password.toCharArray());

        //获取数据库中的账号密码，准备比对
        User user = userMapper.getByAccount(username);

        String credentials = user.getPassword();
        String salt = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(salt);
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                new ShiroUser(), credentials, credentialsSalt, "");

        //校验用户账号密码
        HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
        md5CredentialsMatcher.setHashAlgorithmName(ShiroKit.hashAlgorithmName);
        md5CredentialsMatcher.setHashIterations(ShiroKit.hashIterations);
        boolean passwordTrueFlag = md5CredentialsMatcher.doCredentialsMatch(
                usernamePasswordToken, simpleAuthenticationInfo);

        if (passwordTrueFlag) {
            HashMap<String, Object> result = new HashMap<>();
            result.put("token", JwtTokenUtil.generateToken(String.valueOf(user.getUserId())));
            return result;
        } else {
            return new ErrorResponseData(500, "账号密码错误！");
        }
    }

    /**
     * 测试接口是否走鉴权
     */
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Object test() {
        return SUCCESS_TIP;
    }

    @PostMapping("/context/deal")
    @ResponseBody
    public ResponseData dealPictureContext(@RequestBody List<TextDetection> textDetections) {

        System.out.println("xxxx");

        //@RequestBody Map<String,List<Object>> map
        //List<TextDetection> textDetections = JSONObject.parseArray(JSONObject.toJSONString(map.get("TextDetections").get(0)),TextDetection.class);

        //设置纸张大小
        Document document = new Document(PageSize.A4);

        try {
            for (int i = 1; i < textDetections.size() - 2; i++) {

                //创建word文档
                RtfWriter2.getInstance(document, new FileOutputStream(filepath));
                //打开文档
                document.open();
                //创建段落
                Paragraph p = new Paragraph("", new Font(Font.NORMAL, 10, Font.BOLD, new Color(0, 0, 0)));
                //设置段落为居中对齐
                p.setAlignment(Paragraph.ALIGN_CENTER);
                //写入段落
                document.add(p);
            }
            //关流
            document.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return null;
    }

}

