package com.e590.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * author: duke
 * date_time: 2021-10-17 17:41:53
 * description: Main
 */
public class Test {

    public static void main(String[] args) {
        try {
            if (args == null || args.length == 0) {
                System.out.println("请阅读程序用法，输入有效参数");
                return;
            }

            // 生成的文件保存的路径
            String firstBasePath = "";

            // 生成的文件扩展名
            // exp: xls or csv
            String secondWriteFileSuffixNoDot = "xls";

            // 允许的文件类型
            // exp："txt,text"
            String thirdAllowFileSuffixNoDot = "txt,text";

            // 是否是相同的表头格式
            // exp: "记录时间,是否扫描,可用空间,垃圾,微信,QQ,钉钉,企业微信,应用清理,图片清理,视频清理,音频清理,安装包,大文件,重复文件";
            String fourthAllowDataListHeadStr = "记录时间,是否扫描,可用空间,垃圾,微信,QQ,钉钉,企业微信,应用清理,图片清理,视频清理,音频清理,安装包,大文件,重复文件";

            if (args.length == 1) {
                firstBasePath = args[0];
                System.out.println("解析到参数 args[0] = " + args[0]);
            } else if (args.length == 2) {
                firstBasePath = args[0];
                secondWriteFileSuffixNoDot = args[1];
                System.out.println("解析到参数 args[0] = " + args[0]);
                System.out.println("解析到参数 args[1] = " + args[1]);
            } else if (args.length == 3) {
                firstBasePath = args[0];
                secondWriteFileSuffixNoDot = args[1];
                thirdAllowFileSuffixNoDot = args[2];
                System.out.println("解析到参数 args[0] = " + args[0]);
                System.out.println("解析到参数 args[1] = " + args[1]);
                System.out.println("解析到参数 args[2] = " + args[2]);
            } else if (args.length == 4) {
                firstBasePath = args[0];
                secondWriteFileSuffixNoDot = args[1];
                thirdAllowFileSuffixNoDot = args[2];
                fourthAllowDataListHeadStr = args[3];
                System.out.println("解析到参数 args[0] = " + args[0]);
                System.out.println("解析到参数 args[1] = " + args[1]);
                System.out.println("解析到参数 args[2] = " + args[2]);
                System.out.println("解析到参数 args[3] = " + args[3]);
            }

            analyseTxt(firstBasePath, "txt 数据统计");

            System.out.println("统计数据写入文件成功!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("统计数据写入文件失败. error = " + e.getMessage());
        }
    }

    public static void analyseTxt(String filePath) throws Exception {
        final ArrayList<String> dataList = new ArrayList<>();
        FileUtils.readTxt(filePath, dataList);
        DATA_SET.clear();

        readTxt(basePath);

        if (DATA_SET.size() == 0) {
            throw new IllegalArgumentException("data is null or empty, please check origin data file format.");
        }

        String xlsPath = basePath;
        File file = new File(basePath);
        if (file.isFile()) {
            // 获取父目录路径
            xlsPath = file.getParentFile().getAbsolutePath();
        }
        xlsPath += File.separator + "analyse.xls";

        ExcelUtils.writeExcel(xlsPath, sheetName, DATA_SET);

    }

}
