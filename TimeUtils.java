package cn.we.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by BDwuzhou on 2016/12/25.
 */
public class TimeUtils {

    //分隔符
    public static final String SEPARATOR_DASH = "-";
    public static final String SEPARATOR_COLON = ":";
    public static final String SEPARATOR_SLASH = "/";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String duration2Text(long duration, String separatorType) {
        String durationText;//时间长度的文本
        if (duration / 1000 < 600) {//长度小于10分钟
            durationText = "0" + duration / 1000 / 60 + separatorType +
                    duration / 1000 % 60;
        } else if (duration / 1000 < 3600) {//长度小于1小时
            durationText = duration / 1000 / 60 + separatorType +
                    duration / 1000 % 60;
        } else if (duration / 1000 / 3600 < 10) {//长度小于10小时
            durationText = "0" + duration / 1000 / 3600 +
                    separatorType + duration / 1000 % 3600 / 60 +
                    separatorType + duration / 1000 % 3600 % 60;
        } else {//长度大于等于10小时
            durationText = duration / 1000 / 3600 + separatorType +
                    duration / 1000 % 3600 / 60 + separatorType +
                    duration / 1000 % 3600 % 60;
        }
        return durationText;
    }

    public static String getCurrentFormatTime(boolean withSecond) {
        SimpleDateFormat format = withSecond ?
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") :
                new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2));
    }

    public static String getTimeGaps(long duration) {
        double time = new Date().getTime() - duration * 1.0;
        if (time < 60_000) {
            return "1分钟前";
        } else if (time >= 60_000 && time < 60_000 * 60) {
            return new SimpleDateFormat("m").format(time) + "分钟前";
        } else if (time >= 60 * 60_000 && time < 60 * 60_000 * 24) {
            return new SimpleDateFormat("H").format(time) + "小时前";
        } else if (time >= 60 * 60_000 * 24 && time < 60 * 60_000 * 24 * 2) {
            return "1天前";
        } else if (time >= 60 * 60_000 * 24 * 31 && time < 60 * 60_000 * 24 * 31 * 12) {
            return new SimpleDateFormat("MM月dd日").format(duration);
        } else {
            return new SimpleDateFormat("yyyy-MM-dd").format(duration);
        }
    }

    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType) {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType) {
        Date date = null; // String类型转成date类型
        date = stringToDate(strTime, formatType);
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

}
