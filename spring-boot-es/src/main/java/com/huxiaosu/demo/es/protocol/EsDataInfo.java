/**
 * Project Name demo
 * File Name EsDataInfo
 * Package Name com.huxiaosu.demo.es.protocol
 * Create Time 2019/9/21
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2019, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.es.protocol;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Description
 *
 * @author Administrator
 * @ClassName liujie
 * @date 2019/9/21 19:30
 */
@Data
@ToString
public class EsDataInfo {

    /** 共计*/
    private Integer total;
    /** 共计*/
    private Integer size;
    /** 每页显示 */
    private Integer pageSize;
    /** 当前页 */
    private Integer pageNum;
    /** 共计页 */
    private Integer totalPages;

    private List<Object> list;

    /**
     *
     * Description:
     *  构建 ES response
     * @param total 总数
     * @param pageSize 每页显示
     * @param pageNum 当前页
     * @param list 数据
     * @author liujie
     * @date 2019/8/26 16:05
     */
    public EsDataInfo(Integer total, Integer pageSize, Integer pageNum, List<Object> list) {
        this.total = total;
        this.pageSize = pageSize;
        this.size = total;
        this.pageNum = pageNum;
        this.list = list;
        this.totalPages = this.getTotalPageNumber();

    }


    private Integer getTotalPageNumber() {
        if (total < 1 || pageSize == 0) {
            return 0;
        }
        if ((total > pageSize) && (total % pageSize != 0)) {
            return total / pageSize + 1;
        }
        if ((total > pageSize) && (total % pageSize == 0)) {
            return total / pageSize;
        }
        return 1;
    }
}