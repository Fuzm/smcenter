package com.irdstudio.ssm.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.irdstudio.ssm.framework.constant.DateFormatConstant;


/**
 * 日期时间工具类
 * @author chenqiming
 *
 */
public class TimeUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat(DateFormatConstant.DATE_FORMAT);

    /**
     * 比较两个日期的大小<br>
     * 日期格式：yyyy-MM-dd<br>
     * 返回结果：date1 > date2 返回1，date1 = date2 返回0，date1 < date2 返回-1。
     * 
     * @param date1
     * @param date2
     * @return
     * @throws Exception
     */
    public static int compareDates(String date1, String date2) throws Exception {
        int result = 0;
        try {
            long d1 = sdf.parse(date1).getTime();
            long d2 = sdf.parse(date2).getTime();
            if (d1 > d2) {
                result = 1;
            } else if (d1 == d2) {
                result = 0;
            } else if (d1 < d2) {
                result = -1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return result;
    }

    /**
     * 比较两个日期的相隔天数<br>
     * 比较方式：date2 减 date1
     * 
     * @param date1
     * @param date2
     * @return
     * @throws Exception
     */
    public static int getDaysBetweenTwoDates(String date1, String date2) throws Exception {
        int result = 0;
        try {
            long d1 = sdf.parse(date1).getTime();
            long d2 = sdf.parse(date2).getTime();

            result = (int) ((d2 - d1) / 1000 / 60 / 60 / 24);
            // 绝对天数
            result = result < 0 ? result * -1 : result;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return result;
    }
    
    /**
     * 获得两个时间之间相差的月份，如天数未对应，则不算满一个月
     * @param date1 开始时间
     * @param date2 结束时间
     * @return
     * @throws Exception
     */
    public static int getMonthsBetweenTwoDates(String date1, String date2) {
        int result = 0;
        try {
        	
        	Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();

            c1.setTime(sdf.parse(date1));
            c2.setTime(sdf.parse(date2));

            result = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12 + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
            c1.add(Calendar.MONTH, result);
            result = c1.after(c2) ? result - 1 : result;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
    

    /**
     * 得到date增加n天后的日期
     * 
     * @param date
     *            格式：yyyy-MM-dd
     * @param n
     *            任意整数
     * @return
     * @throws Exception
     */
    public static String addDay(String date, int n) throws Exception {
        return addDate(Calendar.DATE, date, n);
    }

    /**
     * 得到date增加n月后的日期<br>
     * <b>注：如果日期是月末，且当月天数大于增加n月后的天数，得到的日期是n月后的月末</b>
     * 
     * @param date
     *            格式：yyyy-MM-dd
     * @param n
     *            任意整数
     * @return
     * @throws Exception
     */
    public static String addMonth(String date, int n) throws Exception {
        return addDate(Calendar.MONTH, date, n);
    }

    /**
     * 得到date增加n年后的日期<br>
     * <b>注：闰年的2月29日增加n年后如果不是闰年，会得到2月28日</b>
     * 
     * @param date
     *            格式：yyyy-MM-dd
     * @param n
     *            任意整数
     * @return
     * @throws Exception
     */
    public static String addYear(String date, int n) throws Exception {
        return addDate(Calendar.YEAR, date, n);
    }

    /**
     * 日期增加天数、月数、年数
     * 
     * @param addType
     *            Calendar.DATE，Calendar.MONTH，Calendar.YEAR
     * @param date
     *            格式：yyyy-MM-dd
     * @param addNum
     *            任意整数
     * @return
     * @throws Exception
     */
    private static String addDate(int addType, String date, int addNum) throws Exception {
        String result = "";
        try {
            sdf.parse(date);
            Calendar calendar = sdf.getCalendar();
            calendar.add(addType, addNum);

            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH) + 1;// 月份需要加1！！！！！！
            int d = calendar.get(Calendar.DAY_OF_MONTH);

            result = y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return result;
    }

    /**
     * 检查当前日期是否月末
     * 
     * @param date
     *            格式：yyyy-MM-dd
     * @return
     * @throws Exception
     */
    public static boolean checkDateIsEndOfMonth(String date) throws Exception {
        boolean result = false;
        try {
            sdf.parse(date);
            Calendar calendar = sdf.getCalendar();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;// 月份需要加1！！！！！！

            // 统计截止当前月份（含）总共的天数
            int total = getMonthsDays(year, month);
            String yearStartDate = year + "-01-01";// 获得当年的1月1号
            // 获取相差天数
            int diffDays = getDaysBetweenTwoDates(yearStartDate, date) + 1;// 天数要加1！！！
            if (total == diffDays) {
                result = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return result;
    }

    /**
     * 统计截止当前月份（含）总共的天数
     * 
     * @param year
     * @param month
     * @return
     */
    private static int getMonthsDays(int year, int month) {
        int total = 0;
        switch (month) {
        case 1:
            total = 31;
            break;
        case 2:
            total = 59;
            break;
        case 3:
            total = 90;
            break;
        case 4:
            total = 120;
            break;
        case 5:
            total = 151;
            break;
        case 6:
            total = 181;
            break;
        case 7:
            total = 212;
            break;
        case 8:
            total = 243;
            break;
        case 9:
            total = 273;
            break;
        case 10:
            total = 304;
            break;
        case 11:
            total = 334;
            break;
        case 12:
            total = 365;
            break;
        }
        if (leapYear(year) && month > 1) {
            total += 1;
        }
        return total;
    }

    /**
     * 判断是否闰年<br>
     * 闰年条件（满足一个即可）：1、能被4整除但不能被100整除；2、能被400整除
     * 
     * @param year
     * @return
     */
    public static boolean leapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    /**
     * 获取当前日期，格式：yyyy-MM-dd
     * 
     * @return
     */
    public static String getCurDate() {
        return sdf.format(new Date());
    }

    /**
     * 获取昨日日期
     * 
     * @param curDate
     *            格式：yyyy-MM-dd
     * @return 格式：yyyy-MM-dd
     * @throws Exception
     */
    public static String getYesterday(String curDate) throws Exception {
        return addDay(curDate, -1);
    }

    /**
     * 获取明日日期
     * 
     * @param curDate
     *            格式：yyyy-MM-dd
     * @return 格式：yyyy-MM-dd
     * @throws Exception
     */
    public static String getTomorrow(String curDate) throws Exception {
        return addDay(curDate, 1);
    }

    /**
     * 获取每月天数
     * 
     * @param year
     * @param month
     * @return
     */
    public static int getMonthEndDay(int year, int month) {
        int endDay = 0;
        switch (month) {
        case 1:
            endDay = 31;
            break;
        case 2:
            endDay = 28;
            break;
        case 3:
            endDay = 31;
            break;
        case 4:
            endDay = 30;
            break;
        case 5:
            endDay = 31;
            break;
        case 6:
            endDay = 30;
            break;
        case 7:
            endDay = 31;
            break;
        case 8:
            endDay = 31;
            break;
        case 9:
            endDay = 30;
            break;
        case 10:
            endDay = 31;
            break;
        case 11:
            endDay = 30;
            break;
        case 12:
            endDay = 31;
            break;
        }
        if (leapYear(year) && month == 2) {
            endDay = 29;
        }
        return endDay;
    }

    /**
     * 获取当前月份所在的季末月份
     * 
     * @param month
     * @return
     */
    public static int getEndMonthOfQuarter(int month) {
        int result = 0;
        switch (month) {
        case 1:
        case 2:
        case 3:
            result = 3;
            break;
        case 4:
        case 5:
        case 6:
            result = 6;
            break;
        case 7:
        case 8:
        case 9:
            result = 9;
            break;
        case 10:
        case 11:
        case 12:
            result = 12;
            break;
        }
        return result;
    }

    /**
     * 获取当前日期所在月份的月末日期
     * 
     * @param curDate
     *            格式：yyyy-MM-dd 或 yyyyMMdd
     * @return 格式：yyyy-MM-dd
     */
    public static String getEndDateOfMonth(String curDate) {
        String tmp = curDate.replaceAll("-", "");
        int year = Integer.parseInt(tmp.substring(0, 4));
        int month = Integer.parseInt(tmp.substring(4, 6));

        return getEndDateOfMonth(year, month);
    }

    /**
     * 根据月份获取月末日期
     * 
     * @param year
     * @param month
     * @return 格式：yyyy-MM-dd
     */
    public static String getEndDateOfMonth(int year, int month) {
        // 获取月份最后一天
        int endOfMonth = getMonthEndDay(year, month);
        return year + "-" + (month < 10 ? "0" + month : month) + "-" + endOfMonth;
    }

    /**
     * 获取当前日期所在季度的季末日期
     * 
     * @param curDate
     *            格式：yyyy-MM-dd 或 yyyyMMdd
     * @return 格式：yyyy-MM-dd
     */
    public static String getEndDateOfQuarter(String curDate) {
        String tmp = curDate.replaceAll("-", "");
        int year = Integer.parseInt(tmp.substring(0, 4));
        int month = Integer.parseInt(tmp.substring(4, 6));

        return getEndDateOfQuarter(year, month);
    }

    /**
     * 获取当前月份所在季度的季末日期
     * 
     * @param year
     * @param month
     * @return
     */
    public static String getEndDateOfQuarter(int year, int month) {
        // 获取当前月份所在的季末月份
        int m = getEndMonthOfQuarter(month);
        // 获取月份最后一天
        int endOfMonth = getMonthEndDay(year, m);
        return year + "-" + (m < 10 ? "0" + m : m) + "-" + endOfMonth;
    }


    
    /**
     * 获取当前日期（机器时间）
     * @return 时间格式：yyyy-MM-dd
     */
    public static String getCurrentDate() {
        return DateFormatUtils.format(new Date(), DateFormatConstant.DATE_FORMAT, Locale.CHINA);
    }
    
    /**
     * 获取当前时间（机器时间）
     * @return 时间格式：yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateTime() {
        return DateFormatUtils.format(new Date(), DateFormatConstant.DATETIME_FORMAT, Locale.CHINA);
    }
    
    /**
     * 按传入格式获取时间戳
     * @param pattern
     * @return
     */
    public static String getTimeStampByPattern(String pattern) {
        return DateFormatUtils.format(new Date(), pattern, Locale.CHINA);
    }

    /**
     * 获取营业日期+当前时间
     * @param bizDate 营业日期格式：yyyy-MM-dd
     * @return
     */
    public static String getBizDateTime(String bizDate) {
        return bizDate + " " +DateFormatUtils.format(new Date(), DateFormatConstant.TIME_FORMAT, Locale.CHINA);
    }

    
}
