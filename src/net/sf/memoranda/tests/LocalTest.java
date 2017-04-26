/**
 * 
 */
package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import net.sf.memoranda.util.Local;

/**
 * Apr 11, 2017
 * @author Jesus Rodriguez Jr
 *
 */
public class LocalTest {
	private Local local= null;
	private String[] months1 = null, months2 = null;
	private String[] week1 = null, week2 = null;
	private Configuration config =null;
	private Date date = null;
	private Calendar calendar = null;
	private CalendarDate calDate = null;
	private long lastYr;

	/**Method for setting up JUnit testing
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		config = new Configuration();
		local = new Local();
		months1 = new String[]{ 	"Januar", "Februar", "März", "April", "Mai", "Juni",
	            				"Juli", "August", "September", "Oktober", "November", "Dezember"};
		week1 = new String[]{	 "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
		week2 = local.getWeekdayNames();
		months2 = local.getMonthNames();
		date = new Date();
		calendar = Calendar.getInstance();
		calDate = new CalendarDate();
		lastYr = calendar.getTimeInMillis()-Long.parseLong("40000000000");		
		
		
	}

	/**Method for tearing down JUnit testing
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	 @Rule
	  public ExpectedException exception = ExpectedException.none();

	@Test
	public void GetMonthNamesArrayTest() {
		
		assertNotEquals("Local is initialized.", local, null);
		assertArrayEquals("months[] == local.getMonthNames()", local.getMonthNames(), months2);		
		assertNotEquals("January != December", local.getMonthName(0), months2[11]);
		assertNotEquals("local.getMonthNames() != months1", local.getMonthNames(), months1);
			
	}
	
	@Test
	public void GetMonthNamesTest() {
		exception.expect(ArrayIndexOutOfBoundsException.class);
		
		assertNotEquals("Local is initialized.", local, null);
		assertEquals("January == January", local.getMonthName(0), months2[0]);
		assertNotEquals("February != null", local.getMonthName(1), null);
		assertFalse("Not equals", months1[2].equals(local.getMonthName(2)));
		
		fail(local.getMonthName(12));
	}
	
	@Test
	public void GetWeekdayNamesArrayTest(){
		
		assertArrayEquals("local.getWeekdayNames() == week2", local.getWeekdayNames(), week2);
		assertNotEquals("local.getWeekdayNames() != null", local.getWeekdayNames(), null);
		
		config.put("FIRST_DAY_OF_WEEK", "mon");

		assertNotEquals("week1 != week2", week1, week2);
		assertNotEquals("local.getWeekdayNames() != week", local.getWeekdayNames(), week1);		
		
		
	}
	
	@Test
	public void GetWeekdayNameTest(){
		exception.expect(ArrayIndexOutOfBoundsException.class);
		
		assertFalse("wed != web",week1[3].equals(local.getWeekdayName(4)));
		assertTrue("web == web", week2[2].equals(local.getWeekdayName(4)));
		assertEquals("Sun == Sun", local.getWeekdayName(1), "Sun");		

		fail(local.getWeekdayName(0));

	}
	
	@Test
	public void GetDateStringTest(){
		exception.expect(IllegalArgumentException.class);
		
		Date date2 = date, date3 = new Date(lastYr);
		
		assertNotEquals("date format != date2 format", 
				local.getDateString(date, 0), local.getDateString(date2, 2));
		
		assertEquals("date format == date2 format", 
				local.getDateString(date, 2), local.getDateString(date2, 2));
		
		assertNotEquals("LastYr Date != Current Date", 
				local.getDateString(date3, 0), local.getDateString(date, 2));

		try{
			fail(local.getDateString((Date)null, 0));
		}catch(NullPointerException e){
			assertNotNull(e);
		}
		
		fail(local.getDateString(date, 4));

	}
	
	@Test
	public void GetDateStringCalendarTest(){
		exception.expect(IllegalArgumentException.class);
		
		Calendar calendar2 = calendar, calendar3 = Calendar.getInstance();
		calendar3.setTimeInMillis(lastYr);
		
		assertNotEquals("calendar format != calendar2 format", 
				local.getDateString(calendar, 0), local.getDateString(calendar2, 2));
		
		assertEquals("calendar format == calendar format", 
				local.getDateString(calendar, 2), local.getDateString(calendar, 2));
		
		assertNotEquals("LastYr Calendar != Current Calendar", 
				local.getDateString(calendar3, 2), local.getDateString(calendar, 2));

		calendar = null;
		
		try{
			fail(local.getDateString(calendar, 0));
		}catch(NullPointerException e){
			assertNotNull(e);
		}
		
		fail(local.getDateString(calendar2, 4));

	}
	
	@Test
	public void GetDateStringCalendarDateTest(){
		exception.expect(IllegalArgumentException.class);
		
		CalendarDate calDate2 = calDate, calDate3 = new CalendarDate(new Date(lastYr));
		
		assertNotEquals("calendar format != calendar2 format", 
				local.getDateString(calDate, 0), local.getDateString(calDate2, 2));
		
		assertEquals("calendar format == calendar format", 
				local.getDateString(calDate, 2), local.getDateString(calDate2, 2));
		
		assertNotEquals("LastYr Calendar != Current Calendar", 
				local.getDateString(calDate3, 2), local.getDateString(calDate, 2));

		calDate = null;
		
		try{
			fail(local.getDateString(calDate, 0));
		}catch(NullPointerException e){
			assertNotNull(e);
		}
		
		fail(local.getDateString(calDate2, 4));

	}
	
	@Test
	public void GetDateStringParamTest(){
		exception.expect(IllegalArgumentException.class);
		
		Calendar calendar2 = calendar, calendar3 = Calendar.getInstance();
		calendar3.setTimeInMillis(lastYr);
		
		assertNotEquals("calendar format != calendar2 format", 
				local.getDateString(calendar.getTime().getMonth(),calendar.getTime().getDay(),calendar.getTime().getYear(), 0),
				local.getDateString(calendar2.getTime().getMonth(),calendar2.getTime().getDay(),calendar2.getTime().getYear(), 2));		
		
		assertEquals("calendar format == calendar format", 
				local.getDateString(calendar.getTime().getMonth(),calendar.getTime().getDay(),calendar.getTime().getYear(), 0),
				local.getDateString(calendar2.getTime().getMonth(),calendar2.getTime().getDay(),calendar2.getTime().getYear(), 0));
		
		assertNotEquals("LastYr Calendar != Current calendar", 
				local.getDateString(calendar3.getTime().getMonth(),calendar3.getTime().getDay(),calendar3.getTime().getYear(), 0),
				local.getDateString(calendar2.getTime().getMonth(),calendar2.getTime().getDay(),calendar2.getTime().getYear(), 0));
		

		calendar = null;
		
		try{
			fail(local.getDateString(calendar, 0));
		}catch(NullPointerException e){
			assertNotNull(e);
		}
		
		fail(local.getDateString(calendar2, 4));

	}
	
	@Test
	public void GetTimeStringDateTest(){
		exception.expect(NullPointerException.class);
		
		Date date2 = date, date3 = new Date(lastYr);
		
		assertEquals("Date time string == Date2 time string", 
				local.getTimeString(date), local.getTimeString(date2));
		
		assertNotEquals("Date time string != Date3 time string", 
				local.getTimeString(date), local.getTimeString(date3));
		
		fail(local.getTimeString((Date)null));
		
	}
	
	@Test
	public void GetTimeStringCalendarTest(){
		exception.expect(NullPointerException.class);

		Calendar calendar2 = calendar, calendar3 = Calendar.getInstance();
		calendar3.setTimeInMillis(lastYr);

		assertEquals("Date time string == Date2 time string", 
				local.getTimeString(calendar), local.getTimeString(calendar2));
		
		assertNotEquals("Date time string != Date3 time string", 
				local.getTimeString(calendar), local.getTimeString(calendar3));
		
		calendar3 = null;
		
		fail(local.getTimeString(calendar3));
		
	}
	
	@Test
	public void GetTimeStringParamTest(){
		exception.expect(NullPointerException.class);

		Date date2 = date, date3 = new Date(lastYr);

		assertEquals("Date HH:MM == Date HH:MM", 
				local.getTimeString(date.getHours(),date.getMinutes()), 
				local.getTimeString(date2.getHours(),date2.getMinutes()));
		
		assertNotEquals("Date HH:MM != lastYr Date HH:MM", 
				local.getTimeString(date.getHours(),date.getMinutes()), 
				local.getTimeString(date3.getHours(),date3.getMinutes()));
		
		date = null;
		
		fail(local.getTimeString(date.getHours(),date.getMinutes()));
		
	}
	
	@Test
	public void ParseTimeStringTest(){
		exception.expect(NullPointerException.class);
		
		assertEquals("Cannot convert string to Time", local.parseTimeString("CannotConvert"), null);
		assertEquals("Cannot convert string to Time", local.parseTimeString("Cannot:Convert"), null);
		assertEquals("Cannot convert string to Time", local.parseTimeString(":"), null);
		assertNotEquals("Convert string to Time", local.parseTimeString("0:0"), null);
		assertNotEquals("Convert string to Time", local.parseTimeString("000:000"+1), null);
		assertNotEquals("Convert string to Time", local.parseTimeString("-12:192"), null);
		
		assertNotNull(local.parseTimeString(null));
	}
	
	@Test
	public void GetStringTest(){
		exception.expect(NullPointerException.class);
		
		assertEquals("getString(time) == Time", local.getString("time"), "Time");
		assertNotEquals("getString(time) == Time", local.getString(local.getString("break;")), null);
		
		fail(local.getString(null));
	}

}
