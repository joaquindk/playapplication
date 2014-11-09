import java.net.UnknownHostException;

import play.Logger;
import redis.clients.jedis.JedisPubSub;

import com.mongodb.MongoException;

import controllers.MongoDbClient;


public class JedisListener extends JedisPubSub {
	
	MongoDbClient dbClient;

	@Override
	public synchronized void onMessage(String arg0, String arg1) {
		Logger.info("Message received!!! " + arg0 + "--" + arg1);
		try {
			dbClient.getInstance().saveDummy(arg1);
		} catch (UnknownHostException | MongoException e) {
			Logger.error("Error saving message", e);
		}
	}

	@Override
	public void onPMessage(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPSubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPUnsubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnsubscribe(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

}
