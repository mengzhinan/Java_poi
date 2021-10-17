package com.e590.excel;

/**
 * author: duke
 * date_time: 2021-10-17 17:41:53
 * description: Main
 */
public class Test {

    public static void main(String[] args) {
        try {
            if (args == null || args.length == 0) {
                System.out.println("请输入待解析 txt 文件的根目录");
            }

            String basePath = args[0];
            System.out.println("解析到参数 args[0](basePath) = " + basePath);

            ExcelUtils.analyseTxt(basePath, "txt 数据统计");

            System.out.println("写入 Excel 文件成功!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("写入 Excel 文件失败. error = " + e.getMessage());
        }
    }

}
