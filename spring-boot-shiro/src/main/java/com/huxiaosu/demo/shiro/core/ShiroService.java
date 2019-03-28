/**
 * Project Name hxs
 * File Name ShiroService
 * Package Name com.huxiaosu.wcs.core.shiro
 * Create Time 2019/3/15
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.shiro.core;

import com.huxiaosu.demo.shiro.config.SpringContextHolder;
import com.huxiaosu.demo.shiro.model.User;
import com.huxiaosu.demo.shiro.service.RedisClient;
import com.huxiaosu.demo.shiro.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @ClassName: ShiroService
 * @author: liujie
 * @date: 2019/3/15 23:30
 */
@Slf4j
@Component
public class ShiroService {


    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisClient redisClient;

    /**
     *
     * Description:
     * 初始化默认权限
     * @return: Map
     * @author: liujie
     * @date: 2019/3/15 23:49
     */
    public Map<String, String> loadFilterChainDefinitions() {
        /*
            配置访问权限
            - anon:所有url都都可以匿名访问
            - authc: 需要认证才能进行访问（此处指所有非匿名的路径都需要登录才能访问）
            - user:配置记住我或认证通过可以访问
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/login", "anon");

        filterChainDefinitionMap.put("/error", "anon");
        filterChainDefinitionMap.put("/assets/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/install/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        // 这里可以查询数据库中的配置项进行配置， 次 demo 采用的是菜单 URL 配置（比较简单的一种）
        // 其他的操作均需认证通过方可访问
        filterChainDefinitionMap.put("/**", "user");
        return filterChainDefinitionMap;
    }

    /**
     *
     * Description:
     *  重新加载权限
     * @return:
     * @author: liujie
     * @date: 2019/3/15 23:49
     */
    public void updatePermission() {
        ShiroFilterFactoryBean shirFilter = SpringContextHolder.getBean(ShiroFilterFactoryBean.class);
        synchronized (shirFilter) {
            AbstractShiroFilter shiroFilter = null;
            try {
                shiroFilter = (AbstractShiroFilter) shirFilter.getObject();
            } catch (Exception e) {
                throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
            }

            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

            // 清空老的权限控制
            manager.getFilterChains().clear();

            shirFilter.getFilterChainDefinitionMap().clear();
            shirFilter.setFilterChainDefinitionMap(loadFilterChainDefinitions());
            // 重新构建生成
            Map<String, String> chains = shirFilter.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                manager.createChain(url, chainDefinition);
            }
        }
    }

    /**
     * 重新加载用户权限
     *  清空用户的缓存数据，在用户下次授权的时候重新从数据库查询
     * @param user
     */
    private void reloadAuthorizingByUserId(User user) {
        String cacheRoleKey = user.getUserId() + ShiroRealm.AuthPrefix.USER_ROLE ;
        String cacheFuncKey = user.getUserId() + ShiroRealm.AuthPrefix.USER_FUNC ;
        redisClient.del(cacheRoleKey);
        redisClient.del(cacheFuncKey);

        log.info("用户[{}]的权限更新成功！！", user.getUserAccount());

    }

    /**
     * 重新加载所有拥有roleId角色的用户的权限
     *  当修改角色权限的时候 调用次方法
     * @param roleId
     */
    public void reloadAuthorizingByRoleId(Long roleId) {
        List<User> userList = roleService.findAllByRoleId(roleId);
        if (CollectionUtils.isEmpty(userList)) {
            return;
        }
        for (User user : userList) {
            reloadAuthorizingByUserId(user);
        }
    }
}
