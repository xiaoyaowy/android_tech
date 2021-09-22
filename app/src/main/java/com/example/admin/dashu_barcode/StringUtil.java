package com.example.admin.dashu_barcode;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static boolean isEmpty(String str) {
        if (str == null || "null".equals(str) || str.length() == 0) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean isPhoneNumber(String phone) {
        if (isEmpty(phone)) {
            return false;
        }
        String regExp = "^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.find();
    }


    public static boolean isIdCard(String idCard) {
        if (isEmpty(idCard)) {
            return false;
        }
        String regExp = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}[x|X|\\d]$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(idCard);
        return m.find();
    }


    public static String inputStream2String(InputStream is) {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }


    public static String idCardDeal(String idCard) {
        StringBuffer sb = new StringBuffer(idCard);
        sb.replace(6, 16, "**********");
        return sb.toString();
    }
}
