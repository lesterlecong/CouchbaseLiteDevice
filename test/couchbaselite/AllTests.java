package couchbaselite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CreateDocumentTest.class, SaveReadDataTest.class, SelectDocumentTest.class })
public class AllTests {

}
