package Test.OperationTests;

import static org.junit.Assert.*;

import operation.Add;
import operation.GoogleCalendarOp;

import org.junit.Test;

import storagecontroller.StorageManager;

import data.Task;

public class GoogleCalenderOpTests {

	@Test
	public void test() {
		Add newadd=new Add();
		Task[] result = newadd.execute("add go to party on 3 July at 5pm");
						
		
		result = newadd.execute("add go study 3pm to 6pm tmr @work");
		result = newadd.execute("add do homework today weekly - 5");
		assertEquals(7,StorageManager.getAllTasks().length);
		
		GoogleCalendarOp newGCal=new GoogleCalendarOp();
		
	
		newGCal.execute("logout");
		newGCal.execute("export.gcal");
		newGCal.execute("import.gcal");
		newGCal.execute("sync.gcal");
		newGCal.execute("login dfdsf   sdfsd   ");
		newGCal.execute("login jid.troubleshoot jotitdown");
		newGCal.execute("export.gcal");
		newGCal.execute("import.gcal");
		newGCal.execute("sync.gcal");
		newGCal.undo();
		newGCal.redo();
		newGCal.execute("logout");
	}

}
