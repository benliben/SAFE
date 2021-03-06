package com.android.benben.safe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/8/4.
 * Md5的加密模式
 */
public class Md5Util {

    /**
     *
     *
     * @param psd
     */
    /**
     * 给指定字符串安装MD%算法加密
     * @param psd 需要加密的密码
     * @return MD5字符串 返还""的时候出现错误
     */
    public static String encoder(String psd) {
        /*加盐处理*/
        psd = psd + "benben";
        StringBuffer stringBuffer = new StringBuffer();
        /*1.指定加密算法类型*/

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            /*2.将需要加密的字符串转换成byte类型的数组，然后进行随机哈希的过程*/
            byte[] bs = digest.digest(psd.getBytes());
            /*3.通过循环遍历bs，然后让其生成32位字符串，固定写法*/
            for (byte b : bs
                    ) {
                int i = b & 0xff;
                /*int类型的i需要转换成16进制字符*/
                String hexString = Integer.toHexString(i);
                if (hexString.length() < 2) {
                    hexString = "0" + hexString;
                }
                /*4.拼接字符串的过程*/
                stringBuffer.append(hexString);
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

}
