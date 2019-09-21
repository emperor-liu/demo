/**
 * Project Name demo
 * File Name ReadFile
 * Package Name com.lljqiu.demo.file
 * Create Time 2018/11/17
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.asdc.com.cn. All rights reserved.
 */
package com.lljqiu.demo.file.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Description
 *
 * @ClassName: ReadFile
 * @author: liujie
 * @date: 2018/11/17 15:54
 */
public class ReadFile {

    public static void main(String[] args) throws IOException {
        String content = FileUtils.readFileToString(new File("/Users/liujie/Desktop/info.txt"),"UTF-8");
        JSONObject json = JSONObject.parseObject(content);

        StringBuilder sbTitle = new StringBuilder();
        sbTitle.append("序号,项目协议号,贷款合作客户名称,联系人,联系人电话,项目名称,项目位置,开始日期,终止日期");
        StringBuilder cvs = new StringBuilder();
        cvs.append("\n").append(sbTitle);
        JSONArray result = json.getJSONArray("result");
        for(int i = 0; i < result.size(); i++){
            JSONArray jsonArray = result.getJSONArray(i);
            StringBuilder cvsRow = new StringBuilder();
            for(int j =0;j<jsonArray.size();j++){
                if(j == jsonArray.size()-1){
                    cvsRow.append(jsonArray.getJSONObject(j).getString("info"));
                }else{
                    cvsRow.append(jsonArray.getJSONObject(j).getString("info")).append(",");
                }
            }
            cvs.append("\n").append(cvsRow);
        }
        FileUtils.write(new File("/Users/liujie/Desktop/test.csv"),cvs.toString());


    }
}
