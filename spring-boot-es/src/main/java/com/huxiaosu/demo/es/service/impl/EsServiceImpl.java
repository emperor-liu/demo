/**
 * Project Name demo
 * File Name EsServiceImpl
 * Package Name com.huxiaosu.demo.es.service.impl
 * Create Time 2019/9/21
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2019, www.huxiaosu.com. All rights reserved.
 */
package com.huxiaosu.demo.es.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.huxiaosu.demo.es.config.ElasticSearchClient;
import com.huxiaosu.demo.es.exception.EsException;
import com.huxiaosu.demo.es.model.EsModel;
import com.huxiaosu.demo.es.protocol.EsDataInfo;
import com.huxiaosu.demo.es.service.EsService;
import com.huxiaosu.demo.es.utils.Constants;
import com.huxiaosu.demo.es.utils.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Description
 *
 * @author Administrator
 * @ClassName liujie
 * @date 2019/9/21 19:29
 */
@Slf4j
@Service
public class EsServiceImpl implements EsService {

    @Autowired
    private ElasticSearchClient client;

    @Override
    public EsDataInfo selectEs(EsModel esModel) {
        EsDataInfo esDataInfo = null;
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            String searchType = esModel.getSearchType();
            if (Validator.isNumber(searchType)) {
                searchSourceBuilder = combosParams(esModel, searchSourceBuilder);
            }

            esDataInfo = setInfo(esModel, searchSourceBuilder);
        } catch (Exception e) {
            log.error(StrUtil.format("error in query indexName exising:{} mustQuery:{} mustNotQuery:{} orQuery:{}",
                    esModel.getIndexName(), esModel.getMustQuery(), esModel.getMustNotQuery(), esModel.getOrQuery()));
            log.error("selectOne By IndexName & query error {}", e.getMessage());
        }
        return esDataInfo;
    }

    private EsDataInfo setInfo(EsModel esModel, SearchSourceBuilder searchSourceBuilder) throws Exception {
        // 分页数据
        int start = (esModel.getCurrent() - 1) * esModel.getPageSize();
        searchSourceBuilder.from(start);
        searchSourceBuilder.size(esModel.getPageSize());

        //设置一个可选的超时，控制允许搜索的时间
        searchSourceBuilder.timeout(new TimeValue(2, TimeUnit.SECONDS));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(esModel.getIndexName().toLowerCase());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.getClient().search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        TotalHits totalHits = hits.getTotalHits();
        long numHits = totalHits.value;

        SearchHit[] searchHits = hits.getHits();
        List<Object> list = new ArrayList<>();


        //嵌套在SearchHits可以迭代的单个搜索结果中
        Arrays.stream(searchHits).forEach(hit -> {
            JSONObject json = new JSONObject();
            json.put("es-index-key", hit.getId());
            json.putAll(hit.getSourceAsMap());
            list.add(json);
        });

        return new EsDataInfo((int) numHits, esModel.getPageSize(), esModel.getCurrent(), list);
    }

    /**
     * Description:
     * 查询索引不包含某个字段的数据
     *
     * @param esModel 查询条件
     * @return: SearchSourceBuilder
     * @author: liujie
     * @date: 2019/9/9 11:46
     */
    private SearchSourceBuilder combosParams(EsModel esModel, SearchSourceBuilder searchSourceBuilder) {
        String searchType = esModel.getSearchType();
        String existsField = esModel.getExistsField();

        switch (searchType) {
            case "0":
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
                if (ObjectUtil.isNotEmpty(esModel.getMustQuery())) {
                    // 拼接 and 条件
                    esModel.getMustQuery().forEach((key, value) -> queryBuilder.must(QueryBuilders.matchQuery(key, value)));
                }
                if (ObjectUtil.isNotEmpty(esModel.getMustNotQuery())) {
                    // 拼接 not 条件
                    esModel.getMustNotQuery().forEach((key, value) -> queryBuilder.mustNot(QueryBuilders.matchQuery(key, value)));
                }
                if (ObjectUtil.isNotEmpty(esModel.getOrQuery())) {
                    // 拼接 or 条件
                    esModel.getOrQuery().forEach((key, value) -> queryBuilder.should(QueryBuilders.matchQuery(key, value)));
                }
                if (ObjectUtil.isNotEmpty(esModel.getRangeQuery())) {
                    // 拼接 范围条件
                    List<Map<String, String>> rangeQueryParams = esModel.getRangeQuery();
                    rangeQueryParams.forEach(params -> {
                        try {
                            String name = params.get("name");
                            EsException.checkCondition(StrUtil.isBlank(name), "rangeQuery->name 不能为空");
                            String gte = params.get("gte");
                            String gt = params.get("gt");
                            String lte = params.get("lte");
                            String lt = params.get("lt");
                            boolean gtFlag = StrUtil.isBlank(gt) && StrUtil.isBlank(gte) && StrUtil.isBlank(lt) && StrUtil.isBlank(lte);
                            EsException.checkCondition(gtFlag,  "rangeQuery->gte/gt/lt/lte 不能为空");
                            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(name);

                            if (StrUtil.isNotBlank(gt) && StrUtil.isNotBlank(gte)) {
                                rangeQueryBuilder.gt(gt);
                            } else {
                                if (StrUtil.isNotBlank(gt)) {
                                    rangeQueryBuilder.gt(gt);
                                }
                                if (StrUtil.isNotBlank(gte)) {
                                    rangeQueryBuilder.gte(gte);
                                }
                            }
                            if (StrUtil.isNotBlank(lt) && StrUtil.isNotBlank(lte)) {
                                rangeQueryBuilder.lt(lt);
                            } else {
                                if (StrUtil.isNotBlank(lt)) {
                                    rangeQueryBuilder.lt(lt);
                                }
                                if (StrUtil.isNotBlank(lte)) {
                                    rangeQueryBuilder.lte(lte);
                                }
                            }
                            queryBuilder.filter(QueryBuilders.rangeQuery(name).gte(gte).lte(lte));
                        } catch (Exception e) {
                            throw new EsException("rangeQuery参数异常");
                        }
                    });
                }

                boolean queryParamsIsNull = ObjectUtil.isEmpty(queryBuilder.must()) && ObjectUtil.isEmpty(queryBuilder.should())
                        && ObjectUtil.isEmpty(queryBuilder.mustNot()) && ObjectUtil.isEmpty(queryBuilder.filter());

                if (queryParamsIsNull) {
                    // 没有条件查询所有
                    searchSourceBuilder.query(QueryBuilders.matchAllQuery());
                } else {
                    searchSourceBuilder.query(queryBuilder);

                }
                if(ObjectUtil.isNotEmpty(esModel.getOrderMap())){
                    // 排序
                    esModel.getOrderMap().forEach((key, value) -> {
                        if (Constants.ZERO.equals(esModel.getOrderType())) {
                            if ("desc".equalsIgnoreCase(value)) {
                                searchSourceBuilder.sort(key + ".keyword", SortOrder.DESC);
                            } else {
                                searchSourceBuilder.sort(key + ".keyword", SortOrder.ASC);
                            }
                        } else {
                            FieldSortBuilder fieldSort = new FieldSortBuilder(key);
                            if ("desc".equalsIgnoreCase(value)) {
                                searchSourceBuilder.sort(fieldSort.order(SortOrder.DESC));
                            } else {
                                searchSourceBuilder.sort(fieldSort.order(SortOrder.DESC));
                            }
                        }
                    });
                }
                break;
            case "1":
                // 查询不包含某个字段的数据
                EsException.checkCondition(StrUtil.isBlank(existsField), "existsField不能为空");
                searchSourceBuilder.query(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(existsField)));
                break;
            case "2":
                // 查询包含某个字段的数据
                EsException.checkCondition(StrUtil.isBlank(existsField),  "existsField不能为空");
                searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.existsQuery(existsField)));
                break;
            default:
                searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        }
        return searchSourceBuilder;

    }

    @Override
    public EsDataInfo selectEsOne(EsModel esModel) {
        EsDataInfo esDataInfo = null;
        try {
            List<Object> list = new ArrayList<>();
            GetRequest getRequest = new GetRequest(esModel.getIndexName().toLowerCase(), esModel.getEsId());
            GetResponse documentFields = client.getClient().get(getRequest, RequestOptions.DEFAULT);
            Map<String, Object> source = documentFields.getSource();
            list.add(source);
            esDataInfo = new EsDataInfo(1, 1, 1, list);
        } catch (Exception e) {
            log.error(StrUtil.format("error in query indexName exising:{} esId:{}",
                    esModel.getIndexName(), esModel.getEsId()));
            log.error("selectEsOne by esId & esIndexName {}", e.getMessage());
        }
        return esDataInfo;
    }

    @Override
    public void saveOrUpdate(EsModel esModel) {
        String indexName = esModel.getIndexName().toLowerCase();
        String id = esModel.getEsId();
        JSONObject record;
        try {
            record = JSONObject.parseObject(esModel.getRecord());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new EsException("record 参数校验失败，record必须为 JSON string");
        }
        try {
            if (esModel.getType().equals(Constants.ZERO)) {
                IndexRequest indexRequest = new IndexRequest(indexName)
                        .source(record.toJSONString(), XContentType.JSON);
                indexRequest.id(id);

                indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
                indexRequest.setRefreshPolicy("true");
                log.info("====indeName {} start {}",indexName,System.currentTimeMillis());
                client.getClient().index(indexRequest, RequestOptions.DEFAULT);
                log.info("====indeName {} end {}",indexName,System.currentTimeMillis());
            } else {
                UpdateRequest request = new UpdateRequest(indexName, id);
                request.doc(record.toJSONString(), XContentType.JSON);
                request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
                request.setRefreshPolicy("true");
                client.getClient().update(request, RequestOptions.DEFAULT);
            }
        } catch (Exception e) {
            log.error("{}", e);
            throw new EsException("保存 ES 数据失败");
        }

    }

}
