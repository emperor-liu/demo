/**
 * Project Name demo
 * File Name ItextPdfTest
 * Package Name com.lljqiu.demo.file
 * Create Time 2018/11/17
 * Create by name：liujie -- email: liujie@huxiaosu.com
 * Copyright © 2015, 2018, www.asdc.com.cn. All rights reserved.
 */
package com.lljqiu.demo.file.utils;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

/**
 * Description
 *
 * @ClassName: ItextPdfTest
 * @author: liujie
 * @date: 2018/11/17 15:40
 */
public class ItextPdfTest {
    private static final String DESKTOP_PATH = "/Users/liujie/Desktop";
    /**txt原始文件的路径*/
    private static final String TXT_FILE_PATH = DESKTOP_PATH + "/test.txt";
    /**生成的pdf文件路径*/
    private static final String PDF_FILE_PATH = DESKTOP_PATH + "/test.pdf";
    /**添加水印图片路径*/
    private static final String IMAGE_FILE_PATH = DESKTOP_PATH + "/test.jpg";
    /**生成临时文件前缀*/
    private static final String PRE_FIX = "tempFile";
    /**所有者密码*/
    private static final String OWNER_PASSWORD = "12345678";

    /**
     * txt文件转换为pdf文件
     *
     * @param txtFile       txt文件路径
     * @param pdfFile       pdf文件路径
     * @param userPassWord  用户密码
     * @param waterMarkName 水印内容
     * @param permission    操作权限
     */
    private static void generatePDFWithTxt(String txtFile, String pdfFile,
                                           String userPassWord,
                                           String waterMarkName, int permission) {
        try {
            // 生成临时文件
            File file = File.createTempFile(PRE_FIX, ".pdf");
            System.out.println("文件路径====" + file.getPath());
            // 创建pdf文件到临时文件
            if (createPDFFile(txtFile, file)) {
                // 增加水印和加密
                waterMark(file.getPath(), pdfFile, userPassWord,
                        waterMarkName, permission);
                System.out.println("end 创建成功.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建PDF文档
     *
     * @param txtFilePath txt文件路径（源文件）
     * @param file        pdf文件路径(新文件)
     */
    private static boolean createPDFFile(String txtFilePath, File file) {
        // 设置纸张
        System.out.println("step 1 : 设置纸张");
        Rectangle rect = new Rectangle(PageSize.A4);
        // 设置页码
        System.out.println("step 2 : 设置页码");
        HeaderFooter footer = new HeaderFooter(new Phrase("页码:",
                ItextPdfTest.setChineseFont()), true);
        //设置边框
        System.out.println("step 3 : 设置边框");
        footer.setBorder(Rectangle.NO_BORDER);
        // step1   创建对象
        System.out.println("step 4 : 创建对象");
        Document doc = new Document(rect, 50, 50, 50, 50);
        System.out.println("step 5 : 为对象设置页码");
        doc.setFooter(footer);
        try {
            //读取源文件
            System.out.println("step 6 : 读取源文件");
            FileReader fileRead = new FileReader(txtFilePath);
            System.out.println("step 7 : 转换文件流");
            BufferedReader read = new BufferedReader(fileRead);
            // 设置pdf文件生成路径 step2
            System.out.println("step 8 : 设置pdf文件生成路径 ");
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            // 打开pdf文件 step3
            System.out.println("step 9 : 打开pdf文件 ");
            doc.open();
            // 实例化Paragraph 获取写入pdf文件的内容，调用支持中文的方法. step4
            System.out.println("step 10 : 实例化Paragraph 获取写入pdf文件的内容，调用支持中文的方法. ");
            System.out.println("step 11 : 添加内容到pdf(这里将会按照txt文件的原始样式输出)");
            while (read.ready()) {
                // 添加内容到pdf(这里将会按照txt文件的原始样式输出)
                doc.add(new Paragraph(read.readLine(), ItextPdfTest
                        .setChineseFont()));
            }
            // 关闭pdf文件 step5
            System.out.println("step 11 : 关闭pdf文件");
            doc.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在pdf文件中添加水印
     *
     * @param inputFile     原始文件
     * @param outputFile    水印输出文件
     * @param waterMarkName 水印名字
     */
    private static void waterMark(String inputFile, String outputFile,
                                  String userPassWord,
                                  String waterMarkName, int permission) {
        try {
            System.out.println("添加水印 step1 : 拿到已写的文件");
            PdfReader reader = new PdfReader(inputFile);
            System.out.println("添加水印 step2 : 转换为可早做的PDF对象");
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
                    outputFile));
            // 设置密码
            System.out.println("添加水印 step3 : 设置密码（可要可不要）");
            stamper.setEncryption(userPassWord.getBytes(),
                    ItextPdfTest.OWNER_PASSWORD.getBytes(), permission, false);
            System.out.println("添加水印 step4 : 设置字体");
            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);

            int total = reader.getNumberOfPages() + 1;
            Image image = Image.getInstance(IMAGE_FILE_PATH);
            image.setAbsolutePosition(200, 400);
            PdfContentByte under;
            int j = waterMarkName.length();
            char c;
            for (int i = 1; i < total; i++) {
                int rise = 500;
                under = stamper.getUnderContent(i);
                // 添加图片
                under.addImage(image);
                under.beginText();
                under.setColorFill(Color.CYAN);
                under.setFontAndSize(base, 30);
                // 设置水印文字字体倾斜 开始
                if (j >= 15) {
                    under.setTextMatrix(10, 10);
                    for (int k = 0; k < j; k++) {
                        under.setTextRise(rise);
                        c = waterMarkName.charAt(k);
                        under.showText(c + "");
                        rise -= 20;
                    }
                } else {
                    under.setTextMatrix(180, 100);
                    for (int k = 0; k < j; k++) {
                        under.setTextRise(rise);
                        c = waterMarkName.charAt(k);
                        under.showText(c + "");
                        rise -= 18;
                    }
                }
                // 字体设置结束
                under.endText();
                // 画一个圆
                under.ellipse(250, 450, 350, 550);
                under.setLineWidth(1f);
                under.stroke();
            }
            stamper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置中文
     *
     * @return Font
     */
    private static Font setChineseFont() {
        BaseFont base;
        Font fontChinese;
        fontChinese = null;
        try {
            base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.EMBEDDED);
            fontChinese = new Font(base, 12, Font.NORMAL);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return fontChinese;
    }

    public static void main(String[] args) {
        generatePDFWithTxt(TXT_FILE_PATH, PDF_FILE_PATH, "123", "test", 16);
    }
}
