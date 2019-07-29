package com.ctgu.sell.utils;

import com.ctgu.sell.enums.CodeEnum;

/**
 * @author Jiayuan
 * @version 1.0
 * @description:
 * @time 2018/7/28 15:40
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
