package it.palex.provaLogin.library.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author Alessandro Pagliaro
 *
 */
public class DateUtility {
	
	private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DateUtility.class);


	/**
	 * Note the date is converted to UTC but the TimeZone remains the default.
	 * So if you call more time this method you will get a conversion error.<br>
	 * Example you have date <b>2020-02-17T14:24:34.338 CET</b> if you call this method you will get
	 * a new date <b>2020-02-17T13:24:34.338+0000 CET</b> and if you call again you will get
	 * <b>2020-02-17T12:24:34.338+0000 CET</b> cause the time zone is CET on your computer.
	 * So only first call will give you the date <b>value</b> in UTC with the time zone of JVM.
	 *
	 * @param date
	 * @return
	 */
	public static Date dateToUTC(Date date) {
		if(date==null) {
			throw new NullPointerException();
		}
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d;
		try {
			d = dateFormatLocal.parse( dateFormatGmt.format(date) );
		} catch (ParseException e) {
			LOGGER.error("ParseException parsing date. broken jre");
			throw new RuntimeException("ParseException parsing date. broken jre");
		}
		return d;
	}

	/**
	 *
	 * @param d1
	 * @param d2
	 * @return difference in seconds between d1 and d2
	 * @throws NullPointerException if d1 and d2 is null
	 */
	public static long diffInSeconds(Date d1, Date d2){
		if(d1==null || d2==null){
			throw new NullPointerException();
		}
		long seconds = (d1.getTime()-d2.getTime())/1000;

		return seconds;
	}

	/**
	 * Note the current date is converted to UTC but the TimeZone remains the default of jvm. 
	 * TimeZone.setDefault(TimeZone.getTimeZone(timezone));
	 * 
	 * @return Date in UTC 
	 */
	public static Date getCurrentDateInUTC(){
		Date date = new Date();
		
		return dateToUTC(date);
	}
	
	public static Calendar getCurrentCalendarInUTC(){
		Date date = getCurrentDateInUTC();
		return dateToCalendar(date);
	}
		
	public static Calendar dateToCalendar(Date date){
		if(date==null){
			throw new NullPointerException();
		}
		Calendar park = Calendar.getInstance();
		park.setTime(date);
		return park;
	}

	/**
	 * Add negative value to subtract
	 * @param date
	 * @param seconds
	 * @return
	 */
	public static Date addSecondsToDate(Date date, int seconds){
		if(date==null){
			throw new NullPointerException();
		}
		return calendarToDate(addTimeUnitToCalendar(dateToCalendar(date), Calendar.SECOND, seconds));
	}

	public static Date calendarToDate(Calendar cal){
		if(cal==null){
			throw new NullPointerException();
		}
		return cal.getTime();
	}

	private static Calendar addTimeUnitToCalendar(Calendar cal, int timeUnit, int value){
		if(cal==null){
			throw new NullPointerException();
		}
		Calendar park = (Calendar) cal.clone();
		park.add(timeUnit, value);

		return park;
	}
}

