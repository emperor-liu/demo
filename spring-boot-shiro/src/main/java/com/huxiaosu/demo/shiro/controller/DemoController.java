/**
 * Project Name demo
 * File Name DemoController
 * Package Name com.huxiaosu.demo.shiro.controller
 * Create Time 2019/3/28
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.shiro.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;


/**
 * Description
 *      @RequiresPermissions("/admin/userManager.html") 次注解标识 需要进行鉴权操作  参数是 需要拥有的权限
 *          次注解可以放到方法上面或类上面
 * @ClassName: DemoController
 * @author: liujie
 * @date: 2019/3/28 12:16
 */
@Slf4j
@Controller
public class DemoController {

    @RequiresPermissions("/admin/userManager.html")
    @GetMapping("getMenus")
    @ResponseBody
    public String getMenus() {
        return "success";
    }


    @PostMapping(path="/loginSystem",produces={"application/json;charset=UTF-8"})
    @ResponseBody
    public String loginSystem(@RequestBody JSONObject json) throws Exception {
        if(log.isDebugEnabled()){
            log.info("request login parasm = {}",json.toString());
        }
        String loginToken = UUID.randomUUID().toString();
        String userAccount = json.getString("userAccount");
        String password = json.getString("password");
        UsernamePasswordToken shiroToken = new UsernamePasswordToken(userAccount, password);
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            // 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            // 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            // 所以这一步在调用login(token)方法时,它会走到xxRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            currentUser.login(shiroToken);
            return "success";
        } catch (Exception e) {
            log.error("登录失败，用户名[{}]", userAccount, e);
            shiroToken.clear();
            return "登录失败";
        }
    }


}
