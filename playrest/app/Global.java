

import java.util.concurrent.TimeUnit;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Akka;
import redis.clients.jedis.Jedis;
import scala.concurrent.duration.FiniteDuration;

import com.typesafe.plugin.RedisPlugin;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		Logger.info("Application has started");
		Akka.system().scheduler().scheduleOnce(
				FiniteDuration.apply(10, TimeUnit.MILLISECONDS),
				new Runnable() {
					public void run() {
						Logger.info("Run method startedS");
						Jedis j = play.Play.application().plugin(RedisPlugin.class).jedisPool().getResource();
						j.subscribe(new JedisListener(), "dummyChannel");
					}
				},
				Akka.system().dispatcher()
		);
	} 
	
	
    
}
