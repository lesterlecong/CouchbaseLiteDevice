package couchbaselite;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class SelectDocumentTest {
	
	private static CouchbaseLite cbLite;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cbLite = new CouchbaseLite("localhost", 4984);
		cbLite.databaseName("trialdb");
		cbLite.initialize();
		cbLite.createDocument("trialdoc");
	}
	
	
	@Test
	public void testSelectDocument() {
		assertTrue("Document successfully SELECTED", cbLite.selectDocument("trialdoc"));
	}

}
