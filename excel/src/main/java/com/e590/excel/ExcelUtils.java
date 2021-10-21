package com.e590.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * author: duke
 * date_time: 2021-10-17 17:31:22
 * description:
 */
public class ExcelUtils {

    /**
     * 写入 Excel 文件
     *
     * @param excelFile         excel 文件路径 + 文件名
     * @param sheetName         sheet 表名
     * @param dataSet           二维结构的数据集合
     * @param allowTableHeadStr 允许的列表头部格式字符串
     */
    public static void writeExcel(String excelFile,
                                  String sheetName,
                                  ArrayList<String> dataSet,
                                  String allowTableHeadStr)
            throws IOException, IllegalArgumentException {
        if (CommonUtils.isEmpty(sheetName)) {
            sheetName = "sheet1";
        }
        boolean isXlsFileExists = FileUtils.createFileIfNotExists(excelFile);
        if (!isXlsFileExists) {
            throw new IllegalArgumentException(excelFile + " not exists Exception.");
        }
        if (dataSet == null || dataSet.size() == 0) {
            throw new IllegalArgumentException("dateSet is null or empty Exception.");
        }
        dataSet.add(0, allowTableHeadStr);
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
                if (CommonUtils.isEmpty(columnText)) {
                    continue;
                }
                String[] columnArray = columnText.split(",");
                int columnListSize = columnArray.length;
                for (int cellIndex = 0; cellIndex < columnListSize; cellIndex++) {
                    //4.根据row创建cell
                    Cell cell = row1.createCell(cellIndex);
                    //5.向cell里面设置值
                    String cellText = columnArray[cellIndex];
                    if (CommonUtils.isEmpty(cellText)) {
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

}
