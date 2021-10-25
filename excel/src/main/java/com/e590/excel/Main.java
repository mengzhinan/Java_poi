package com.e590.excel;

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
            // 3、允许读取的文件类型。Example："txt,text"
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
                if (paramArray[i] == null) {
                    paramArray[i] = "";
                }
                paramArray[i] = paramArray[i].replaceAll("“", "")
                        .replaceAll("’", "")
                        .replaceAll("\"", "")
                        .replaceAll("'", "")
                        .trim();
                System.out.println("Parsed args[" + i + "] = " + args[i]);
            }

            //读取、分析、生成文件
            Analyse.analyseTxt(paramArray);

            System.out.println("统计数据写入文件成功!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("统计数据写入文件失败. error = " + e.getMessage());
        }
    }

}
