/**
 * Project Name demo
 * File Name ResumableInfo
 * Package Name com.lljqiu.demo.file.stack
 * Create Time 2018/11/17
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.asdc.com.cn. All rights reserved.
 */
package com.lljqiu.demo.file.stack;

import com.lljqiu.demo.file.utils.HttpUtils;

import java.io.File;
import java.util.HashSet;

/**
 * Description
 *
 * @ClassName: ResumableInfo
 * @author: liujie
 * @date: 2018/11/17 23:34
 */
public class ResumableInfo {
    public int      resumableChunkSize;
    public long     resumableTotalSize;
    public String   resumableIdentifier;
    public String   resumableFilename;
    public String   resumableRelativePath;

    public String   generateUniqueIdentifier;
    public String   signature;

    public static class ResumableChunkNumber {
        public ResumableChunkNumber(int number) {
            this.number = number;
        }

        public int number;

        @Override
        public boolean equals(Object obj) {
            return obj instanceof ResumableChunkNumber
                    ? ((ResumableChunkNumber)obj).number == this.number : false;
        }

        @Override
        public int hashCode() {
            return number;
        }
    }

    /**Chunks uploaded*/
    public HashSet<ResumableChunkNumber> uploadedChunks = new HashSet<>();

    public String resumableFilePath;

    public boolean vaild(){
        if (resumableChunkSize < 0 || resumableTotalSize < 0
                || HttpUtils.isEmpty(resumableIdentifier)
                || HttpUtils.isEmpty(resumableFilename)
                || HttpUtils.isEmpty(resumableRelativePath)
                || HttpUtils.isEmpty(generateUniqueIdentifier)
                || HttpUtils.isEmpty(signature)) {
            return false;
        } else {
            return true;
        }
    }
    public boolean checkIfUploadFinished() {
        //check if upload finished
        int count = (int) Math.ceil(((double) resumableTotalSize) / ((double) resumableChunkSize));
        for(int i = 1; i < count; i ++) {
            if (!uploadedChunks.contains(new ResumableChunkNumber(i))) {
                return false;
            }
        }

        //Upload finished, change filename.
        File file = new File(resumableFilePath);
        String new_path = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - ".temp".length());
        file.renameTo(new File(new_path));
        return true;
    }
}
