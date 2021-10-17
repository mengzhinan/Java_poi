package com.e590.excel;

import java.util.ArrayList;

/**
 * author: duke
 * date_time: 2021-10-17 17:41:53
 * description:
 */
public class Test {

    public static void main(String[] args) {
        try {
            ArrayList<String> data = ExcelUtils.readTxt("C:\\Users\\duke\\Desktop\\a.txt");
            if (data == null || data.size() == 0) {
                throw new IllegalArgumentException("data is null or empty, please check origin data file format.");
            }
            ExcelUtils.writeExcel("C:\\Users\\duke\\Desktop\\target.xls", "文件分析", data);
            System.out.println("写入 Excel 文件成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("写入 Excel 文件失败");
        }
    }

}
