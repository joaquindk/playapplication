import java.net.UnknownHostException;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;


public class MongoDbClient {	
	
	Mongo mongo;
	DB falconTest;

	public MongoDbClient() throws UnknownHostException, MongoException{
		 mongo = new Mongo("localhost" ,27017 );
		 falconTest = mongo.getDB("falcontest");
	}
	
	public void saveDummy(String dummy){
		BasicDBObject document = new BasicDBObject();
		document.put("json", dummy);
		document.put("createdDate", new Date());
		falconTest.getCollection("dummyJson").save(document);
	}
}
