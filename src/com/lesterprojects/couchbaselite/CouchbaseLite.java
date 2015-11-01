package com.lesterprojects.couchbaselite;

import com.couchbase.lite.*;
import com.couchbase.lite.replicator.*;
import com.couchbase.lite.util.Log;

import java.io.*;
import java.util.Map;
import java.net.*;



public class CouchbaseLite {
	private Manager manager;
	private Database database;
	private Document document;
	private Replication pullReplication;
	private Replication pushReplication;
	
	private String host;
	private int port;
	private String databaseName;
	private String currentDocumentID = "";
	
	CouchbaseLite(){
	
	}
	
	CouchbaseLite(String hostName, int portNumber){
		host = hostName;
		port = portNumber;
	}
	
	public void setHost(String hostName){
		host = hostName;
	}
	
	public void setPort(int portNumber){
		port = portNumber;
	}
	
	public void databaseName(String dbName){
		databaseName = dbName;
	}
	
	public void initialize() throws CouchbaseLiteException{
		try{
			manager = new Manager(new JavaContext("data"), Manager.DEFAULT_OPTIONS);
			createDatabase();
			setupReplicator();
			Manager.enableLogging("CBLite", Log.VERBOSE);
		}catch(IOException e){
			Log.e("COUCHBASELITE", "Cannot create Manager instance", e);
		}
	}
	
	public boolean createDocument(){
		Document createdDocument =  database.createDocument();
		currentDocumentID = createdDocument.getId();
		
		return (createdDocument != null);
	}
	
	public String getCurrentDocumentID(){
		return currentDocumentID;
	}
	public boolean createDocument(String id){
		Document createdDocument = database.getDocument(id);
		currentDocumentID = id;
		
		return (createdDocument != null);
	}
	
	public boolean selectDocument(String id){
		if(database == null){
			System.out.println("database is null");
		}
		document = database.getDocument(id);
		
		if(document != null){
			currentDocumentID = id;
		}
		
		return (document != null);
	}
	
	public boolean deleteDocument(String id) throws CouchbaseLiteException{
		boolean result = false;
		document = database.getDocument(id);
		if(document != null){
			result = document.delete();
		}
		
		return result;
	}
		
	
	public void save(final String key, final Object value){
		if(document == null){
			System.out.println("Document is null");
		}
		try {
			document.update(new Document.DocumentUpdater() {
				
				@Override
				public boolean update(UnsavedRevision newRevision) {
					Map<String, Object> properties = newRevision.getUserProperties();
					properties.put(key, value);
					newRevision.setUserProperties(properties);
					return true;
				}
			});
		} catch (CouchbaseLiteException e) {
			e.printStackTrace();
		}
	}
	
	
	public void save(Map<String, Object> data){
		for(Map.Entry<String, Object> dataEntry : data.entrySet()){
			save(dataEntry.getKey(), dataEntry.getValue());
		}
	}
	
	public String read(String key){
		Object readDataObject = document.getProperty(key);
		String readDataString = "";
		
		if(readDataObject != null){
			readDataString = readDataObject.toString();
		}
		
		return readDataString;
	}
	
	public void replicate(){
		pushReplication.start();
	}
	
	private void createDatabase() throws CouchbaseLiteException{
		System.out.println("Database name:" + databaseName);
		if(manager != null && !databaseName.isEmpty()){
				database = manager.getDatabase(databaseName);
		}
	}
	
	private void setupReplicator() throws MalformedURLException{
		URL url = new URL("http://" + host + ":" + Integer.toString(port) + "/" + databaseName );
		pullReplication = database.createPullReplication(url);
		pushReplication = database.createPushReplication(url);
		
		pullReplication.setContinuous(true);
	}
}
