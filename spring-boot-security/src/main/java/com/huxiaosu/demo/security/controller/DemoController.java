/**
 * Project Name demo
 * File Name DemoController
 * Package Name com.huxiaosu.demo.security.controller
 * Create Time 2019/3/28
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.security.controller;

import com.alibaba.fastjson.JSONObject;
import com.huxiaosu.demo.security.core.AuthService;
import com.huxiaosu.demo.security.core.AuthUsers;
import com.huxiaosu.demo.security.utils.EncryptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * Description
 *
 * @ClassName: DemoController
 * @author: liujie
 * @date: 2019/3/28 14:38
 */
@Slf4j
@Controller
public class DemoController {
    @Autowired
    private AuthService authService;
    @GetMapping("login")
    public String login(){return "login";}

    @GetMapping("main")
    public String main(){return "main";}

    @PostMapping(path="/loginSystem",produces={"application/json;charset=UTF-8"})
    @ResponseBody
    public String loginSystem(@RequestBody JSONObject json) throws Exception {
        if(log.isDebugEnabled()){
            log.info("request login parasm = {}",json.toString());
        }
        String loginToken = UUID.randomUUID().toString();
        String userAccount = json.getString("userAccount");
        String password = json.getString("password");
        // 这里需要调用
        AuthUsers userDetails = (AuthUsers) authService.loadUserByUsername(userAccount);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        return "success";
    }
}
