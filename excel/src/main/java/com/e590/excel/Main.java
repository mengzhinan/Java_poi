package com.e590.excel;

import com.e590.excel.file.txt.FileUtils;
import com.e590.excel.file.xls.ExcelUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * author: duke
 * date_time: 2021-10-17 17:41:53
 * description: Main
 */
public class Main {

    public static void main(String[] args) {
        try {
            if (args == null || args.length == 0) {
                System.out.println("请阅读程序用法，输入有效参数");
                return;
            }

            // 1、生成的文件保存的路径。
            // 2、生成的文件扩展名。Example: xls or csv
            // 3、允许的文件类型。Example："txt,text"
            // 4、是否是相同的表头格式。
            // Example: "记录时间,是否扫描,可用空间,垃圾,微信,QQ,钉钉,企业微信,应用清理,图片清理,视频清理,音频清理,安装包,大文件,重复文件";
            String[] paramArray = {
                    "",
                    "xls",
                    "txt,text",
                    "记录时间,是否扫描,可用空间,垃圾,微信,QQ,钉钉,企业微信,应用清理,图片清理,视频清理,音频清理,安装包,大文件,重复文件"
            };

            int argsSize = Math.min(args.length, paramArray.length);
            for (int i = 0; i < argsSize; i++) {
                paramArray[i] = args[i];
                System.out.println("Parsed args[i] = " + args[i]);
            }

            analyseTxt(paramArray);

            System.out.println("统计数据写入文件成功!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("统计数据写入文件失败. error = " + e.getMessage());
        }
    }

    private static void analyseTxt(String[] paramArray)
            throws IllegalArgumentException, IOException {
        final ArrayList<String> dataList = new ArrayList<>();
        FileUtils.readTxt(paramArray[0], dataList, paramArray[2], paramArray[3]);

        if (dataList.size() == 0) {
            throw new IllegalArgumentException("data is null or empty, please check origin data file format.");
        }

        dataList.add(0, paramArray[3]);

        String filePath = paramArray[0];
        File file = new File(filePath);
        if (file.isFile()) {
            // 获取父目录路径
            filePath = file.getParentFile().getAbsolutePath();
        }
        filePath += File.separator + "analyse." + paramArray[1];


        ExcelUtils.writeExcel(filePath, " Txt 数据分析", dataList);

    }

}
