/**
 * Project Name demo
 * File Name ResumableInfoStorage
 * Package Name com.lljqiu.demo.file.stack
 * Create Time 2018/11/17
 * Create by name：liujie -- email: jie_liu1@asdc.com.cn
 * Copyright © 2015, 2018, www.asdc.com.cn. All rights reserved.
 */
package com.lljqiu.demo.file.stack;

import java.util.HashMap;

/**
 * Description
 *
 * @ClassName: ResumableInfoStorage
 * @author: liujie
 * @date: 2018/11/17 23:37
 */
public class ResumableInfoStorage {
    private ResumableInfoStorage() {
    }
    private static ResumableInfoStorage sInstance;

    public static synchronized ResumableInfoStorage getInstance() {
        if (sInstance == null) {
            sInstance = new ResumableInfoStorage();
        }
        return sInstance;
    }

    /**resumableIdentifier --  ResumableInfo*/
    private HashMap<String, ResumableInfo> mMap = new HashMap<>();

    /**
     * Get ResumableInfo from mMap or Create a new one.
     * @param resumableChunkSize
     * @param resumableTotalSize
     * @param resumableIdentifier
     * @param resumableFilename
     * @param resumableRelativePath
     * @param resumableFilePath
     * @return
     */
    public synchronized ResumableInfo get(int resumableChunkSize, long resumableTotalSize,
                                          String resumableIdentifier, String resumableFilename,
                                          String resumableRelativePath, String resumableFilePath) {

        ResumableInfo info = mMap.get(resumableIdentifier);

        if (info == null) {
            info = new ResumableInfo();

            info.resumableChunkSize     = resumableChunkSize;
            info.resumableTotalSize     = resumableTotalSize;
            info.resumableIdentifier    = resumableIdentifier;
            info.resumableFilename      = resumableFilename;
            info.resumableRelativePath  = resumableRelativePath;
            info.resumableFilePath      = resumableFilePath;

            mMap.put(resumableIdentifier, info);
        }
        return info;
    }

    public synchronized ResumableInfo get(int resumableChunkSize, long resumableTotalSize,
                                          String resumableIdentifier, String resumableFilename,
                                          String resumableRelativePath, String resumableFilePath,String generateUniqueIdentifier,String signature) {

        ResumableInfo info = mMap.get(resumableIdentifier);

        if (info == null) {
            info = new ResumableInfo();

            info.resumableChunkSize     = resumableChunkSize;
            info.resumableTotalSize     = resumableTotalSize;
            info.resumableIdentifier    = resumableIdentifier;
            info.resumableFilename      = resumableFilename;
            info.resumableRelativePath  = resumableRelativePath;
            info.resumableFilePath      = resumableFilePath;
            info.generateUniqueIdentifier = generateUniqueIdentifier;
            info.signature              = signature;

            mMap.put(resumableIdentifier, info);
        }
        return info;
    }

    /**
     * ResumableInfo
     * @param info
     */
    public void remove(ResumableInfo info) {
        mMap.remove(info.resumableIdentifier);
    }
}
