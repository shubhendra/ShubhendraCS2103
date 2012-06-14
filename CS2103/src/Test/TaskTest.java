package Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.TaskDateTime;
import data.Task;
import storagecontroller.StorageManager;

public class TaskTest {
	Task zero=new Task("aaa");
	TaskDateTime temp2=new TaskDateTime();
	TaskDateTime start1=new TaskDateTime(2012,7,1,16,0,0);
	TaskDateTime end1=new TaskDateTime(2012,7,1,18,0,0);
	TaskDateTime start2=new TaskDateTime(2012,7,1);
	TaskDateTime end2=new TaskDateTime(2012,7,2);
	Task one=new Task("Meeting UTOWN","",start1,end1,null,"");
	private Logger logger = Logger.getLogger(TaskTest.class.getName());
	Task two=new Task("Meeting UTOWN","",start1,end1,null,"",false,false);
	Task three=new Task("Meeting UTOWN","",start1,end1,null);
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGet() {
		StorageManager.addTask(one);
		assertEquals("$$__01-07-2012180000",one.getTaskId().substring(0, 20));
		assertEquals(null,one.getLabels());
		assertEquals("Meeting UTOWN",one.getName());
		assertEquals("",one.getGCalId());
		assertEquals(start1,one.getStart());
		assertEquals(end1,one.getEnd());
		assertEquals("",one.getRecurringId());
		assertEquals("",one.getRecurring());
	}

	@Test
	public final void testSet()
	{
		Task newTask=new Task();
		newTask.setGCalId("<CMPT:false><IMPT:false><DEAD:false><RECUR:><RECURID:><LABELS:>");
		newTask.setCompleted(false);
		newTask.setImportant(false);
		newTask.setDeadline(true);
		TaskDateTime end=new TaskDateTime(2012,7,1,3,0,0);
		newTask.setEnd(end);
		newTask.setStart(null);
		newTask.setName("Sleep");
		newTask.setRecurring("weekly");
		newTask.setRecurring("");
		ArrayList<String> labelsList=new ArrayList<String>();
		labelsList.add("Play");
		newTask.setLabels(labelsList);
		logger.debug(newTask.toString());
	}

	@Test
	public final void testIsEqual() {
		assertEquals(true,one.isEqual(two));
		assertEquals(true,one.isEqual(zero));
	}

	@Test
	public final void testIsIdenticalTo() {
		assertEquals(true,one.isIdenticalTo(two));
		assertEquals(false,one.isIdenticalTo(zero));
	}

	@Test
	public final void testToString() {
		logger.debug(one.toString());
		logger.debug(two.toString());
		ArrayList<String> labelsList=new ArrayList<String>();
		labelsList.add("Play");
		Task four=new Task("meeting UTOWN","",start1,null,labelsList,"weekly");
		logger.debug(four.toString());
		Task five=new Task("meeting UTOWN","",null,end1,labelsList,"weekly",false,true);
		logger.debug(five.toString());
		Task six=new Task("meting UTOWN","",start2,end2,labelsList,"weekly");
		logger.debug(six.toString());
		Task seven=new Task("meeting UTOWN","",null,start2,labelsList,"weekly");
		logger.debug(seven.toString());
		Task eight=new Task("meeting UTOWN","",start2,null,labelsList,"weekly");
		logger.debug(eight.toString());
		assertEquals(true,(one.toString().equals(two.toString())));
	}
	
	@Test
	public final void testToggle()
	{
		one.toggleCompleted();
		one.toggleDeadline();
		one.toggleImportant();
		assertEquals(true,one.getCompleted());
		assertEquals(true,one.getImportant());
		assertEquals(true,one.getDeadline());
	}

	

}
