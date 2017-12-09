package cn.we.base.utils;


import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Pattern;

public class PinYinUtil {
    public static HanyuPinyinOutputFormat format;

    /**
     * 获取拼音的首字母（大写）
     *
     * @param pinyin
     * @return
     */
    public static String getFirstLetter(final String pinyin) {
        if (TextUtils.isEmpty(pinyin)) {
            return "~";
        } else {
            String c = pinyin.substring(0, 1);
            Pattern pattern = Pattern.compile("^[A-Za-z]+$");
            if (pattern.matcher(c).matches()) {
                return c.toUpperCase();
            }
            return "~";
        }
    }

    public static String HanZiToPinYin(String hanzi) {
        char[] chars = hanzi.toCharArray();
        if (format == null) {
            format = new HanyuPinyinOutputFormat();
        }
        //设置大写
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        //取消声调
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        StringBuilder sb = new StringBuilder();
        for (char aChar : chars) {
            if (Character.isWhitespace(aChar)) {
                continue;
            }
            if (Character.toString(aChar).matches("[\\u4E00-\\u9FA5]")) {
                try {
                    String letter = PinyinHelper.toHanyuPinyinStringArray(aChar, format)[0];
                    sb.append(letter);
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            } else {

                if (Character.isLetter(aChar)) {//字母
                    sb.append(Character.toUpperCase(aChar));
                } else {//其他特殊字符
                    sb.append("#");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 多个汉字截取首拼音字母
     *
     * @param hanzi 多个汉字例如：“你好子”
     * @return “nhz”
     */
    public static String moreHanZiToPinYin(String hanzi) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(hanzi)) {
            char ch[] = hanzi.toCharArray();
            for (int i = 0; i < ch.length; i++) {
                String py = PinYinUtil.HanZiToPinYin(ch[i] + "");
                String zm = PinYinUtil.getFirstLetter(py);
                sb.append(zm);
            }
        }
        return sb.toString();
    }

}

