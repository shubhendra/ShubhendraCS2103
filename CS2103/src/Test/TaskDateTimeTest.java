package Test;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import data.TaskDateTime;
public class TaskDateTimeTest {
	private static Logger logger=Logger.getLogger(TaskDateTimeTest.class);
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testTaskDateTime()
	{
		TaskDateTime newDateTime=new TaskDateTime();
		logger.debug(newDateTime.formattedToString());
	}
	@Test 
	public final void testTaskDateTimeLong()
	{
		TaskDateTime testDate=new TaskDateTime(TaskDateTime.getCurrentDateTime().getTimeMilli());
		logger.debug(testDate.formattedToString());
	}
	@Test
	public final void testTaskDateTimeIntIntInt()
	{
		TaskDateTime testDate=new TaskDateTime(2012,2,4);
		logger.debug(testDate.formattedToString());
	}
	@Test
	public final void testGetCurrentDate() {
		assertEquals("15 Jun 2012",TaskDateTime.getCurrentDate().formattedToString());
	}

	@Test
	public final void testIsEqual() {
		TaskDateTime first=new TaskDateTime(2015,4,3,16,0,0);
		TaskDateTime second=new TaskDateTime(2015,4,3,16,0,0);
		assertEquals(true,first.isEqual(second));
	}

	@Test
	public final void testCompareTo() {
		TaskDateTime first=new TaskDateTime(2015,4,3,16,0,0);
		TaskDateTime second=new TaskDateTime(2015,4,3,16,0,0);
		assertEquals(0,first.compareTo(second));
	}

	@Test
	public final void testFormattedToString() {
		TaskDateTime first=new TaskDateTime(2015,4,3,16,0,0);
		assertEquals("4:00 PM 03 Apr 2015",first.formattedToString());
	}

	@Test
	public final void testPresentableToString() {
		TaskDateTime first=new TaskDateTime(2015,4,3,16,0);
		assertEquals("03 Apr 2015 4:00 PM",first.presentableToString());
	}

	@Test
	public final void testGenerateDateCode() {
		TaskDateTime first=new TaskDateTime();
		assertEquals("01-01-2000",first.generateDateCode());
	}
	@Test
	public final void testSet()
	{
		TaskDateTime first=new TaskDateTime();
		first.set(2012,6,13);
		logger.debug(first.formattedToString());
		first.set(2012, 6,13,11,21,0);
		logger.debug(first.formattedToString());
	}
	@Test
	public final void testSetTimeInMilli()
	{
		TaskDateTime first=new TaskDateTime();
		first.setTimeMilli(0);
		logger.debug(first.toString());
	}
	@Test
	public final void testGenerateTimeCode() {
		TaskDateTime first = new TaskDateTime(14,11);
		assertEquals("141100",first.generateTimeCode());
		TaskDateTime second=new TaskDateTime(2012,6,5);
		assertEquals("235959",second.generateTimeCode());
	}
	@Test
	public final void get()
	{
		TaskDateTime first=new TaskDateTime(2015,4,3,23,0,0);
		assertEquals(23,first.get(Calendar.HOUR_OF_DAY));
		assertEquals(4,first.get(Calendar.MONTH));
	}
	@Test
	public final void getTime()
	{
		TaskDateTime test=new TaskDateTime(2011,4,5,23,54,59);
		logger.debug(test.getDate().formattedToString());
		logger.debug(test.getTime().formattedToString());
	}
	@Test
	public final void dateToXml()
	{
		TaskDateTime test=new TaskDateTime(2012,6,13,23,55,0);
		String xml=test.dateToXml();
		String xml2=test.dateTimeToXml();
		logger.debug(xml);
		logger.debug(xml2);
	}
	@Test
	public final void xmlToDate()
	{
		
	}

}