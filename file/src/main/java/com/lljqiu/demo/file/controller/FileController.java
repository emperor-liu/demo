/**
 * Project Name demo
 * File Name FileController
 * Package Name com.lljqiu.demo.file.controller
 * Create Time 2018/11/17
 * Create by name：liujie -- email: jie_liu1@asdc.com.cn
 * Copyright © 2015, 2018, www.asdc.com.cn. All rights reserved.
 */
package com.lljqiu.demo.file.controller;

import com.lljqiu.demo.file.stack.ResumableInfo;
import com.lljqiu.demo.file.stack.ResumableInfoStorage;
import com.lljqiu.demo.file.utils.HttpUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * Description
 *
 * @ClassName: FileController
 * @author: liujie
 * @date: 2018/11/17 22:25
 */
@Controller
public class FileController {

    @GetMapping("/uploadPage")
    public String uploadPage(){
        return "uploadPage";
    }


    private final String UPLOAD_DIR = "/Users/liujie/Desktop/upload/";

//    @GetMapping("/upload")
//    @ResponseBody
//    public String getUpload(HttpServletRequest request,HttpServletResponse response) throws ServletException {
//        int resumableChunkNumber        = getResumableChunkNumber(request);
//        ResumableInfo info = getResumableInfo(request);
//
//        if (info.uploadedChunks.contains(new ResumableInfo.ResumableChunkNumber(resumableChunkNumber))) {
//            return "upload...";
//        } else {
//
//            return "not fount";
//        }
//    }

    @RequestMapping(value = "/upload", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.OPTIONS})
    @ResponseBody
    public String uploadInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
        int resumableChunkNumber        = getResumableChunkNumber(request);

        ResumableInfo info = getResumableInfo(request);
        if(request.getMethod().equalsIgnoreCase(RequestMethod.GET.name())){
            if (info.uploadedChunks.contains(new ResumableInfo.ResumableChunkNumber(resumableChunkNumber))) {
                return "upload...";
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return "not fount";
            }
        }else{
            RandomAccessFile raf = new RandomAccessFile(info.resumableFilePath, "rw");

            //Seek to position
            raf.seek((resumableChunkNumber - 1) * (long)info.resumableChunkSize);

            //Save to file
            InputStream is = request.getInputStream();
            long readed = 0;
            long content_length = request.getContentLength();
            byte[] bytes = new byte[1024 * 100];
            while(readed < content_length) {
                int r = is.read(bytes);
                if (r < 0)  {
                    break;
                }
                raf.write(bytes, 0, r);
                readed += r;
            }
            raf.close();
            //Mark as uploaded.
            info.uploadedChunks.add(new ResumableInfo.ResumableChunkNumber(resumableChunkNumber));
            if (info.checkIfUploadFinished()) {
                // 处理业务逻辑
                ResumableInfoStorage.getInstance().remove(info);
                return "SUCCESS";
            } else {
                return "upload...";
            }
        }

    }

    private int getResumableChunkNumber(HttpServletRequest request) {
        return HttpUtils.toInt(request.getParameter("resumableChunkNumber"), -1);
    }

    private ResumableInfo getResumableInfo(HttpServletRequest request) throws ServletException {
        String base_dir = UPLOAD_DIR;

        int resumableChunkSize = HttpUtils.toInt(request.getParameter("resumableChunkSize"), -1);
        long resumableTotalSize = HttpUtils.toLong(request.getParameter("resumableTotalSize"), -1);
        String resumableIdentifier = request.getParameter("resumableIdentifier");
        String resumableFilename = request.getParameter("resumableFilename");
        String resumableRelativePath = request.getParameter("resumableRelativePath");
        String generateUniqueIdentifier = request.getParameter("generateUniqueIdentifier");
        String signature = request.getParameter("signature");
        // Here we add a ".temp" to every upload file to indicate NON-FINISHED
        new File(base_dir).mkdir();
        String resumableFilePath = new File(base_dir, resumableFilename).getAbsolutePath() + ".temp";

        ResumableInfoStorage storage = ResumableInfoStorage.getInstance();

        ResumableInfo info = storage.get(resumableChunkSize, resumableTotalSize, resumableIdentifier, resumableFilename,
                resumableRelativePath, resumableFilePath, generateUniqueIdentifier, signature);
        if (!info.vaild()) {
            storage.remove(info);
            throw new ServletException("Invalid request params.");
        }
        return info;
    }
}
