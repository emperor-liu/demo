/**
 * Project Name spring-boot-es
 * File Name NotEmpty
 * Package Name com.huxiaosu.demo.es.annotation
 * Create Time 2019/8/7
 * Create by name：liujie -- email: liujie@huxiaosu.com
 *
 */
package com.huxiaosu.demo.es.annotation;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Field;

/**
 * 请求参数对象非空判断
 *
 * @author liujie
 * @ClassName VerifyProcessor
 * @date 2019/8/7 13:34
 */
public class VerifyProcessor {

    public static final Integer[] MESSAGE_TYPE = {1, 2, 3, 4, 5, 6, 7};

    /**
     * Description:
     * 请求对象必选参数非空判断
     *
     * @param object 请求参数对象
     * @author: liujie
     * @date: 2019/8/7 13:50
     */
    public static void verifyBeanParams(Object object) {

        Field[] fields = ReflectUtil.getFieldsDirectly(object.getClass(), false);
        for (Field field : fields) {
            if (field.isAnnotationPresent(NotEmpty.class)) {
                NotEmpty notEmpty = field.getAnnotation(NotEmpty.class);
                Object fieldValue;
                try {
                    field.setAccessible(true);
                    fieldValue = ReflectUtil.getFieldValue(object, field);
                    boolean b = ObjectUtil.isNull(fieldValue);
                    if(b) {
                        throw new RuntimeException(notEmpty.message());
                    }
                } catch (Exception e) {
                    throw new RuntimeException( notEmpty.message());
                }
            }
            if (field.isAnnotationPresent(Number.class)) {
                Number number = field.getAnnotation(Number.class);
                String valueValid = number.value();
                Object fieldValue;
                try {
                    field.setAccessible(true);
                    fieldValue = ReflectUtil.getFieldValue(object, field);
                    if(ObjectUtil.isNotNull(fieldValue)) {
                        // 非空时校验
                        String[] reg = StrUtil.split(valueValid, ",");
                        boolean result = ArrayUtil.contains(reg, fieldValue.toString());
                        if(!result) {
                            throw new RuntimeException(number.message());
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(number.message());
                }
            }
        }
    }


}

