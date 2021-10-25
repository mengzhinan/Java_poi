package com.e590.excel.file.txt;

import com.e590.excel.file.FileUtils;
import com.e590.excel.utils.CommonUtils;

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
     * 读取文本文件
     *
     * @param file 文件路径 + 扩展名
     * @return 数据集合
     */
    public static ArrayList<String> readText(File file)
            throws IOException {
        if (FileUtils.isNullOrNotExists(file)) {
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
            String line;
            // != null，表示未读到文件末尾
            while ((line = br.readLine()) != null) {
                if (CommonUtils.isNullOrEmpty(line)) {
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
        if (CommonUtils.isNullOrEmpty(filePath)) {
            throw new IllegalArgumentException(filePath + " to be write is null or empty exception.");
        }
        if (CommonUtils.isNullOrEmpty(dataList)) {
            throw new IllegalArgumentException("data list will be write is empty exception.");
        }
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            FileUtils.createFileIfNotExists(filePath);

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
