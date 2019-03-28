/**
 * Project Name hxs
 * File Name CredentialsMatcher
 * Package Name com.huxiaosu.wcs.core.shiro
 * Create Time 2019/3/15
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.shiro.core;

import com.huxiaosu.demo.shiro.utils.EncryptionUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * Description
 *
 * @ClassName: CredentialsMatcher
 * @author: liujie
 * @date: 2019/3/15 23:51
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authToken, AuthenticationInfo info) {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        // 获得用户输入的密码
        String inPassword = new String(token.getPassword());
        //获得数据库中的密码
        String dbPassword = (String) info.getCredentials();
        inPassword = EncryptionUtil.encryptMD5(inPassword);
        //进行密码的比对
        return this.equals(inPassword, dbPassword);
    }
}
