package com.e590.excel.file.txt;

import com.e590.excel.file.FileUtils;
import com.e590.excel.utils.CommonUtils;
import com.e590.excel.utils.ParseDataUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * author: duke
 * date_time: 2021-10-20 23:12:12
 * description:
 */
public class TxtUtils {

    /**
     * 获取文件的扩展名
     *
     * @param file 文件对象
     * @return 扩展名 (没有点)
     */
    public static String getFileSuffixWithoutDot(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        String name = file.getName();
        if (!name.contains(".")) {
            return null;
        }
        return name.substring(name.lastIndexOf(".") + 1);
    }

    /**
     * 创建文件和目录，最后判断文件是否存在
     *
     * @param filePath 文件路径 + 文件名
     * @return 文件是否存在
     */
    public static boolean createFileIfNotExists(String filePath) {
        if (CommonUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent == null) {
            return false;
        }
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.exists();
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
    public static void readTxt(String txtFile,
                               ArrayList<String> dataList,
                               String allowFileSuffixStr,
                               String allowTableHeadStr) {
        if (CommonUtils.isEmpty(txtFile)) {
            return;
        }
        reRead(new File(txtFile), dataList, allowFileSuffixStr, allowTableHeadStr);
    }

    /**
     * 递归读取文件
     *
     * @param baseFile 文件路径 + 文件名
     * @return 内容
     */
    private static void reRead(File baseFile,
                               ArrayList<String> dataList,
                               String allowFileSuffixStr,
                               String allowTableHeadStr) {
        if (baseFile == null || !baseFile.exists()) {
            return;
        }
        if (baseFile.isFile()) {
            // 获取文件后缀名
            String fileSuffix = getFileSuffixWithoutDot(baseFile);
            if (CommonUtils.isEmpty(fileSuffix)) {
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
                if (CommonUtils.isEmpty(line)) {
                    continue;
                }
                dataList.add(line);
            }

        }
        if (baseFile.isDirectory()) {
            File[] list = baseFile.listFiles();
            if (list == null || list.length == 0) {
                return;
            }
            for (File file : list) {
                reRead(file, dataList, allowFileSuffixStr, allowTableHeadStr);
            }
        }
    }

    /**
     * 读取文本文件
     *
     * @param file 文件路径 + 扩展名
     * @return 数据集合
     */
    public static ArrayList<String> readText(File file)
            throws IOException {
        if (file == null || !file.exists()) {
            return null;
        }
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            ArrayList<String> list = new ArrayList<>();
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String line = null;
            // != null，表示未读到文件末尾
            while ((line = br.readLine()) != null) {
                if (CommonUtils.isEmpty(line)) {
                    // 读到空字符串，忽略
                    continue;
                }
                list.add(line);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e);
        } finally {
            FileUtils.closeIO(br);
            FileUtils.closeIO(isr);
            FileUtils.closeIO(fis);
        }
    }

    /**
     * 写入文本文件
     *
     * @param filePath 文件路径 + 扩展名
     * @param dataList 数据集合
     */
    public static void writeText(String filePath, ArrayList<String> dataList)
            throws IOException, IllegalArgumentException {
        if (CommonUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException(filePath + " to be write is null or empty exception.");
        }
        if (dataList == null || dataList.size() == 0) {
            throw new IllegalArgumentException("data list will be write is empty exception.");
        }
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            createFileIfNotExists(filePath);

            fos = new FileOutputStream(filePath);
            osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);
            int size = dataList.size();
            for (int i = 0; i < size; i++) {
                bw.write(dataList.get(i));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e);
        } finally {
            FileUtils.closeIO(bw);
            FileUtils.closeIO(osw);
            FileUtils.closeIO(fos);
        }

    }

}
