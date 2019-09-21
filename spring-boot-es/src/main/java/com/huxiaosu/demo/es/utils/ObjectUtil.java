/**
 * Project Name demo
 * File Name ObjectUtil
 * Package Name com.huxiaosu.demo.es.utils
 * Create Time 2019/9/21
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2019, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.es.utils;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Iterator;
import java.util.Map;

/**
 * Description
 *
 * @author Administrator
 * @ClassName liujie
 * @date 2019/9/21 19:33
 */
public class ObjectUtil {
    /**
     * 判断指定对象是否为空，支持：
     *
     * <pre>
     * 1. CharSequence
     * 2. Map
     * 3. Iterable
     * 4. Iterator
     * 5. Array
     * </pre>
     *
     * @param obj 被判断的对象
     * @return 是否为空，如果类型不支持，返回false
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if(null == obj) {
            return true;
        }

        if(obj instanceof CharSequence) {
            return StrUtil.isEmpty((CharSequence)obj);
        }else if(obj instanceof Map) {
            return MapUtil.isEmpty((Map)obj);
        }else if(obj instanceof Iterable) {
            return IterUtil.isEmpty((Iterable)obj);
        }else if(obj instanceof Iterator) {
            return IterUtil.isEmpty((Iterator)obj);
        }else if(ArrayUtil.isArray(obj)) {
            return ArrayUtil.isEmpty(obj);
        }

        return false;
    }

    /**
     * 判断指定对象是否为非空，支持：
     *
     * <pre>
     * 1. CharSequence
     * 2. Map
     * 3. Iterable
     * 4. Iterator
     * 5. Array
     * </pre>
     *
     * @param obj 被判断的对象
     * @return 是否为空，如果类型不支持，返回true
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }
}
