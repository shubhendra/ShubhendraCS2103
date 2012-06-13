/**
 * 
 */
package Test.OperationTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import operation.Add;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import storagecontroller.StorageManager;

import data.Task;

/**
 * @author Shubhendra Agrawal
 *
 */
public class AddTests {

	/**
	 * @throws java.lang.Exception
	 */
	
	private final String[] TestEvents={ "add go to party on $date$ at $time$",
		"add Study for CS2103 at $time$ on $date$ @work @party",
		"add Go for holiday from $time$ to $time2$ on $date$",
		"add Go for hiking from $date$ $time$ to $date2$ $time2$",
		"add Presentation from $time$ $date$ to $time2$ $date2$",
		"insert finish project CS 2103 by $time$ on $date$",
		"insert do homework $time$ $date$ $recurring$ -$times$",
		"insert go study $time$ to $time2$ $date$ @work",
		"insert Do the presentation $time$", 
		"insert finish work "};		
		//"insert go back Home $date$"};
	
	private static String[] Time1={"2 pm", "3PM", "02.30am", "23:45", "00:45", "12 PM"};
	private static String[] Time2={"2.45 pm", "3.50 PM", "03 am", "23:50", "4:45", "1.00 PM"};
	private static String[] recurring={"daily","weekly"};
	private static String[] times={"1","3 times"};
	private static String[] Date1={"12/7/2012", "12/9","12th Jul 2012", "1st Sep", "today", "tmr", "tomorrow" };
	private static String[] Date2={"13/8/2012", "12/10","12th August 2012", "2nd Sep", "today", "tmr", "tomorrow" };
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
	}
	private ArrayList<Task> addedTasks=new ArrayList<Task>();
	@Test
	public void test() {
		System.out.println("executing");
		int c=0;
		int d=0;
		Add newAdd=new Add();
		for(int i=0;i<Time1.length;i++){
			for(int j=0;j<Date1.length;j++){
				for (int k=0;k<TestEvents.length;k++){
					String command=TestEvents[k];
					command=command.replace("$date$", Date1[j]);
				//	System.out.println(command);
					command=command.replace("$time$", Time1[i]);
				//	System.out.println(command);
					command=command.replace("$date2$", Date2[j]);
				//	System.out.println(command);
					command=command.replace("$time2$", Time2[i]);
				//	System.out.println(command);
					command=command.replace("$recurring$", recurring[1]);
				//	System.out.println(command);
					command=command.replace("$times$", times[1]);
				//	System.out.println(command);
					
					if (k<1){
						newAdd= new Add();
					}
					else
						newAdd= new Add(command.split("\\s+")[0]);
					Task[] added=newAdd.execute(command);
					if (added!=null){
						d++;
						Collections.addAll(addedTasks,added);
					} 
					c++;
					System.out.println(c);
					if (newAdd.isUndoAble()){
					newAdd.undo();
					newAdd.redo();
					}
					newAdd.undo();
					newAdd.redo();
				}
			}
		}
		
		//assertEquals(d,addedTasks.size());
		System.out.println(d);
		System.out.println(addedTasks.size());
		
	}

}
