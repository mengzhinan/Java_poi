package com.e590.excel;

import com.e590.excel.file.FileUtils;
import com.e590.excel.file.xls.ExcelUtils;
import com.e590.excel.utils.CommonUtils;
import com.e590.excel.utils.ParseDataUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * author: duke
 * date_time: 2021-10-25 18:56:11
 * description:
 */
public class Analyse {

    public static void analyseTxt(String[] paramArray)
            throws IllegalArgumentException, IOException {
        final ArrayList<String> dataList = new ArrayList<>();
        readFile(paramArray[0], dataList, paramArray[2], paramArray[3]);

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

    /**
     * 读取 txt 文件中的数据
     *
     * @param txtFile            文件路径 + 文件名
     * @param dataList           数据集合
     * @param allowFileSuffixStr 允许读取的文件类型扩展名
     * @param allowTableHeadStr  允许的数据列表 head 格式
     * @return 文件内容集合
     */
    private static void readFile(String txtFile,
                                 ArrayList<String> dataList,
                                 String allowFileSuffixStr,
                                 String allowTableHeadStr){
        if (CommonUtils.isNullOrEmpty(txtFile)) {
            return;
        }
        reRead(new File(txtFile), dataList, allowFileSuffixStr, allowTableHeadStr);

        // 获取文件后缀名
        String fileSuffix = FileUtils.getFileSuffixWithoutDot(baseFile);
        if (CommonUtils.isNullOrEmpty(fileSuffix)) {
            return;
        }
        boolean isAllow = false;
        String[] allowSuffixArr = allowFileSuffixStr.split(",");
        for (String allowFileSuffix : allowSuffixArr) {
            if (allowFileSuffix.equalsIgnoreCase(fileSuffix)) {
                isAllow = true;
                break;
            }
        }
        if (!isAllow) {
            return;
        }
        ArrayList<String> tempList = null;
        try {
            tempList = readText(baseFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tempList == null || tempList.size() == 0) {
            return;
        }
        for (int i = 0; i < tempList.size(); i++) {
            String line = ParseDataUtils.convertLine(tempList.get(i), allowTableHeadStr);
            if (CommonUtils.isNullOrEmpty(line)) {
                continue;
            }
            dataList.add(line);
        }
    }

}
