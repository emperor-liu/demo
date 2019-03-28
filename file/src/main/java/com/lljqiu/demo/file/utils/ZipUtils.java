/**
 * Project Name demo
 * File Name ZipUtils
 * Package Name com.lljqiu.demo.file
 * Create Time 2018/11/17
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.asdc.com.cn. All rights reserved.
 */
package com.lljqiu.demo.file.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Description
 *  ZIP 文件压缩
 * @ClassName: ZipUtils
 * @author: liujie
 * @date: 2018/11/17 15:34
 */
public class ZipUtils {

    /**
     *
     * Description: 
     *
     * @param zipPathName 压缩后文件路径
     * @param paths 待压缩文件路径
     * @param names 待压缩文件名称
     * @return:
     * @author: liujie
     * @date: 2018/11/17 15:38
     */
    public static void compression(String zipPathName, List<String> paths, List<String> names)
            throws Exception {
        System.out.println("开始压缩...");
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipPathName));
        BufferedOutputStream bo = new BufferedOutputStream(out);
        for (int i = 0; i < paths.size(); i++) {

            createZip(out, paths.get(i), names.get(i), bo);
        }
        bo.close();
        out.close(); // 输出流关闭
        System.out.println("压缩完成");
    }

    private static void createZip(ZipOutputStream out, String string, String base,
                                  BufferedOutputStream bo) throws Exception {
        // 创建zip压缩进入点base
        out.putNextEntry(new ZipEntry(base));
        System.out.println(base);
        FileInputStream in = new FileInputStream(string + File.separator + base);
        BufferedInputStream bi = new BufferedInputStream(in);
        int b;
        while ((b = bi.read()) != -1) {
            // 将字节流写入当前zip目录
            bo.write(b);
        }
        bi.close();
        in.close();
    }

    public static void main(String[] args) throws Exception {

        String zipPathName = "/Users/liujie/Desktop/test.zip";
        List<String> paths = new ArrayList<>();
        List<String> names = new ArrayList<>();
        paths.add("/Users/liujie/Desktop/");
        names.add("js正则.js");
        compression(zipPathName, paths, names);

    }

}
