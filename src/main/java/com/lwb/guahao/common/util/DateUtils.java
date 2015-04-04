package com.lwb.guahao.common.util;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Lu Weibiao
 * Date: 2015/3/7 17:06
 */
public class DateUtils {
    /**常用日期时间格式**/
    public final static String yearMonthDayTimePattern = "yyyy年MM月dd日 HH:mm:ss";
    public final static String yearMonthDayPattern = "yyyy年MM月dd日";
    public final static String yearMonthDayWeekPattern = "yyyy年MM月dd日(E)";
    public final static String yearMonthDayWeekTimePattern = "yyyy年MM月dd日(E) HH:mm";
    public final static String timePattern = "HH:mm:ss";
    public final static String weekPattern = "E";
    public final static String ISOYearMonthDayPattern = "yyyy-MM-dd";
    public final static SimpleDateFormat yearMonthDayTimeFormatter = new SimpleDateFormat(yearMonthDayTimePattern);
    public final static SimpleDateFormat timeFormatter = new SimpleDateFormat(timePattern);
    public final static SimpleDateFormat yearMonthDayWeekTimeFormatter = new SimpleDateFormat(yearMonthDayWeekTimePattern);
    public final static SimpleDateFormat weekFormatter = new SimpleDateFormat(weekPattern);
    public final static SimpleDateFormat yearMonthDayFormatter = new SimpleDateFormat(yearMonthDayPattern);
    public final static SimpleDateFormat ISODateFormatter = new SimpleDateFormat(ISOYearMonthDayPattern);
    public final static SimpleDateFormat yearMonthDayWeekFormatter = new SimpleDateFormat(yearMonthDayWeekPattern);

    /**
     * 根据出生日期字符串算出年龄
     * @param birthDateString
     * @param birthDateStringFormat
     * @return
     */
    public static int getAge(String birthDateString, String birthDateStringFormat) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(birthDateStringFormat);
        try {
            Date birthDate = dateFormatter.parse(birthDateString);
            return getAge(birthDate);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据出生日期算出年龄
     * @param birthDate
     * @return
     */
    public static int getAge(Date birthDate) {
        DateTime curDate = new DateTime();
        return new Period(new DateTime(birthDate), curDate, PeriodType.years()).getYears();
    }
}
