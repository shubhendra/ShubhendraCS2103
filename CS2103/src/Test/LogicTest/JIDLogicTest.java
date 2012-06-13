/**
 * 
 */
package Test.LogicTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import data.Task;
import logic.JIDLogic;

/**
 * @author Shubhendra Agrawal
 *
 */
public class JIDLogicTest {

	/**
	 * @throws java.lang.Exception
	 */
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

	@Test
	public void test() {
		JIDLogic.JIDLogic_init();
		JIDLogic.setCommand("");
		JIDLogic.executeCommand(null);
		
		JIDLogic.setCommand("add");
		assertEquals("add",JIDLogic.getCommand());
		Task[] result=JIDLogic.executeCommand("add go to school at 3pm tmr");
		assertEquals(1, result.length);
		JIDLogic.setCommand("undo");
	
		result=JIDLogic.executeCommand("undo");
		assertEquals(1, result.length);
		result=JIDLogic.executeCommand("undo");
		//assertEquals(1, result.length);
		JIDLogic.setCommand("redo");
		result=JIDLogic.executeCommand("redo");
		assertEquals(1, result.length);
		result=JIDLogic.executeCommand("redo");
		//assertEquals(1, result.length);
		JIDLogic.setCommand("search");
		result=JIDLogic.executeCommand("search go to school");
		assertNotNull(result);
		JIDLogic.setCommand("undo");
		result=JIDLogic.executeCommand("undo");
	//	assertEquals(1, result.length);
		JIDLogic.JIDLogic_close();
	}

}
