package com.poseidon.framework.tools;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类
 *
 * @author tiansheng
 * @version 1.0
 * @date 2019/8/23 23:52
 * @since 1.8
 */
public class StringUtils {

    /**
     * 字符串是否为空
     *
     * @param s 目标字符串
     * @return 返回boolean
     */
    public static boolean isEmpty(String s) {
        if (s == null) {
            return true;
        }
        // Unicode 编码下的空格
        if ("\u0000".equals(s)) {
            return true;
        }
        return s.length() == 0 || "".equals(s) || " ".equals("");
    }

    /**
     * 获取最后一个字符
     *
     * @param v 目标字符串
     * @return 返回该字符串的最后一个字符
     */
    public static String getLastString(String v) {
        return v.substring(v.length() - 1);
    }

    /**
     * 删除最后一个字符
     *
     * @param v 目标字符串
     * @return 返回处理后的字符串
     */
    public static String deleteLastString(String v) {
        return v.substring(0, v.length() - 1);
    }

    /**
     * 获取一个字符串的开始位置和结束位置
     *
     * @param v    源字符串
     * @param find 需要查找的字符串
     * @return 数组 0=开始位置 1=结束位置
     */
    public static int[] getStartAndEndIndex(String v, String find) {
        int start = -1; // 开始下标
        int end = -1; // 结束下标
        int currentIndex = 0; // 当前下标 -1等于当前源字符串长度已遍历完
        char[] source = v.toCharArray(); // 源字符串char数组
        char[] target = find.toCharArray();
        while (source.length >= currentIndex) {
            boolean result = true;
            for (int i = 0; i < target.length; i++) {
                if (source[currentIndex] == target[i]) {
                    result = true;
                } else {
                    result = false;
                    currentIndex++;
                    break;
                }
                currentIndex++;
            }
            if (result) {
                start = currentIndex - target.length;
                end = currentIndex;
                break;
            }
        }
        return new int[]{start, end};
    }

    /**
     * 格式化
     *
     * @param input
     * @param args
     * @return
     */
    public static String format(String input, Object... args) {
        int offset = 0;
        int subscript = 0;
        char[] chars = input.toCharArray();
        StringBuilder builder = new StringBuilder();
        char previous = '#';
        for (int i = 0; i < chars.length; i++) {
            char current = chars[i];
            if (previous == '{' && current == '}') {
                char[] temp = new char[(i - offset) - 1];
                System.arraycopy(chars, offset, temp, 0, (offset = i - 1));
                builder.append(temp).append(args[subscript]);
                temp = new char[chars.length - offset - 2];
                System.arraycopy(chars, offset + 2, temp, 0, temp.length);
                // reset
                chars = temp;
                subscript ++;
                i        = 0;
                offset   = 0;
            } else {
                previous = current;
            }
        }
        return builder.append(chars).toString();
    }

    /**
     * 合并行去除多余空格
     * @param text
     * @return
     */
    public static String trim(String text){
        StringBuilder content = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(text);
        while(tokenizer.hasMoreTokens()){
            String str = tokenizer.nextToken();
            content.append(str);
            if(tokenizer.hasMoreTokens()){
                content.append(" ");
            }
        }
        return content.toString();
    }

}

