/**
 * @Description:
 * @author: zs
 * @date: 2019年8月23日 下午6:34:03
 */
package com.huxiaosu.demo.es.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.huxiaosu.demo.es.annotation.VerifyProcessor;
import com.huxiaosu.demo.es.exception.EsException;
import com.huxiaosu.demo.es.model.EsModel;
import com.huxiaosu.demo.es.protocol.EsDataInfo;
import com.huxiaosu.demo.es.protocol.ResponseContent;
import com.huxiaosu.demo.es.service.EsService;
import com.huxiaosu.demo.es.utils.Constants;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liujie
 * @date 2019年8月23日 下午6:34:03
 */
@Slf4j
@RestController
@RequestMapping("/esServer")
@Api(value = "es 操作中心")
public class EsController {

    @Autowired
    private EsService esService;

    @PostMapping("/selectEs")
    @ApiOperation(value = "es查询", notes = "传入indexName, sources, start, pageSize")
    @ApiResponses(value = {
            @ApiResponse(code = 00, message = "code == 00 成功", response = ResponseContent.class),
            @ApiResponse(code = 99001, message = "参数校验失败"),
            @ApiResponse(code = 99005, message = "请求参数为空"),
            @ApiResponse(code = 99999, message = "未知错误")
    })
    public ResponseContent selectEs(@ApiParam("es查询信息") @RequestBody EsModel esModel) {
        VerifyProcessor.verifyBeanParams(esModel);
        EsDataInfo esDataInfo;
        if (esModel.getCurrent() < 1) {
            esModel.setCurrent(1);
        }
        if (esModel.getPageSize() < 1) {
            esModel.setPageSize(10);
        }
        if (StringUtils.isNotBlank(esModel.getEsId())) {

            esDataInfo = esService.selectEsOne(esModel);
        } else {

            esDataInfo = esService.selectEs(esModel);
        }
        return this.setSuccess(esDataInfo);

    }

    @PostMapping("/saveInfo")
    @ApiOperation(value = "保存或更新 ES", notes = "根据传入的索引名称查询,参数说明 indexName&id&type&record 必填  type默认=0")
    public ResponseContent saveInfo(@ApiParam("es查询信息") @RequestBody EsModel esModel) {
        VerifyProcessor.verifyBeanParams(esModel);
        EsException.checkCondition(StrUtil.isBlank(esModel.getEsId()), "索引 ID 不能为空");
        if (ObjectUtil.isNull(esModel.getType())) {
            esModel.setType(Constants.ZERO);
        }
        esService.saveOrUpdate(esModel);
        return this.setSuccess();
    }


    protected ResponseContent setSuccess() {
        return this.setSuccess(null);
    }
    protected ResponseContent setSuccess(Object result) {
        ResponseContent responseContent = new ResponseContent();
        responseContent.setStatus(true);
        responseContent.setErrorMessage("success");
        responseContent.setData(result);
        return responseContent;
    }
}
