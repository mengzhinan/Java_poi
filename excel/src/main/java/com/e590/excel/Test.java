package com.e590.excel;

import java.io.File;
import java.util.ArrayList;

/**
 * author: duke date_time: 2021-10-17 17:41:53 description:
 */
public class Test {

	public static void main(String[] args) {
		if (args == null || args.length == 0) {
			System.out.println("请输入待解析 txt 文件的根目录");
		}
		String basePath = args[0];
		System.out.println("解析到参数 args[0] = " + basePath);
		try {
			ArrayList<String> data = ExcelUtils.readTxt(basePath);
			if (data == null || data.size() == 0) {
				throw new IllegalArgumentException("data is null or empty, please check origin data file format.");
			}
			File file = new File(basePath);
			if (file.isFile()) {
				basePath = file.getParentFile().getAbsolutePath();
			}
			ExcelUtils.writeExcel(basePath + File.separator + "analyse.xls", "数据分析结果", data);
			System.out.println("写入 Excel 文件成功");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("写入 Excel 文件失败, error = " + e.getMessage());
		}
	}

}
