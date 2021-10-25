package com.e590.excel;

import com.e590.excel.file.FileUtils;
import com.e590.excel.file.txt.TxtUtils;
import com.e590.excel.file.xls.ExcelUtils;
import com.e590.excel.utils.CommonUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * author: duke
 * date_time: 2021-10-25 18:56:11
 * description:
 */
public class Analyse {

    private static final String RESULT_FILE_NAME = "AnalyseResult";

    public static void analyseTxt(String[] paramArray)
            throws IllegalArgumentException, IOException {

        final ArrayList<String> dataList = new ArrayList<>();
        readFile(paramArray[0], dataList, paramArray[2], paramArray[3]);

        if (dataList.size() == 0) {
            throw new IllegalArgumentException("data is null or empty, please check origin data file format.");
        }

        // 在数据集合中，添加表头行
        dataList.add(0, paramArray[3]);

        String filePath = paramArray[0];
        File file = new File(filePath);
        if (file.isFile()) {
            // 获取父目录路径
            filePath = file.getParentFile().getAbsolutePath();
        }
        // 在当前目录下，生成目标后缀的 analyse 名称文件
        filePath += File.separator + RESULT_FILE_NAME + "." + paramArray[1];

        if ("txt".equalsIgnoreCase(paramArray[1])
                || "text".equalsIgnoreCase(paramArray[1])) {
            TxtUtils.writeText(filePath, dataList);
        } else {
            ExcelUtils.writeExcel(filePath, " Txt 数据分析", dataList);
        }
    }

    /**
     * 读取 txt 文件中的数据
     *
     * @param txtFile            文件路径 + 文件名
     * @param dataList           数据集合
     * @param allowFileSuffixStr 允许读取的文件类型扩展名
     * @param allowTableHeadStr  允许的数据列表 head 格式
     */
    private static void readFile(String txtFile,
                                 ArrayList<String> dataList,
                                 String allowFileSuffixStr,
                                 String allowTableHeadStr) {
        if (CommonUtils.isNullOrEmpty(txtFile)) {
            return;
        }

        FileUtils.recursiveCallReadFile(new File(txtFile), file -> {
            if (FileUtils.isNullOrNotExists(file)) {
                return;
            }
            // 获取文件后缀名
            String fileSuffix = FileUtils.getFileSuffixWithoutDot(file);
            // 获取文件名
            String fileName = file.getName();

            if (CommonUtils.isNullOrEmpty(fileName)
                    || (RESULT_FILE_NAME + "." + fileSuffix).equalsIgnoreCase(fileName)) {
                // 不要对刚才生成的 txt 文件二次读取分析
                return;
            }
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
                tempList = TxtUtils.readText(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (CommonUtils.isNullOrEmpty(tempList)) {
                return;
            }
            for (int i = 0; i < tempList.size(); i++) {
                String line = convertLine(tempList.get(i), allowTableHeadStr);
                if (CommonUtils.isNullOrEmpty(line)) {
                    continue;
                }
                dataList.add(line);
            }
        });

    }

    /**
     * 转换每一行数据
     *
     * @param line 行内容
     * @return 转换后的内容
     */
    private static String convertLine(String line, String allowTableHeadStr) {
        if (CommonUtils.isNullOrEmpty(line) || CommonUtils.isNullOrEmpty(allowTableHeadStr)) {
            return null;
        }
        line = line.replaceAll("：", ":")
                .replaceAll("，", ",")
                .replaceAll("\n", "")
                .replaceAll("\t", "")
                .trim();
        String split1 = ",";
        String split2 = ":";
        String[] array = line.split(split1);
        if (array.length != allowTableHeadStr.split(split1).length) {
            return null;
        }
        StringBuilder keyStr = new StringBuilder();
        StringBuilder valueStr = new StringBuilder();
        for (String item : array) {
            if (CommonUtils.isNullOrEmpty(item)) {
                continue;
            }
            String[] itemArray = new String[2];
            // yyyy-MM-dd HH:mm:ss:SSS 时间格式特殊
            int indexFirstColon = item.indexOf(split2);
            if (indexFirstColon == -1) {
                itemArray[0] = item;
                itemArray[1] = "";
            } else {
                itemArray[0] = item.substring(0, indexFirstColon);
                itemArray[1] = item.substring(indexFirstColon + 1);
            }
            keyStr.append(",")
                    .append(itemArray[0].trim());
            valueStr.append(",")
                    .append(itemArray[1].trim());
        }
        String keyResult = keyStr.substring(1);
        String valueResult = valueStr.substring(1);
        if (CommonUtils.isNullOrEmpty(keyResult) || CommonUtils.isNullOrEmpty(valueResult)) {
            return null;
        }
        if (!keyResult.equals(allowTableHeadStr)) {
            return null;
        }
        return valueResult;
    }

}
