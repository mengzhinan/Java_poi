package com.e590.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * author: duke
 * date_time: 2021-10-17 17:31:22
 * description:
 */
public class ExcelUtils {

    // 允许的文件类型
    private static final String[] ALLOW_FILE_SUFFIX = {"txt", "text"};
    // 是否是相同的表头格式
    private static final String ALLOW_TABLE_HEAD = "记录时间,是否扫描,可用空间,垃圾,微信,QQ,钉钉,企业微信,应用清理,图片清理,视频清理,音频清理,安装包,大文件,重复文件";

    // 全局保存解析到的数据
    private static final ArrayList<String> DATA_SET = new ArrayList<>();

    private static boolean isEmpty(String txt) {
        return txt == null || txt.trim().length() == 0;
    }

    /**
     * 获取文件的扩展名
     *
     * @param file 文件对象
     * @return 扩展名 (没有点)
     */
    private static String getFileSuffixWithoutDot(File file) {
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
    private static boolean createFileIfNotExists(String filePath) {
        if (isEmpty(filePath)) {
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
     * @param txtFile 文件路径 + 文件名
     * @return 文件内容集合
     */
    private static void readTxt(String txtFile) {
        if (isEmpty(txtFile)) {
            return;
        }
        File file = new File(txtFile);
        if (!file.exists()) {
            return;
        }
        reRead(file);
    }

    /**
     * 递归读取文件
     *
     * @param baseFile 文件路径 + 文件名
     * @return 内容
     */
    private static ArrayList<String> reRead(File baseFile) {
        if (baseFile == null || !baseFile.exists()) {
            return null;
        }
        if (baseFile.isFile()) {
            ArrayList<String> fileContent = readContent(baseFile);
            if (fileContent == null || fileContent.size() == 0) {
                return null;
            }
            return fileContent;
        }
        if (baseFile.isDirectory()) {
            File[] list = baseFile.listFiles();
            if (list == null || list.length == 0) {
                return null;
            }
            for (File file : list) {
                return reRead(file);
            }
        }
        return null;
    }

    private static ArrayList<String> readContent(File file) {
        if (file == null || !file.exists() || !file.canRead()) {
            return null;
        }
        // 获取文件后缀名
        String fileSuffix = getFileSuffixWithoutDot(file);
        if (isEmpty(fileSuffix)) {
            return null;
        }
        boolean isAllow = false;
        for (String allowFileSuffix : ALLOW_FILE_SUFFIX) {
            if (allowFileSuffix.equalsIgnoreCase(fileSuffix)) {
                isAllow = true;
                break;
            }
        }
        if (!isAllow) {
            return null;
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
                line = convertLine(line);
                if (!isEmpty(line)) {
                    DATA_SET.add(line);
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
        return null;
    }

    /**
     * 转换每一行数据
     *
     * @param line 行内容
     * @return 转换后的内容
     */
    private static String convertLine(String line) {
        line = line.replaceAll("：", ":")
                .replaceAll("，", ",")
                .replaceAll("\n", "")
                .replaceAll("\t", "")
                .trim();
        String[] array = line.split(",");
        if (array.length != ALLOW_TABLE_HEAD.split(",").length) {
            return null;
        }
        StringBuilder keyStr = new StringBuilder();
        StringBuilder valueStr = new StringBuilder();
        int index = 0;
        for (String item : array) {
            index++;
            if (isEmpty(item)) {
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
            if (itemArray.length != 2 || isEmpty(itemArray[0])) {
                continue;
            }
            keyStr.append(",")
                    .append(itemArray[0].trim());
            valueStr.append(",")
                    .append(itemArray[1].trim());
        }
        String keyResult = keyStr.substring(1);
        String valueResult = valueStr.substring(1);
        if (isEmpty(keyResult) || isEmpty(valueResult)) {
            return null;
        }
        if (!keyResult.equals(ALLOW_TABLE_HEAD)) {
            return null;
        }
        return valueResult;
    }

    /**
     * 写入 Excel 文件
     *
     * @param excelFile excel 文件路径 + 文件名
     * @param sheetName sheet 表名
     * @param dataSet   二维结构的数据集合
     */
    private static void writeExcel(String excelFile,
                                   String sheetName,
                                   ArrayList<String> dataSet)
            throws IOException, IllegalArgumentException {
        if (isEmpty(sheetName)) {
            sheetName = "sheet1";
        }
        boolean isXlsFileExists = createFileIfNotExists(excelFile);
        if (!isXlsFileExists) {
            throw new IllegalArgumentException(excelFile + " not exists Exception.");
        }
        if (dataSet == null || dataSet.size() == 0) {
            throw new IllegalArgumentException("dateSet is null or empty Exception.");
        }
        dataSet.add(0, ALLOW_TABLE_HEAD);
        FileOutputStream fos = null;
        Workbook workbook = null;
        try {
            fos = new FileOutputStream(excelFile);
            //1.创建workbook
            workbook = new HSSFWorkbook();
            //2.根据workbook创建sheet
            Sheet sheet = workbook.createSheet(sheetName);

            //3.根据sheet创建row
            //循环行
            int dateSetSize = dataSet.size();
            for (int line = 0; line < dateSetSize; line++) {
                Row row1 = sheet.createRow(line);
                // 循环列
                String columnText = dataSet.get(line);
                if (isEmpty(columnText)) {
                    continue;
                }
                String[] columnArray = columnText.split(",");
                int columnListSize = columnArray.length;
                for (int cellIndex = 0; cellIndex < columnListSize; cellIndex++) {
                    //4.根据row创建cell
                    Cell cell = row1.createCell(cellIndex);
                    //5.向cell里面设置值
                    String cellText = columnArray[cellIndex];
                    if (isEmpty(cellText)) {
                        cellText = "";
                    }
                    cell.setCellValue(cellText);
                }
            }

            //6.通过输出流写到文件里去
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
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

    public static void analyseTxt(String basePath, String sheetName) throws IOException {
        DATA_SET.clear();

        readTxt(basePath);

        if (DATA_SET.size() == 0) {
            throw new IllegalArgumentException("data is null or empty, please check origin data file format.");
        }

        String xlsPath = basePath;
        File file = new File(basePath);
        if (file.isFile()) {
            // 获取父目录路径
            xlsPath = file.getParentFile().getAbsolutePath();
        }
        xlsPath += File.separator + "analyse.xls";

        writeExcel(xlsPath, sheetName, DATA_SET);

    }

}
