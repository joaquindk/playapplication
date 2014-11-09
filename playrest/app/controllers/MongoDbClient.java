package controllers;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;


public class MongoDbClient {	
	
	private static Mongo mongo;
	private static DB falconTest;
	
	private static MongoDbClient instance = null;

	private MongoDbClient() throws UnknownHostException, MongoException{
		 mongo = new Mongo("localhost" ,27017 );
		 falconTest = mongo.getDB("falcontest");
		 Logger.info("Mongo client created");
	}
	
	public static MongoDbClient getInstance() throws UnknownHostException, MongoException{
		if(instance == null){
			instance = new MongoDbClient();
		}
		return instance;
	}
	
	public synchronized void saveDummy(String dummy){
		BasicDBObject document = new BasicDBObject();
		document.put("dummyString", dummy);
		document.put("createdDate", new Date());
		falconTest.getCollection("dummyJson").insert(document);
		Logger.info("Dummy written into mongo: " + dummy);
	}
	
	public List<String> getAllDummies(){
		List<String> jsonDummies = new ArrayList<String>();
		DBCursor result = falconTest.getCollection("dummyJson").find();
		while(result.hasNext()){
			DBObject obj = result.next();
			jsonDummies.add((String)obj.get("dummyString"));
		}
		return jsonDummies;
	}
	
	public void close(){
		mongo.close();
	}
}
