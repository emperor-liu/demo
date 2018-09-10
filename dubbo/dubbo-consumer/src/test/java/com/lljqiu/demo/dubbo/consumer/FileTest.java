/**
 * Project Name dubbo-consumer
 * File Name FileTest.java
 * Package Name com.lljqiu.demo.dubbo.consumer
 * Create Time 2018年9月10日
 * Create by name：liujie -- email: liujie@lljqiu.com
 * Copyright © 2015, 2017, www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.demo.dubbo.consumer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** 
 * ClassName: FileTest.java <br>
 * Description: <br>
 * Create by: name：liujie <br>email: liujie@lljqiu.com <br>
 * Create Time: 2018年9月10日<br>
 */
public class FileTest {
	
	public static void main(String[] args) throws Exception {
//		String filePath = "/Users/liujie/Desktop/uploadDeviceId.log";
//		File file = new File(filePath);
//		BufferedReader reader = new BufferedReader(new FileReader(file));
//		String tempString = null;
//		System.out.println("开始时间="+new Date().getTime());
//        Set<String> set = new HashSet<String>();
//        while ((tempString = reader.readLine()) != null) {
//        	
//        	int statusIndex = tempString.indexOf("status");
//        	String statusOk = tempString.substring(statusIndex+7, statusIndex+9);
//        	if("ok".equalsIgnoreCase(statusOk)){
//        		int indexOf = tempString.indexOf("deviceId");
//            	set.add(tempString.substring(indexOf+9, indexOf+29));
//        	}
//        	
//        }
//        reader.close();
//        System.out.println("结束时间="+new Date().getTime());
//        System.out.println(set.size());
        
        diffFileDevice();


	}
	
	
	public static void diffFileDevice() throws Exception{
		String filePath = "/Users/liujie/Desktop/opensips.log";
		String openSipFile = "/Users/liujie/Desktop/uploadDeviceStatus.log";
		File file = new File(filePath);
		BufferedReader readerW = new BufferedReader(new FileReader(file));
		String tempString = null;
		List<String> listW = new ArrayList<String>();
        while ((tempString = readerW.readLine()) != null) {
//        	int indexOf = tempString.indexOf("deviceId");
        	listW.add(tempString);
        }
        readerW.close();
        System.out.println(listW);
        
        File fileOpen = new File(openSipFile);
        BufferedReader readerO = new BufferedReader(new FileReader(fileOpen));
        String tempStringO = null;
        Set<String> listO = new HashSet<String>();
        while ((tempStringO = readerO.readLine()) != null) {
        	listO.add(tempStringO);
        }
        readerO.close();
        System.out.println(listO);
        
        listW.removeAll(listO);
        System.out.println(listW);
        
	}

}
