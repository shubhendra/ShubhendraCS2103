package Test;
import data.Task;
import data.TaskDateTime;
import gcal.GoogleCalendar;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import storagecontroller.StorageManager;
import com.google.gdata.data.extensions.Reminder.Method;

public class GoogleCalendarTest {
	GoogleCalendar test=new GoogleCalendar();
	private Logger logger = Logger.getLogger(GoogleCalendarTest.class.getName());
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	test.logout();
	}
	@Test
	public final void testLogin() {
		test.login("jid.troubleshoot@gmail.com", "jotitdown");
		assertEquals(true,test.isLoggedIn());
		test.login("jid.troubleshoot@gmail.com", "aaaaaaaa");
	}

	@Test
	public final void testAddTask() {
	test.login("jid.troubleshoot@gmail.com", "jotitdown");
		TaskDateTime start1=new TaskDateTime(2012,6,10,21,0,0);
		TaskDateTime end1=new TaskDateTime(2012,6,10,23,0,0);
		ArrayList<String> testLabels=new ArrayList<String>();
		testLabels.add("Work");
		Task newTask=new Task("Go to office","",start1,end1,testLabels,"");
		test.addTask(newTask, 0, Method.NONE);
		logger.debug(newTask.getGCalId());
	}

	@Test
	public final void testDeleteEvent() {
		test.login("jid.troubleshoot@gmail.com", "jotitdown");
		TaskDateTime start1=new TaskDateTime(2012,6,10,21,0,0);
		TaskDateTime end1=new TaskDateTime(2012,6,10,23,0,0);
		Task test2=new Task("Go to airport",null,start1,end1,null,null);
		test.deleteEvent(test2);
	}

	@Test
	public final void testUpdateEvent() {
		test.login("jid.troubleshoot@gmail.com", "jotitdown");
		TaskDateTime start1=new TaskDateTime(2012,6,10,21,0,0);
		TaskDateTime end1=new TaskDateTime(2012,6,10,23,0,0);
		Task test2=new Task("Go to airport",null,start1,end1,null,null);
		Task test1=new Task("Go to airport terminal 2",null,start1,end1,null,null);
		test.updateEvent(test2, test1);
	}
	
	@Test
	public final void testImportFromGcal()
	{
		test.login("jid.troubleshoot@gmail.com", "jotitdown");
		test.importFromGcal();
	}
	@Test
	public final void testExportToGcal()
	{
		test.login("jid.troubleshoot@gmail.com", "jotitdown");
		test.exportToGcal();
	}
	@Test
	public final void sync()
	{
		test.login("jid.troubleshoot@gmail.com", "jotitdown");
		logger.debug(StorageManager.getAllTasks().length);
		StorageManager.loadFile();
		for(int i=0;i<StorageManager.getAllTasks().length;i++)
		{
			logger.debug(StorageManager.getAllTasks()[i].toString());
		}
		test.sync();
		for(int i=0;i<StorageManager.getAllTasks().length;i++)
		{
			logger.debug(StorageManager.getAllTasks()[i].toString());
		}
	}

}
