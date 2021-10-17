package com.e590.excel;

import java.io.IOException;
import java.util.ArrayList;

/**
 * author: duke
 * date_time: 2021-10-17 17:41:53
 * description:
 */
public class Test {

    public static void main(String[] args) {

        try {

            ArrayList<String> date = ExcelUtils.readTxt("C:\\Users\\duke\\Desktop\\a.txt");


            ExcelUtils.writeExcel("C:\\Users\\duke\\Desktop\\target.xls", "文件分析", makeList());
            System.out.println("写入 Excel 文件成功");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("写入 Excel 文件失败");
        }
    }

    private static ArrayList<String> makeList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("表头1,表头2,表头3");
        list.add("内容1,内容2,内容3");
        list.add("内容11,内容22,内容33");
        return list;
    }

}
