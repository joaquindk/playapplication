package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.typesafe.plugin.RedisPlugin;

import play.*;
import play.mvc.*;
import redis.clients.jedis.Jedis;
import views.html.*;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}

	/**
	 * REST method used to receive a dummy json object
	 * 
	 * @return
	 */
	@BodyParser.Of(BodyParser.Json.class)
	public static Result receiveDummy() {
		JsonNode json = request().body().asJson();
	    Jedis jedis = play.Play.application().plugin(RedisPlugin.class).jedisPool().getResource();
	    try {
			jedis.publish("dummyChannel", convertNode(json));
			Logger.info("Message published to redis: " + convertNode(json));
		} catch (JsonProcessingException e) {
			Logger.error("Error publishing json", e);
		}
	    
		return ok("JSON Dummy received");
	}
	
	private static final ObjectMapper SORTED_MAPPER = new ObjectMapper();
	static {
	    SORTED_MAPPER.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
	}

	private static String convertNode(final JsonNode node) throws JsonProcessingException {
	    final Object obj = SORTED_MAPPER.treeToValue(node, Object.class);
	    final String json = SORTED_MAPPER.writeValueAsString(obj);
	    return json;
	}

}
