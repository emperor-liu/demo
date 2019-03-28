/**
 * Project Name demo
 * File Name UserStatusEnum
 * Package Name com.huxiaosu.demo.shiro.enums
 * Create Time 2019/3/27
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.shiro.enums;

/**
 * Description
 *
 * @ClassName: UserStatusEnum
 * @author: liujie
 * @date: 2019/3/27 14:12
 */
public enum UserStatusEnum {
    NORMAL(1,"正常"),
    DISABLE(0,"禁用"),;

    private Integer code;
    private String desc;

    UserStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static UserStatusEnum get(Integer code) {
        if (null == code) {
            return NORMAL;
        }
        UserStatusEnum[] enums = UserStatusEnum.values();
        for (UserStatusEnum anEnum : enums) {
            if (anEnum.getCode().equals(code)) {
                return anEnum;
            }
        }
        return NORMAL;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
