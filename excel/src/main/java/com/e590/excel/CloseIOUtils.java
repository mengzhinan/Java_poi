package com.e590.excel;


import org.apache.poi.ss.usermodel.Workbook;

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
public class CloseIOUtils {

    public static void closeIO(Reader reader) {
        if (reader == null) {
            return;
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeIO(Writer writer) {
        if (writer == null) {
            return;
        }
        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeIO(OutputStream oStream) {
        if (oStream == null) {
            return;
        }
        try {
            oStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeIO(InputStream iStream) {
        if (iStream == null) {
            return;
        }
        try {
            iStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeIO(Workbook bk) {
        if (bk == null) {
            return;
        }
        try {
            bk.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
