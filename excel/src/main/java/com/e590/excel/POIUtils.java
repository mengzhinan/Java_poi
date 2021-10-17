package com.e590.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * author: duke
 * date_time: 2021-10-17 17:31:22
 * description:
 */
public class POIUtils {

    public static void readTxt(String txtFile) {

    }

    public static void writeExcel(String excelFile, String txt) {
        try {
            //1.创建workbook
            Workbook workbook=new HSSFWorkbook();
            //2.根据workbook创建sheet
            Sheet sheet = workbook.createSheet("会员列表");
            //3.根据sheet创建row
            Row row1 = sheet.createRow(0);
            //4.根据row创建cell
            Cell cell1 = row1.createCell(0);
            //5.向cell里面设置值
            cell1.setCellValue("按键");
            //6.通过输出流写到文件里去
            FileOutputStream fos=new FileOutputStream("D:\\123\\01.xls");
            workbook.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
