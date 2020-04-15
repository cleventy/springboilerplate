package com.cleventy.springboilerplate.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.util.StringUtils;

public class DateUtil {
	
	public static final String FORMAT_DDSMMSDDDD = "dd/MM/yyyy";
	public static final String FORMAT_HHDPMM = "HH:mm";
	public static final String FORMAT_HHDPMMDPSS = FORMAT_HHDPMM + ":ss";
	public static final String FORMAT_DDSMMSDDDDSHHDPMMDPSS = FORMAT_DDSMMSDDDD + " " + FORMAT_HHDPMMDPSS;

	// GET
	public static Calendar getNow() {
		return Calendar.getInstance(getDefaultTimeZone());
	}

	
	// DEFAULT
	private static DateFormat getDefaultDateFormat(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(getDefaultTimeZone());
		return dateFormat;
	}
	public static TimeZone getDefaultTimeZone() {
		// default timezone set on SpringBootTomcatApplication
		// should be the same as the one used in serverTimezone parameter in spring.datasource.url property

		// return TimeZone.getTimeZone(ZoneId.of("UTC"));
		return TimeZone.getTimeZone(ZoneId.of("Europe/Madrid"));
	}

	
	// COMPARE
	public static boolean isInMargin(Calendar date1, Calendar date2, Long minutesMargin) {
		long millis1 = date1.getTimeInMillis();
		long millis2 = date2.getTimeInMillis();
		long millisMargin = minutesMargin.longValue()*60*1000;
		return Math.abs(millis1-millis2)<=Math.abs(millisMargin);
	}
	
	
	// CONVERT
	public static Calendar to(String dateString, String formatIn) throws ParseException {
		Date date = getDefaultDateFormat(formatIn).parse(dateString);
		Calendar cal = getNow();
		cal.setTime(date);
		return cal;
	}
	public static String to(Calendar dateCalendar, String formatOut) {
		Date date = dateCalendar.getTime();
		return getDefaultDateFormat(formatOut).format(date);
	}
	
	
	// CHECK
	public static boolean isCorrectHHMM(String hour) {
		return isCorrect(hour, FORMAT_HHDPMM);
	}
	public static boolean isCorrectDDMMYYYY(String day) {
		return isCorrect(day, FORMAT_DDSMMSDDDD);
	}
	private static boolean isCorrect(String dateString, String format) {
		try {
			return !StringUtils.isEmpty(format) && !StringUtils.isEmpty(dateString) && dateString.trim().equals(dateString) && format.length()==dateString.length() && to(to(dateString, format), format).compareTo(dateString)==0;
		} catch (Exception e) {
			return false;
		}
	}
	
}
