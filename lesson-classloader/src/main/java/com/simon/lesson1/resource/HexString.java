package com.simon.lesson1.resource;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;

/**
 * Created by sang on 2019/4/16.
 */
public class HexString {

    /**
     * 16进制的字符串表示转成字节数组
     *
     * @param hexString
     *            16进制格式的字符串
     * @return 转换后的字节数组
     **/
    public static byte[] toByteArray(String hexString) {
        if (StringUtils.isEmpty(hexString))
            throw new IllegalArgumentException("this hexString must not be empty");

        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    /**
     * 字节数组转成16进制表示格式的字符串
     *
     * @param byteArray
     *            需要转换的字节数组
     * @return 16进制表示格式的字符串
     **/
    public static String toHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1)
            throw new IllegalArgumentException("this byteArray must not be null or empty");

        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if ((byteArray[i] & 0xff) < 0x10)//0~F前面不零
                hexString.append("0");
            hexString.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return hexString.toString().toLowerCase();
    }

    public static void main(String[] args) {
        String  str = "罗长";
        byte[] sb = str.getBytes();
        System.out.println( sb.length);
        System.out.println(toHexString(sb));
        byte[] b={(byte)0xe7,(byte)0xbd,(byte)0x97,(byte)0xe9,(byte)0x95,(byte)0xbf};
        String test= new String(b, Charset.forName("UTF-8"));
        System.out.println(test);

        char a= 'a';
        char au= 'A';
        char z= 'z';
        char zu= 'Z';
        System.out.println(Character.digit(a,16)& 0xff);
        System.out.println((int)a);
        System.out.println((int)au);
        System.out.println((int)z);
        System.out.println((int)zu);
    }
}
