import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/8/3.
 */
public class Md5Util {
    private static final String TAG = "lyx";

    /**
     * @param args
     */
    public static void main(String[] args) {
        /*普通模式*/
//        String psd = "123";
        /*加盐模式*/
        String psd = "123" + "benben";
        encoder(psd);
    }

    /**
     * 给指定字符串安装MD5算法去加密
     *
     * @param psd 需要加密的密码
     */
    private static void encoder(String psd) {
        /*1.指定加密算法类型*/
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            /*2.将需要加密的字符串转换成byte类型的数组，然后进行随机哈希过程*/
            byte[] bs = digest.digest(psd.getBytes());
            /*3.通过循环遍历bs，然后让其生成32位字符串，固定写法*/
            /*4.拼接字符串的过程*/
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bs) {
                int i = b & 0xff;
                /*int类型的i需要转换成16进制字符*/
                String hexSthing = Integer.toHexString(i);
                if (hexSthing.length() < 2) {
                    hexSthing = "0" + hexSthing;
                }
                stringBuffer.append(hexSthing);
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
