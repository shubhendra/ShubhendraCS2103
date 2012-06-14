package Test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Task;
import data.TaskDateTime;
import data.TaskHashMap;

import storagecontroller.FileHandler;
import org.apache.log4j.Logger;
public class FileHandlerTest {
	private static Logger logger = Logger.getLogger(FileHandlerTest.class);
	TaskHashMap testMap=new TaskHashMap();
	@Before
	public void setUp() throws Exception {
	
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testWriteToFile() {
		FileHandler handler=new FileHandler("JotItDownDatabase.xml");
		handler.writeToFile(testMap);
		TaskDateTime start=new TaskDateTime(2012,6,13,7,0,0);
		TaskDateTime end=new TaskDateTime(2012,6,13,7,0,1);
		Task addTask=new Task("Drink water","",start,end,null,"Personal");
		testMap.addTask(addTask);
		handler.writeToFile(testMap);
		FileHandler exceptionTest=new FileHandler("JoItDown.txt");
		exceptionTest.writeToFile(testMap);
	}

	@Test
	public final void testReadFromFile() {
		testMap.clearHashMap();
		FileHandler handler=new FileHandler("JotItDownDataBase.xml");
		handler.readFromFile(testMap);
		logger.debug(testMap.getKeySet());
		FileHandler exceptionTest=new FileHandler("JoItDown.txt");
		exceptionTest.readFromFile(testMap);
	}

	@Test
	public final void testReadDate() {
		FileHandler handler=new FileHandler("JotItDownAgenda.txt");
		String testString=handler.readDate();
		logger.debug(testString);
	}

	@Test
	public final void testWriteEmailId() {
		FileHandler handler=new FileHandler("JotItDownEMail.txt");
		handler.writeEmailId("jid.troubleshoot@gmail.com");
	}

	@Test
	public final void testWriteDate() {
		FileHandler handler=new FileHandler("JotItDownAgenda.txt");
		handler.writeDate(TaskDateTime.getCurrentDate().formattedToString());
	}

	@Test
	public final void testReadEmailId() {
		FileHandler handler=new FileHandler("JotItDownEMail.txt");
		String emailId=handler.readEmailId();
		logger.debug(emailId);
		FileHandler testException=new FileHandler("JotItDown.txt");
		String date=testException.readDate();
		logger.debug(date);
	}

}
