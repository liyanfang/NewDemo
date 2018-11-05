package cn.suanzi.newdemo.Util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.lang.System.currentTimeMillis;

/**
 * 时间
 * Created by liyanfang on 2016/6/7.
 */
public class DateUtil {
    /** 时分秒*/
    public static final String FORMAT_H_M_S = "yyyy-MM-dd HH:mm:ss";
    /** 通常时间*/
    public static final String FORMAT = "yyyy-MM-dd";
    /** 两分钟*/
    public static final long TWO_MINUTES = 120;
    /** 时分*/
    public static final String FORMAT_H_M = "yyyy-MM-dd HH:mm";
    public static SimpleDateFormat mSimpleDateFormat;

    /**
     * 把时间转换为时间戳
     * @param time 需要转换的时间
     * @return 返回一个时间戳
     */
    public static long getTimestamp(String time) {
        if (TextUtils.isEmpty(time)) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_H_M_S, Locale.CANADA);
        Date d;
        try {
            d = sdf.parse(time);
            return d.getTime() / 1000;
        }catch (ParseException e) {
        }
        return 0;
    }

    /**
     * 转化Unix Timestamp为时间"HH:mm"
     * @param unixSeconds Unix Timestamp(秒)
     * @param locale 转化为哪里的时间
     * @return
     */
    public static String unixTS2Time(long unixSeconds, Locale locale) {
        return unixTS2Time(unixSeconds,locale,null);
    }

    /**
     * 转化Unix Timestamp为时间"HH:mm"
     * 提示： 获取时间及本地化显示的例子： TimeZone tz = TimeZone.getTimeZone("GMT+08"); Calendar
     * cal = Calendar.getInstance(tz); SimpleDateFormat sdf = today_new
     * SimpleDateFormat(); sdf.setTimeZone(cal.getTimeZone());
     * System.out.println(sdf.format(cal.getTime())); 输出： 10-3-22 下午7:58
     * SimpleDateFormat类可以以字符串参数形式设置输出时间的格式，例如：
     * sdf.applyPattern("yyyy年MM月dd日HH时mm分ss秒"); 输出： 2010年03月22日20时02分35秒
     * @param unixSeconds Unix Timestamp(秒)
     * @return 时间"HH:mm"
     */
    public static String unixTS2Time(long unixSeconds, Locale locale, String type) {
        if (TextUtils.isEmpty(type)) {
            type = FORMAT_H_M_S;
        }
        Date date = new Date(unixSeconds * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat(type, locale);
        return sdf.format(date);
    }

    /**
     * 判断手机时间跟服务器的时间差
     * @param serviceUnixTS
     * @return
     */
    public static boolean deffDate (String serviceUnixTS) {
        if (TextUtils.isEmpty(serviceUnixTS)) {
            return true;
        }
        boolean flag = true;
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT, Locale.getDefault());
        try {
            long unixSeconds = Long.parseLong(serviceUnixTS) * 1000;
            String nowTime = sdf.format(new Date());
            String serViceTime = sdf.format(new Date(unixSeconds));
            Date serviceDate = sdf.parse(serViceTime);
            Date nowDate = sdf.parse(nowTime);
            long day = (nowDate.getTime() - serviceDate.getTime());// 判断是否是同一天
            if (day == 0) {
                flag = false;
            } else {
                long second = currentTimeMillis() - unixSeconds;
                long secondSub = Math.abs(second)/1000;
                if (secondSub < TWO_MINUTES) {
                    flag = false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    /**
     * 获取当前的时间
     * @return 返回yyyy-MM-dd类型的时间
     */
    public static String getNowTimeYMD () {
        return getNowTimeYMD(null);
    }

    /**
     * 获取当前的时间
     * @return 返回相应格式的时间
     */
    public static String getNowTimeYMD(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TextUtils.isEmpty(format) ? FORMAT : format , Locale.getDefault());
        return dateFormat.format(new Date());
    }

    /**
     * 两个时间相减
     * @param startTime 被减数
     * @param endTime 减数
     * @param timeFormat 时间格式 eg：yyyy-MM-dd HH:mm:ss，若不填则默认yyyy-MM-dd HH:mm:ss
     * @return 返回两者相差的毫秒数
     */
    public static long subDate(String startTime, String endTime, String timeFormat) {
        if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)) {
            return 0;
        }
        SimpleDateFormat dfs = new SimpleDateFormat((timeFormat == null) ? FORMAT_H_M_S : timeFormat, Locale.getDefault());
        long between = 0;
        try {
            java.util.Date begin = dfs.parse(startTime);
            java.util.Date end = dfs.parse(endTime);
            between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return between;
    }

    /**
     * 两个时间相减
     * @param startTime 开始时间 m
     * @param endTime 结束时间
     * @param timeFormat 时间格式 eg：yyyy-MM-dd HH:mm:ss，若不填则默认yyyy-MM-dd HH:mm:ss
     * @return 返回两者相差的毫秒数
     */
    public static long subDate(long startTime, String endTime, String timeFormat) {
        if (startTime == 0 || TextUtils.isEmpty(endTime)) {
            return 0;
        }
        SimpleDateFormat dfs = new SimpleDateFormat((timeFormat == null) ? FORMAT_H_M_S : timeFormat, Locale.getDefault());
        long between = 0;
        try {
            java.util.Date end = dfs.parse(endTime);
            between = (end.getTime() - startTime * 1000);// 得到两者的毫秒数
        } catch (Exception ex) {
        }
        return between;
    }

    /**
     * 两时间相减相差的天数
     * @param startTime 被减数
     * @param endTime 减数
     * @param timeFormat 时间格式
     * @return 返回相差天数
     */
    public static long subTimeDay(String startTime, String endTime, String timeFormat) {
        long between = subDate(startTime, endTime, timeFormat); // 得到毫秒数
        return between / (24 * 3600000); // 天 3600000 为1天
    }

    /**
     * 时间差
     * @param startTime 开始时间(毫秒数)
     * @param endTime 结束时间(毫秒数)
     * @param time 时间差 (分钟记时间)
     * @return 结束时间减开始时间 大于 time 时间差返回true，否则返回false
     */
    public static boolean subTime (long startTime, long endTime, int time) {
        return (endTime - startTime)/60000 >= time; // 60000 是一分钟
    }

    /**
     * 下一个显示小红点时间（每个小时的第10s）
     * @return 单位：ms
     */
//    public static long nextReddotMills(){
////       return  3600000 + Const.TTG.MILLISECONDS_10  - (System.currentTimeMillis() % 3600000);
//    }

    /**
     * 前一个整点前一秒 用于请求数字
     * @return 秒
     */
    public static long preMilOneHour () {
       return  ((System.currentTimeMillis() / 3600000) * 3600000 -1000)/1000;
    }

    /**
     * 两个时间相差天数
     * @param subTime 时间差
     * @return
     */
    public static long day(long subTime) {
        return (subTime / (3600 * 24)); // 天 3600 为1小时， 24 为1天
    }
    /**
     * 两个时间相差小时
     * @param subTime 时间差
     * @return
     */
    public static long hour(long subTime) {
        return (subTime % (3600 * 24)) / 3600 ; // 小时
    }
    /**
     * 两个时间相差分钟
     * @param subTime 时间差
     * @return
     */
    public static long min(long subTime) {
        return (subTime % 3600) / 60; // 分钟
    }

    public static long s(long subTime) {
        return subTime % 60; // 秒
    }

    /**
     * 两个时间相毫秒
     * @param subTime 时间差 (ms)
     * @return
     */
    public static long ms(long subTime) {
        return subTime / 100 % 10; // 计算毫秒
    }

}
