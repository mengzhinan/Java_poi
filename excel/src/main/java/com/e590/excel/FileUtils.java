package com.e590.excel;

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
public class FileUtils {

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
     * @param allowTableHeadStr  允许的数据列表 head 格式
     * @param allowFileSuffixArr 允许读取的文件类型扩展名
     * @return 文件内容集合
     */
    public static void readTxt(String txtFile,
                               ArrayList<String> dataList,
                               String allowTableHeadStr,
                               String[] allowFileSuffixArr) {
        if (CommonUtils.isEmpty(txtFile)) {
            return;
        }
        reRead(new File(txtFile), dataList, allowTableHeadStr, allowFileSuffixArr);
    }

    /**
     * 递归读取文件
     *
     * @param baseFile 文件路径 + 文件名
     * @return 内容
     */
    private static void reRead(File baseFile,
                               ArrayList<String> dataList,
                               String allowTableHeadStr,
                               String[] allowFileSuffixArr) {
        if (baseFile == null || !baseFile.exists()) {
            return;
        }
        if (baseFile.isFile()) {
            readContent(baseFile, dataList, allowTableHeadStr, allowFileSuffixArr);
        }
        if (baseFile.isDirectory()) {
            File[] list = baseFile.listFiles();
            if (list == null || list.length == 0) {
                return;
            }
            for (File file : list) {
                reRead(file, dataList, allowTableHeadStr, allowFileSuffixArr);
            }
        }
    }

    private static void readContent(File file,
                                    ArrayList<String> dataList,
                                    String allowTableHeadStr,
                                    String[] allowFileSuffixArr) {
        if (file == null || !file.exists()) {
            return;
        }
        // 获取文件后缀名
        String fileSuffix = getFileSuffixWithoutDot(file);
        if (CommonUtils.isEmpty(fileSuffix)) {
            return;
        }
        boolean isAllow = false;
        for (String allowFileSuffix : allowFileSuffixArr) {
            if (allowFileSuffix.equalsIgnoreCase(fileSuffix)) {
                isAllow = true;
                break;
            }
        }
        if (!isAllow) {
            return;
        }
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                line = ParseDataUtils.convertLine(line, allowTableHeadStr);
                if (!CommonUtils.isEmpty(line)) {
                    dataList.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 写入文本文件
     *
     * @param filePath 文件路径 + 扩展名
     * @param dataList 数据集合
     */
    public static void writeFile(String filePath, ArrayList<String> dataList) {
        if (CommonUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException("File path to be write is null or empty exception.");
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (bw != null) {
                try {
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (osw != null) {
                try {
                    osw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
