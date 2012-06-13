package Test.OperationTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import operation.Add;
import operation.Delete;

import org.junit.Test;


import storagecontroller.StorageManager;

import data.Task;

public class DeleteTest {

	
	public static void setUpBeforeClass() throws Exception {
		

		
	
	}
	@Test
	public void test() {
		

		Add newadd=new Add();
		Task[] result = newadd.execute("add go to party on 3 July at 5pm");
						
		
		result = newadd.execute("add go study 3pm to 6pm tmr @work");
		result = newadd.execute("add do homework today weekly - 5");
	
		assertEquals(7,StorageManager.getAllTasks().length);	
		Delete newDel=new Delete();
		Task deltasks[]=newDel.execute("delete go study");
		assertEquals(7,StorageManager.getAllTasks().length);	
		newDel.undo();
		assertEquals(7,StorageManager.getAllTasks().length);	
		newDel.redo();
		assertEquals(7,StorageManager.getAllTasks().length);	
		Task finalTaskdeleted[]=newDel.execute("delete "+deltasks[0].getTaskId());
		assertEquals(6,StorageManager.getAllTasks().length);	
		if (newDel.isUndoAble()){
			newDel.undo();
			assertEquals(7,StorageManager.getAllTasks().length);	
			newDel.redo();
			assertEquals(6,StorageManager.getAllTasks().length);	
		}
		
		newDel=new Delete("delete.all");
		Task delalltasks[]=newDel.execute("delete.all do homework");
		assertEquals(6,StorageManager.getAllTasks().length);	
		Task finalAllTaskdeleted[]=newDel.execute("delete.all "+delalltasks[0].getTaskId());
		assertEquals(1,StorageManager.getAllTasks().length);	
		newDel.undo();
		assertEquals(6,StorageManager.getAllTasks().length);	
		newDel.redo();
		assertEquals(1,StorageManager.getAllTasks().length);	
		newDel=new Delete("delete.all");
		
		delalltasks=newDel.execute("delete.all go to party");
		finalAllTaskdeleted=newDel.execute("delete.all 2pm to 3pm");
		assertEquals(1,StorageManager.getAllTasks().length);	
		delalltasks=newDel.execute("delete.all go to party");
		finalAllTaskdeleted=newDel.execute("delete.all "+delalltasks[0].getTaskId());
		
		
		assertEquals(0,StorageManager.getAllTasks().length);	
		}
		
	
}
