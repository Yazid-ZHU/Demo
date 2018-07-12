package com.asiainfo.util;

import org.apache.commons.io.IOUtils;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: zhuyj
 * @Date: 2018/7/11/011 18:02
 * @JIRA:
 * @Description:
 */
public class FileReadUtil {

    /**
     *
     * 功能描述: 
     *
     * @param:
     * @return: 
     * @auther: zhuyj
     * @date: 2018/7/11/011 18:12
     */
    public static void readTxt(String path) {
        // 存放文件数据
        File file = new File(path);
        String line;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;
        List<String> data = new ArrayList<>();
        String chartSet = "GBK";
        try {
            chartSet = getFilecharset(file);
        } catch (Exception e1) {

        }
        try {
            is = new FileInputStream(file);
            isr = new InputStreamReader(is, chartSet);
            reader = new BufferedReader(isr);
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {

        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(isr);
            IOUtils.closeQuietly(is);
        }
    }

    /**
     *
     * 功能描述: 获取文件字符集
     *
     * @param:
     * @return: 
     * @auther: zhuyj
     * @date: 2018/7/11/011 18:11
     */
    private static  String getFilecharset(File sourceFile) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        FileInputStream fileInputStream = null;
        BufferedInputStream bis = null;
        try {
            boolean checked = false;
            fileInputStream = new FileInputStream(sourceFile);
            bis = new BufferedInputStream(fileInputStream);
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                return charset; //文件编码为 ANSI
            } else if (first3Bytes[0] == (byte) 0xFF
                    && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE"; //文件编码为 Unicode
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE"; //文件编码为 Unicode big endian
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8"; //文件编码为 UTF-8
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
            bis.close();
        } catch (Exception e) {

        }  finally{
            IOUtils.closeQuietly(fileInputStream);
            IOUtils.closeQuietly(bis);
        }
        return charset;
    }
}
