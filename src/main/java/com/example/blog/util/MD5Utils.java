package com.example.blog.util;

import org.springframework.util.DigestUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Emma
 * @create 2020 - 05 - 23 - 16:45
 */
public class MD5Utils {


    //1,使用spring自带MD5工具
    public static String code1(String str){
        //使用一般加盐？
        String slat = "code";
        String s = DigestUtils.md5DigestAsHex((str+slat).getBytes());
        return s;
        /*String s = DigestUtils.md5DigestAsHex(str.getBytes());
        return s;*/
    }



    /**
     * MD5加密类
     * @param str 要加密的字符串
     * @return    加密后的字符串
     */
    //自己写的MD5加密
    public static String code2(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[]byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }


    //main方法测试一下
    public static void main(String[] args) {
        System.out.println(code1("18309293959"));
    }
}
