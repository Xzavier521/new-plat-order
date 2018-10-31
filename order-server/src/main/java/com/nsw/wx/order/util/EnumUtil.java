package com.nsw.wx.order.util;


import com.nsw.wx.order.enums.CodeEnum;

/**
 * 张维维
 * 2018-10-23
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    } public static void main(String[] args) {
        System.out.println("sss");
    }
}
