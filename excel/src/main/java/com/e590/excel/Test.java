package com.e590.excel;

import java.util.ArrayList;

/**
 * author: duke
 * date_time: 2021-10-17 17:41:53
 * description:
 */
public class Test {

    public static void main(String[] args) {

        POIUtils.writeExcel("C:\\Users\\duke\\Desktop\\target.xls", null, makeList());
        System.out.println("写入 Excel 文件完成");
    }

    private static ArrayList<ArrayList<String>> makeList() {
        ArrayList<ArrayList<String>> dateSet = new ArrayList<>();

        ArrayList<String> row1 = new ArrayList<>();
        row1.add("表头1");
        row1.add("表头2");
        row1.add("表头3");
        ArrayList<String> row2 = new ArrayList<>();
        row2.add("内容1");
        row2.add("内容2");
        row2.add("内容3");
        ArrayList<String> row3 = new ArrayList<>();
        row3.add("内容11");
        row3.add("内容22");
        row3.add("内容33");
        dateSet.add(row1);
        dateSet.add(row2);
        dateSet.add(row3);


        return dateSet;
    }

}
