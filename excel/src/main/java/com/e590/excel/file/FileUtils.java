package com.e590.excel.file;


import com.e590.excel.utils.CommonUtils;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * author: duke
 * dateTime: 2021-10-22 09:06:40
 * description: 功能描述：
 */
public class FileUtils {

    /**
     * 文件为 null，或者不存在
     *
     * @param file 文件
     * @return 是否存在
     */
    public static boolean isNullOrNotExists(File file) {
        return file == null || !file.exists();
    }

    /**
     * 获取文件的扩展名
     *
     * @param file 文件对象
     * @return 扩展名 (没有点)
     */
    public static String getFileSuffixWithoutDot(File file) {
        if (isNullOrNotExists(file)) {
            return null;
        }
        String name = file.getName();
        if (CommonUtils.isNullOrEmpty(name) || !name.contains(".")) {
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
        if (CommonUtils.isNullOrEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent == null) {
            return false;
        }
        if (!parent.exists() && !parent.mkdirs()) {
            // 如果父目录不存在，且创建父目录失败
            return false;
        }
        try {
            if (!file.exists() && !file.createNewFile()) {
                // 如果目标文件不存在，且创建文件失败
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return !isNullOrNotExists(file);
    }

    /**
     * 递归读取文件
     *
     * @param file 文件路径 + 文件名
     */
    public static void recursiveCallReadFile(File file,
                                             OnReadFileCallback onReadFileCallback) {
        if (isNullOrNotExists(file) || onReadFileCallback == null) {
            return;
        }
        if (file.isFile()) {
            onReadFileCallback.onReadFile(file);
            return;
        }
        if (file.isDirectory()) {
            File[] fileArr = file.listFiles();
            if (CommonUtils.isNullOrEmpty(fileArr)) {
                return;
            }
            for (File childFile : fileArr) {
                // 递归查找文件
                recursiveCallReadFile(childFile, onReadFileCallback);
            }
        }
    }

    //------ close io stream -------------------------------

    public static void closeIO(Reader reader) {
        if (reader == null) {
            return;
        }
        try {
            reader.close();
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }

    public static void closeIO(Writer writer) {
        if (writer == null) {
            return;
        }
        try {
            writer.flush();
        } catch (IOException e) {
            // e.printStackTrace();
        }
        try {
            writer.close();
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }

    public static void closeIO(OutputStream oStream) {
        if (oStream == null) {
            return;
        }
        try {
            oStream.flush();
        } catch (IOException e) {
            // e.printStackTrace();
        }
        try {
            oStream.close();
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }

    public static void closeIO(InputStream iStream) {
        if (iStream == null) {
            return;
        }
        try {
            iStream.close();
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }

    public static void closeIO(Workbook bk) {
        if (bk == null) {
            return;
        }
        try {
            bk.close();
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }

}
