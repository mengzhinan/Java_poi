package com.e590.excel.utils;

/**
 * author: duke
 * date_time: 2021-10-20 23:12:00
 * description:
 */
public class ParseDataUtils {

    /**
     * 转换每一行数据
     *
     * @param line 行内容
     * @return 转换后的内容
     */
    public static String convertLine(String line, String allowTableHeadStr) {
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
