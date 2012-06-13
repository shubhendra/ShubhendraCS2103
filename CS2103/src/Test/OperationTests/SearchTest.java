package Test.OperationTests;

import static org.junit.Assert.*;

import operation.Add;
import operation.Search;

import org.junit.Test;

import storagecontroller.StorageManager;

import data.Task;

public class SearchTest {

	@Test
	public void test() {
		StorageManager.loadFile();
		Add newadd=new Add();
		Task[] result = newadd.execute("add go to party on 3 July at 5pm");
		result[0].setCompleted(true);
		result = newadd.execute("add go study 3pm to 6pm tmr @work");
		result = newadd.execute("add do homework today weekly - 5");
		result = newadd.execute("add *go home by 4pm tmr @school");
		result = newadd.execute("add *go home by 3pm today");
		Search newSearch=new Search();
		result=newSearch.execute("search from 2 pm to 7 pm" );
		assertEquals(4,result.length);
		result=newSearch.execute("search from today to tmr" );
		assertEquals(4,result.length);
		result=newSearch.execute("search from 2pm today to 7pm tmr" );
		
		assertEquals(4,result.length);	
		result=newSearch.execute("search from 2pm today to tmr" );
		result=newSearch.execute("search from today to 7pm tmr" );
		result=newSearch.execute("find *.*");
		assertEquals(9,result.length);
		result=newSearch.searchTodaysTasks();
		//assertEquals(1,result.length);
		result=newSearch.execute("search @work");
		assertEquals(1,result.length);
		result=newSearch.execute("search *by 4pm" );
		assertEquals(1,result.length);
		result=newSearch.execute("search  " );
		assertEquals(9,result.length);
		result=newSearch.execute("search by today" );
		assertEquals(1,result.length);
		result=newSearch.execute("search go to party on 3 July at 5pm" );
		assertEquals(1,result.length);
		result=newSearch.execute("search go on 3 June at 5pm" );
		
		newSearch.undo();
		newSearch.redo();
	}

}
