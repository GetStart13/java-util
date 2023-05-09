package com.fqq.utils.string;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> 2023/5/9 </p>
 * 字符串工具类
 *
 * @author Fqq
 */
public class StringUtil {
    private StringUtil() {
    }

    /**
     * 解析一个字符串，获取所有连续的数字，用集合接收
     */
    public static List<Integer> parseInt(String string) {
        // 解析数字
        ArrayList<Integer> list = new ArrayList<>();
        int start = 0;
        int end;
        int len = string.length();
        for (int i = 0; i < len; i++) {
            char cTop = 0;
            char cRear = 0;
            char c = string.charAt(i);
            if (i > 0) {
                cTop = string.charAt(i - 1);
            }
            if (i < len - 1) {
                cRear = string.charAt(i + 1);
            }
            // 如果 c 是数字 且 它前一个数不是数字 或者 它是第 0 个字符时，获得 start
            if (isNumber(c) && (!isNumber(cTop) || i == 0)) {
                start = i;
            }
            // 如果 c 是数字 且 它后一个数不是数字 或者 它是最后一个字符时，获得 end
            if (isNumber(c) && (!isNumber(cRear) || i == len - 1)) {
                end = i + 1;
                list.add(Integer.parseInt(string.substring(start, end)));
            }
        }
        return list;
    }

    /**
     * 判断是否是数字
     */
    public static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }
}
