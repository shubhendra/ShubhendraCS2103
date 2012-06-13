package Test.OperationTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({  DeleteTest.class, EditTest.class,
		SearchTest.class, AddTests.class })
public class AllTests {

}
