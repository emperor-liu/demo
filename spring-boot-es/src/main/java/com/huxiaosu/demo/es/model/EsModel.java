/**
 * @Description:
 * @author: zs
 * @date: 2019年8月26日 上午11:23:59
 */
package com.huxiaosu.demo.es.model;

import com.huxiaosu.demo.es.annotation.NotEmpty;
import com.huxiaosu.demo.es.annotation.Number;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author liujie
 * @date 2019年8月26日 上午11:23:59
 */
@Data
@ToString
@ApiModel(value = "es查询对象")
public class EsModel {

    @ApiModelProperty(value = "索引名称", name = "indexName", required = true, example = "test_index")
    @NotEmpty(message = "请填写索引名称")
    private String indexName;

    @ApiModelProperty(value = "and 包含并且包含  ", name = "mustQuery", example = "{\"name\":\"zhangsan1\",\"CLZTDM\":\"2\"}")
    private Map<String, Object> mustQuery;
    @ApiModelProperty(value = "not 包含并且不包含  ", name = "mustNotQuery", example = "{\"name\":\"zhangsan1\",\"CLZTDM\":\"2\"}")
    private Map<String, Object> mustNotQuery;
    @ApiModelProperty(value = "or  多条件满足其一", name = "orQuery", example = "{\"name\":\"zhangsan1\",\"CLZTDM\":\"2\"}")
    private Map<String, Object> orQuery;

    @ApiModelProperty(value = "范围查询 ; " +
            "name 要查询的列 " +
            "gte 大于等于 lte 小于等于 " +
            "gt 大于  lt 小于",
            name = "rangeQuery", example = "[{\"name\":\"zhangsan1\",\"gte\":\"2\",\"lte\":\"2\"}]")
    private List<Map<String, String>> rangeQuery;

    @ApiModelProperty(value = "排序  key 为排序字段， value是规则", name = "orderMap", example = "{\"username\":\"desc\"}")
    private Map<String, String> orderMap;

    @ApiModelProperty(value = "查询方式  " +
            "searchType = 1 查询不包含某个字段的数据; " +
            "searchType = 2 查询包含某个字段的数据;" +
            "searchType = 0 按照 mustQuery、mustNotQuery、orQuery 条件查询 会自动拼接",
            name = "searchType", example = "1")
    @Number(value = "0,1,2",message = "请输入正确的查询方式")
    private String searchType;

    @ApiModelProperty(value = "排序字段类型  ；0 是文本、1 是时间", name = "orderType", example = "0")
    @Number(value = "0,1",message = "请输入正确的排序类型")
    private Integer orderType = 0;

    @ApiModelProperty(value = "指定的字段； 当searchType==1或==2 时取值 此值必填", name = "existsField", required = true, example = "test_index")
    private String existsField;

    @ApiModelProperty(value = "当前页，默认：1", name = "current", example = "0")
    private int current = 1;

    @ApiModelProperty(value = "每页条数，默认 ：10", name = "pageSize", example = "10")
    private int pageSize = 10;

    @ApiModelProperty(value = "esId", name = "esId", example = "32ABLvlm04e8CAb0w_1")
    private String esId ;

    @ApiModelProperty(value = "保存或更新 0 保存 1 更新", name = "sources", example = "0")
    private Integer type = 0;
    @ApiModelProperty(value = "待保存或更新的全量数据-JSON ", name = "sources", example = "{\"name\":\"zhangsan1\",\"CLZTDM\":\"2\"}")
    private String record;
}
