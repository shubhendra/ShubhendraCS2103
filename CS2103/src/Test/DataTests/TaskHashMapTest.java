package Test.DataTests;
import java.util.ArrayList;

import data.TaskDateTime;
import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.TaskHashMap;
import data.Task;
public class TaskHashMapTest {
	TaskHashMap test=new TaskHashMap();
	TaskDateTime start=new TaskDateTime(2012,6,3,14,0,0);
	TaskDateTime end=new TaskDateTime(2012,6,3,17,0,0);
	Task testTask=new Task("lecture",null,start,end,null,"weekly");
	private static Logger logger = Logger.getLogger(TaskHashMapTest.class);
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testAddTask() {
		test.addTask(testTask);
		assertEquals(1,test.getKeySet().size());
		test.deleteTaskById(testTask.getTaskId());
	}

	@Test
	public final void testGenerateUniqueId() {
		assertEquals("$$__03-06-2012170000", test.generateUniqueId(testTask).substring(0, 20));
		Task testTask2=new Task("tutorial","",start,null,new ArrayList<String>(),"");
		test.addTask(testTask2);
	}

	@Test
	public final void testDeleteTask() {
		test.addTask(testTask);
		assertEquals(1,test.getKeySet().size());
		test.deleteTask(testTask);
		assertEquals(0,test.getKeySet().size());
		testTask=null;
		logger.debug(test.addTask(testTask));
		test.addTaskById(testTask);
	}

	@Test
	public final void testGetTaskById() {
		test.addTask(testTask);
		assertEquals(testTask,test.getTaskById(testTask.getTaskId()));
	}

	@Test
	public final void testGetHashMapSize() {
		test.addTask(testTask);
		assertEquals(1,test.getKeySet().size());
		test.clearHashMap();
		assertEquals(0,test.getHashMapSize());
	}

}
