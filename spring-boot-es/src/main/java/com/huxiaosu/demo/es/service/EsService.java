/**
 * Project Name demo
 * File Name EsService
 * Package Name com.huxiaosu.demo.es.service
 * Create Time 2019/9/21
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2019, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.es.service;

import com.huxiaosu.demo.es.model.EsModel;
import com.huxiaosu.demo.es.protocol.EsDataInfo;

/**
 * Description
 *
 * @author Administrator
 * @ClassName liujie
 * @date 2019/9/21 19:29
 */
public interface EsService {

    /**
     *
     * Description:
     *  es查询 查询全部
     * @param esModel
     * @return EsDataInfo
     * @author: zs
     * @date: 2019/8/26 15:40
     */
    EsDataInfo selectEs(EsModel esModel);

    /**
     *
     * Description:
     *  es查询根据ID
     * @param esModel
     * @return EsDataInfo
     * @author: zs
     * @date: 2019/8/26 16:32
     */
    EsDataInfo selectEsOne(EsModel esModel);

    /**
     * ES 保存或修改
     * @param esModel
     */
    void saveOrUpdate(EsModel esModel);
}
