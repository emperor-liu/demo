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
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Description
 *
 * @author liujie
 * @ClassName NotEmpty
 * @date 2019/8/7 13:30
 */
@Documented
@Constraint(validatedBy = { })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(NotEmpty.List.class)
public @interface NotEmpty {

    String message() default "{必选参数不可为空}";

    /**
     * Defines several {@code @NotEmpty} constraints on the same element.
     *
     * @see javax.validation.constraints.NotEmpty
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        NotEmpty[] value();
    }
}
