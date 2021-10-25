package com.e590.excel.utils;

import java.io.File;
import java.util.List;

/**
 * author: duke
 * date_time: 2021-10-20 23:13:45
 * description:
 */
public class CommonUtils {

    public static boolean isNullOrEmpty(String txt) {
        return txt == null || txt.trim().length() == 0;
    }

    public static boolean isNullOrEmpty(List<String> list) {
        return list == null || list.size() == 0;
    }

    public static boolean isNullOrEmpty(String[] arr) {
        return arr == null || arr.length == 0;
    }

    public static boolean isNullOrEmpty(File[] arr) {
        return arr == null || arr.length == 0;
    }

}
