package Test.OperationTests;

import static org.junit.Assert.*;

import operation.Add;
import operation.Modify;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import storagecontroller.StorageManager;

import data.Task;

public class EditTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		//StorageManager.loadFile();
		Add newadd=new Add();
		String toBeAddedOld = "add go to party on $date$ at 5pm";
		String toBeEditedNew = "add go to party on $date$ at 5pm";
		//result = newadd.execute("add go study 3pm to 6pm tmr @work");
		String[] dates={"24 July 2012", "25 July 2012"};
		toBeAddedOld=toBeAddedOld.replace("$date$", dates[0]);
		toBeEditedNew=toBeEditedNew.replace("$date$", dates[1]);
		Task[] result=newadd.execute(toBeAddedOld);
		assertNotNull(result);
		assertEquals(1, result.length);
		Task oldTask=result[0];
		Modify newEdit=new Modify("edit");
		result=newEdit.execute("edit go to");
		assertEquals(1, result.length);
		result=newEdit.execute("edit "+oldTask.getTaskId());
		newEdit.redo();
		assertEquals(1, result.length);
		assertEquals(oldTask, result[0]);
		newEdit=new Modify("canceledit");
		result=newEdit.execute("edit "+toBeEditedNew);
		//assertNull(0, result.length);
		newEdit=new Modify("edit");
		result=newEdit.execute("edit "+oldTask.getTaskId());
		newEdit.undo();
		
		
		assertEquals(1, result.length);
		assertEquals(oldTask, result[0]);
		
		newEdit=new Modify();
		result=newEdit.execute("edit "+toBeEditedNew);
		
		assertEquals(1, result.length);

		Task newTask=result[0];
		assertEquals(true,newEdit.isUndoAble());
		result=newEdit.undo();
		assertEquals(oldTask,result[0]);
		result=newEdit.redo();
		assertEquals(newTask,result[0]);
		
		
	}

}
