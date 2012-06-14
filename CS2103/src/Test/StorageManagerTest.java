package Test;
import java.util.ArrayList;

import data.TaskDateTime;
import data.Task;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import storagecontroller.StorageManager;
import org.apache.log4j.Logger;
public class StorageManagerTest {
		//StorageManager managerTest=new StorageManager();
	TaskDateTime start1=new TaskDateTime(2012,6,6,14,0,0);
	TaskDateTime end1=new TaskDateTime(2012,6,6,15,30,0);
	ArrayList<String> labelsTest=new ArrayList<String>();
	private static Logger logger = Logger.getLogger(StorageManagerTest.class);
	
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
public final void testAddTask() {
		labelsTest.add("NUS");
		labelsTest.add("Study");
		Task first=new Task("Go for tutorial",null,start1,end1,labelsTest,null);
		StorageManager.addTask(first);
		assertEquals(1,StorageManager.getAllTasks().length);	
}

	@Test
	public final void testDeleteTaskTask() {
		labelsTest.add("NUS");
		Task second=new Task("Go for tutorial",null,start1,end1,labelsTest,null,false,true);
		StorageManager.addTask(second);
		assertEquals(2,StorageManager.getAllTasks().length);
		StorageManager.deleteTask(second);
	}

	@Test
	public final void testGetAllTasks() {
		for(int i=0;i<StorageManager.getAllTasks().length;i++)
		{
			logger.debug(StorageManager.getAllTasks()[i].toString());
		}
	}
	
	@Test
	public final void testSaveFile() {
		StorageManager.saveFile();
		assertEquals(1,StorageManager.getAllTasks().length);
	}
	
	@Test
	public final void testLoadFile() {
		StorageManager.loadFile();
		assertEquals(1,StorageManager.getAllTasks().length);
	}
	@Test
	public final void testReplaceTask(){
		Task test1=new Task("Go UTOWN",null,start1, end1,null,"Daily");
		StorageManager.addTask(test1);
		Task test2=new Task("Go UTOWN's office",null, start1,end1,null,"One");
		StorageManager.replaceTask(test1, test2);
		for(int i=0;i<StorageManager.getAllTasks().length;i++){
			logger.debug(StorageManager.getAllTasks()[i].toString());
		}
	}
	@Test
	public final void testSaveEmailId()
	{
		StorageManager.saveEmailId("jid.troubleshoot@gmail.com");
	}
	@Test
	public final void testLoadEmailId()
	{
		String temp=StorageManager.loadEmailId();
		System.out.println(temp);
	}
	@Test
	public final void testLoadDate()
	{
		String date=StorageManager.loadDate();
		logger.debug(date);
	}
	@Test
	public final void testSaveDate()
	{
		StorageManager.saveDate("01 Jan 2002");
		String date=StorageManager.loadDate();
		logger.debug(date);
		StorageManager.saveDate("");
		String date2=StorageManager.loadDate();
		logger.debug(date2);
	}
	@Test
	public final void testExportToTxt()
	{
		StorageManager.exportToTxt("temp.txt");
	}
	@Test
	public final void testDeleteTask()
	{
		labelsTest.add("NUS");
		Task second=new Task("Go for tutorial",null,start1,end1,labelsTest,null);
		int size=StorageManager.getAllTasks().length;
		StorageManager.addTask(second);
		StorageManager.deleteTask(second.getTaskId());
		assertEquals(size,StorageManager.getAllTasks().length);
	}
	@Test
	public final void testLoadArchive()
	{
		if(StorageManager.loadArchive())
		{
			for(int i=0;i<StorageManager.getAllArchivedTasks().length;i++)
			{
				logger.debug(StorageManager.getAllArchivedTasks()[i].toString());
			}
		}
		else{
				logger.debug("Error");
			}
	}
	@Test
	public final void testSaveArchives()
	{
		if(StorageManager.saveArchive())
			logger.debug("true");
		else
			logger.debug("false");
	}
	@Test 
	public final void testAddDeleteArchivedTask()
	{
		TaskDateTime start=new TaskDateTime(2012,6,13,6,45,0);
		TaskDateTime end=new TaskDateTime(2012,6,13,7,0,0);
		Task newArchived=new Task("Go back to shear's hall","",start,end,new ArrayList<String>(),"");
		StorageManager.addArchivedTask(newArchived);
		assertEquals(2,StorageManager.getAllArchivedTasks().length);
		StorageManager.deleteArchivedTask(newArchived);
		assertEquals(1,StorageManager.getAllArchivedTasks().length);
	}
	@Test
	
	public final void testGetTaskByRecurrenceId()
	{
		Task[] allTasks=StorageManager.getTaskByRecurrenceID("");
		assertEquals(allTasks,StorageManager.getAllTasks());
	}
}
