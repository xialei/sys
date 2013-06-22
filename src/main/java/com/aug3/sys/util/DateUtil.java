package com.aug3.sys.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

/**
 * Date utility method
 * 
 * @author Roger.xia
 * 
 */
public class DateUtil {

    // # of milliseconds in a day
    public static final long kMilliSecPerDay = 1000 * 60 * 60 * 24;

    public static final String ISO_TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String ISO_DATE_PATTERN = "yyyy-MM-dd";
	
	public static final String ISO_UTC_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	
	public static final String MONGO_DATE_PATTERN = "EEE MMM dd hh:mm:ss 'UTC' yyyy";
	
    private static final Calendar cal = GregorianCalendar.getInstance();

    // SimpleDateFormat is not thread safe
    // public static final SimpleDateFormat simpleDateFormat = new
    // SimpleDateFormat(ISO_DATE_PATTERN);

    private static DateFormat formatter = new SimpleDateFormat("''ddMMMyy''");
    private static DateFormat formatter2 = new SimpleDateFormat("\"ddMMMyy\"");
    private static DateFormat formatter3 = new SimpleDateFormat("MM/dd/yy");
    private static DateFormat formatter5 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

    private static ThreadLocal<SimpleDateFormat> iso_dateformat_threadlocal = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat(ISO_DATE_PATTERN);
        }
    };

    private static ThreadLocal<SimpleDateFormat> utc_dateformat_threadlocal = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            SimpleDateFormat sdf = new SimpleDateFormat(ISO_DATE_PATTERN);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdf;
        }
    };
	
	private static ThreadLocal<SimpleDateFormat> iso_utc_datetimeformat_threadlocal = new ThreadLocal<SimpleDateFormat>() {
		protected synchronized SimpleDateFormat initialValue() {
			SimpleDateFormat sdf = new SimpleDateFormat(ISO_UTC_DATETIME_PATTERN);
	        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			return sdf;
		}
	};

    private static ThreadLocal<SimpleDateFormat> utc_timestampformat_threadlocal = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            SimpleDateFormat sdf = new SimpleDateFormat(ISO_TIMESTAMP_PATTERN);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdf;
        }
    };

    private static ThreadLocal<SimpleDateFormat> iso_timeformat_threadlocal = new ThreadLocal<SimpleDateFormat>() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat(ISO_TIMESTAMP_PATTERN);
        }
    };

    public static SimpleDateFormat getISODateFormat() {
        return iso_dateformat_threadlocal.get();
    }
	
	public static SimpleDateFormat getISO_UTC_datetimeFormat() {
		return iso_utc_datetimeformat_threadlocal.get();
	}

    public static SimpleDateFormat getUTCDateFormat() {
        return utc_dateformat_threadlocal.get();
    }

    public static SimpleDateFormat getTimestampFormat() {
        return utc_timestampformat_threadlocal.get();
    }

    public static SimpleDateFormat getISOTimeFormat() {
        return iso_timeformat_threadlocal.get();
    }

    /**
     * Calculate # of days between supplied arguments
     * 
     * @param dteStart
     *            1st date
     * @param dteEnd
     *            2nd date
     * @return long # of days between the 2 supplied arguments
     */
    public static long getDurationInDays(Date dteStart, Date dteEnd) {
        if (dteStart == null || dteEnd == null)
            return 0;

        return (long) Math.abs(Math.round((dteEnd.getTime() - dteStart.getTime()) / kMilliSecPerDay));
    }

    /**
     * Returns a date from adding # of days to a supplied date
     * 
     * @param dteStart
     *            starting date
     * @param lDuration
     *            # of days to add
     * @return Date Result of adding # of days to a date
     */
    public static Date determineEndDate(Date dteStart, long lDuration) {
        long lMilliSec = dteStart.getTime() + (kMilliSecPerDay * lDuration);
        return new Date(lMilliSec);
    }

    /**
     * Parse a date from supported date formats. These include: ''ddMMMyy'',
     * "ddMMMyy", MM/dd/yy, EEE MMM dd HH:mm:ss z yyyy
     * 
     * @param s
     * @return Date
     * @throws ParseException
     */
    public static Date parseDate(String s) throws ParseException {
        String value = s;
        if (!value.endsWith("d")) {

            if (value.charAt(2) == '/')
                return formatter3.parse(value);
            else
                return formatter5.parse(value);
        } else {
            value = value.substring(0, value.length() - 1);
            // attempt to parse as an Integer
            if (value != null) {
                if (value.startsWith("\"") && value.endsWith("\""))
                    return formatter2.parse(value);
                else if (value.startsWith("'") && value.endsWith("'"))
                    return formatter.parse(value);
                else
                    throw new ParseException("incorrect format for Date(" + s + "): correct format is 'ddMMMyy'd", 0);
            }
        }
        return null;
    }

    public static final Date parseDate(String strPattern, String strDate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(strPattern);
        return df.parse(strDate);
    }

    public static String getCurrentTime() {
        return getISOTimeFormat().format(new Date());
    }

    public static String getCurrentTime(String timeFormatPattern) {
        if (StringUtils.isBlank(timeFormatPattern)) {
            return getCurrentTime();
        }
        DateFormat formatter = new SimpleDateFormat(timeFormatPattern);
        return formatter.format(new Date());
    }

    public static String formatCurrentDate() {
        return getISODateFormat().format(new Date());
    }

    /**
     * @return yyyy-MM-dd
     */
    public static String getCurrentDate() {
        return getISODateFormat().format(new Date(System.currentTimeMillis()));
    }

    public static String formatYearsBeforeToday(int years) {
        cal.setTime(new Date(System.currentTimeMillis()));
        cal.add(Calendar.YEAR, -years);
        return getISODateFormat().format(cal.getTime());
    }

    public static Date getYearsBeforeToday(int years) {
        cal.setTime(new Date(System.currentTimeMillis()));
        cal.add(Calendar.YEAR, -years);
        return cal.getTime();
    }

    public static Date lastDayOfMonth(Date date) {
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * Get pre or next n days
     * 
     * @param n
     *            positive for next, negative for pre
     * @param format
     *            0: 20100901 1: "2010-09-01" 2: Date
     * @return
     */
    public static Object getPreOrNextDays(int n, int format) {
        Calendar a = new GregorianCalendar();
        a.add(Calendar.DATE, n);
        int nYear = a.get(Calendar.YEAR);
        int nMonth = a.get(Calendar.MONTH) + 1;
        int nDay = a.get(Calendar.DATE);
        if (format == 0) {
            return nYear * 10000 + nMonth * 100 + nDay;
        } else if (format == 1) {
            return nYear + "-" + nMonth + "-" + nDay;
        } else {
            return a.getTime();
        }
    }

    public static Object getPreOrNextMonths(int n, int format) {
        Calendar a = new GregorianCalendar();
        a.add(Calendar.MONTH, n);
        int nYear = a.get(Calendar.YEAR);
        int nMonth = a.get(Calendar.MONTH) + 1;
        int nDay = a.get(Calendar.DATE);
        if (format == 0) {
            return nYear * 10000 + nMonth * 100 + nDay;
        } else if (format == 1) {
            return nYear + "-" + nMonth + "-" + nDay;
        } else {
            return a.getTime();
        }
    }

    /**
     * 
     * @Title: convertLocal2UTC
     * @Description:
     * @param strDate
     * @return (UTC)Date, if ParseException occurred,return null
     */
    public static Date convertLocal2UTC(String strDate) {
        try {
            return getUTCDateFormat().parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date convertTimestampLocal2UTC(String strDate) {
        try {
            return getTimestampFormat().parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatDate(Date date) {
        return getISODateFormat().format(date);
    }

    public static String formatDate(Date date, String formatter) {
        DateFormat df = null;
        if (StringUtils.isBlank(formatter)) {
            return formatDate(date);
        }
        df = new SimpleDateFormat(formatter);
        return df.format(date);
    }

    public static String formatDate(String inputDate, String inputFormat, String diserOutput) throws ParseException {
        SimpleDateFormat inputformat = new SimpleDateFormat(inputFormat, Locale.US);
        SimpleDateFormat outputformat = new SimpleDateFormat(diserOutput, Locale.US);
        String outputDate = inputDate;
        try {
            Date tmpDate = inputformat.parse(inputDate);
            outputDate = outputformat.format(tmpDate);
        } catch (ParseException e) {
            throw e;
        }
        return outputDate;
    }

    public static String getYearOfDate(Date date) {
        cal.setTime(date);
        return String.valueOf(cal.get(Calendar.YEAR));
    }

    public static String getMonthOfDate(Date date) {
        cal.setTime(date);
        return String.valueOf(cal.get(Calendar.MONTH) + 1);
    }

    public static void main(String[] srgs) throws ParseException {
        String style = "MMM dd',' yyyy hh:mm:ss aaa";

        String input = "Jun 29, 2012 04:00:00 PM";// "1997-01-00";

        System.out.println(formatDate(input, style, "yyyy-MM-dd"));

        System.out.println(getYearOfDate(new Date()));
    }
}
