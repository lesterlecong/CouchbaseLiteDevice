package com.lesterprojects.couchbaselite;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.lesterprojects.couchbaselite.CouchbaseLite;



public class SaveReadDataTest {

	private static CouchbaseLite cbLite;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cbLite = new CouchbaseLite("localhost", 4984);
		cbLite.databaseName("trialdb");
		cbLite.initialize();
		cbLite.createDocument("testdoc");
		cbLite.selectDocument("testdoc");
	}


	@Test
	public void testSaveRead() {
		Object trialObject = (Object) "trialValue";
		cbLite.save("trialKey", trialObject);
		
		String trialString = (String) cbLite.read("trialKey");
		assertTrue("Successfully save and read", trialString == "trialValue");
	}

}
