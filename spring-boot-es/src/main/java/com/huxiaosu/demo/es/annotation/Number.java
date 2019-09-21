/**
 * Project Name spring-boot-es
 * File Name NotEmpty
 * Package Name com.huxiaosu.demo.es.annotation
 * Create Time 2019/8/7
 * Create by name：liujie -- email: liujie@huxiaosu.com
 *
 */
package com.huxiaosu.demo.es.annotation;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Description
 *  Integer 类型取值定义
 * @author liujie
 * @ClassName Number
 * @date 2019/8/8 09:38
 */
@Documented
@Constraint(validatedBy = { })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface Number {

    String message() default "{请输入指定数值}";

    /** Integer 取值定义 例如取值为 12  value="1,2"*/
    String value();
}
