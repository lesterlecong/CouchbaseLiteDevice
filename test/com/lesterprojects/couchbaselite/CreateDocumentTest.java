package com.lesterprojects.couchbaselite;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.lesterprojects.couchbaselite.CouchbaseLite;

public class CreateDocumentTest {
	
	private static CouchbaseLite cbLite;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cbLite = new CouchbaseLite("localhost", 4984);
		cbLite.databaseName("trialdb");
		cbLite.initialize();
	}

	@Test
	public void testCreateDocument() {
		assertTrue("Document successfully created", cbLite.createDocument());
	}

	@Test
	public void testCreateDocumentString() {
		assertTrue("Document with id successfully created", cbLite.createDocument("trialDoc"));
	}

}
