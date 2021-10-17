package com.e590.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * author: duke
 * date_time: 2021-10-17 17:31:22
 * description:
 */
public class ExcelUtils {

    // 允许的文件类型
    private static String[] ALLOW_FILE_SUFFIX = {"txt", "text"};
    // 是否是相同的表头格式
    private static String ALLOW_TABLE_HEAD = "记录时间,是否扫描,可用空间,垃圾,微信,QQ,钉钉,企业微信,应用清理,图片清理,视频清理,音频清理,安装包,大文件,重复文件";


    private static boolean isEmpty(String txt) {
        return txt == null || txt.trim().length() == 0;
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
    public static ArrayList<ArrayList<String>> readTxt(String txtFile) {
        if (isEmpty(txtFile)) {
            return null;
        }
        File file = new File(txtFile);
        if (!file.exists()) {
            return null;
        }
        ArrayList<ArrayList<String>> dateSet = new ArrayList<>();
        ArrayList<ArrayList<String>> tempList = reRead(file);
        if (tempList != null && tempList.size() > 0) {
            dateSet.addAll(tempList);
        }
        return dateSet;
    }

    private static ArrayList<ArrayList<String>> reRead(File baseFile) {
        if (!baseFile.exists()) {
            return null;
        }
        if (baseFile.isFile()) {
            if ()
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
    }

    /**
     * 写入 Excel 文件
     *
     * @param excelFile excel 文件路径 + 文件名
     * @param sheetName sheet 表名
     * @param dateSet   二维结构的数据集合
     */
    public static void writeExcel(String excelFile,
                                  String sheetName,
                                  ArrayList<ArrayList<String>> dateSet)
            throws IOException, IllegalArgumentException {
        if (isEmpty(sheetName)) {
            sheetName = "sheet1";
        }
        boolean isXlsFileExists = createFileIfNotExists(excelFile);
        if (!isXlsFileExists) {
            throw new IllegalArgumentException(excelFile + " not exists Exception.");
        }
        FileOutputStream fos = null;
        Workbook workbook = null;
        try {
            fos = new FileOutputStream(excelFile);
            //1.创建workbook
            workbook = new HSSFWorkbook();
            //2.根据workbook创建sheet
            Sheet sheet = workbook.createSheet(sheetName);

            //3.根据sheet创建row
            if (dateSet != null && dateSet.size() > 0) {
                //循环行
                int dateSetSize = dateSet.size();
                for (int line = 0; line < dateSetSize; line++) {
                    Row row1 = sheet.createRow(line);
                    // 循环列
                    ArrayList<String> columnList = dateSet.get(line);
                    if (columnList != null && columnList.size() > 0) {
                        int columnListSize = columnList.size();
                        for (int cell = 0; cell < columnListSize; cell++) {
                            //4.根据row创建cell
                            Cell cell1 = row1.createCell(cell);
                            //5.向cell里面设置值
                            cell1.setCellValue(columnList.get(cell));
                        }
                    }
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

}
