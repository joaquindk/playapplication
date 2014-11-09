import play.Logger;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPubSub;


public class JedisListener extends JedisPubSub {

	@Override
	public void onMessage(String arg0, String arg1) {
		Logger.info("Message received!!! " + arg0 + "--" + arg1);
		
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
