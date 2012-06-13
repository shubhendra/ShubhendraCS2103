package Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import Test.OperationTests.AddTests;
import Test.OperationTests.DeleteTest;
import Test.OperationTests.EditTest;
import Test.OperationTests.SearchTest;

@RunWith(Suite.class)
@SuiteClasses({ FileHandlerTest.class, 
		StorageManagerTest.class, TaskDateTimeTest.class,
		TaskHashMapTest.class, TaskTesting.class, AddTests.class,
		DeleteTest.class, EditTest.class,SearchTest.class})
public class AllTests {

}
