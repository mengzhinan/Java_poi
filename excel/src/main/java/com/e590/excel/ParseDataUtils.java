package com.e590.excel;

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
        line = line.replaceAll("：", ":")
                .replaceAll("，", ",")
                .replaceAll("\n", "")
                .replaceAll("\t", "")
                .trim();
        String[] array = line.split(",");
        if (array.length != allowTableHeadStr.split(",").length) {
            return null;
        }
        StringBuilder keyStr = new StringBuilder();
        StringBuilder valueStr = new StringBuilder();
        int index = 0;
        for (String item : array) {
            index++;
            if (CommonUtils.isEmpty(item)) {
                continue;
            }
            String[] itemArray = new String[2];
            if (index == 1) {
                // yyyy-MM-dd HH:mm:ss:SSS 时间格式特殊
                int indexFirstColon = item.indexOf(":");
                if (indexFirstColon == -1) {
                    continue;
                }
                itemArray[0] = item.substring(0, indexFirstColon);
                itemArray[1] = item.substring(indexFirstColon + 1);
            } else {
                itemArray = item.split(":");
            }
            if (itemArray.length != 2 || CommonUtils.isEmpty(itemArray[0])) {
                continue;
            }
            keyStr.append(",")
                    .append(itemArray[0].trim());
            valueStr.append(",")
                    .append(itemArray[1].trim());
        }
        String keyResult = keyStr.substring(1);
        String valueResult = valueStr.substring(1);
        if (CommonUtils.isEmpty(keyResult) || CommonUtils.isEmpty(valueResult)) {
            return null;
        }
        if (!keyResult.equals(allowTableHeadStr)) {
            return null;
        }
        return valueResult;
    }

}
