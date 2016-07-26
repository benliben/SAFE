package com.android.benben.safe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LiYuanxiong on 2016/7/25 16:00.
 * Desribe: 将流转换成字符串
 */
public class StreamUtil {
    /**
     * @param is  流的对象
     * @return  流转换成的字符串  返回null代表异常
     */
    public static String streamToString(InputStream is) throws IOException {
        /*1.在读取的工程中，将读取的内容存储到缓存中 然后一次性的转换成字符串返回*/
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        /*2.读流操作 读到没有为止（循环）*/
        byte[] buffer = new byte[1024];
        /*3.记录读取内容的临时变量*/
        int temp = -1;
        try {
            while ((temp = is.read(buffer)) != -1) {
                bos.write(buffer, 0, temp);
            }
            /*4.返回读取的数据*/
            return bos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            is.close();
            bos.close();
        }
        return null;
    }

}
