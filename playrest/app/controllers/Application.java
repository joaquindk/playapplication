package controllers;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.MongoException;
import com.typesafe.plugin.RedisPlugin;

import play.Logger;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import redis.clients.jedis.Jedis;
import views.html.*;

public class Application extends Controller {
	
	private static List<WebSocket.Out<String>> dummySockets = new ArrayList<WebSocket.Out<String>>();
	
	private static Jedis jedis = play.Play.application().plugin(RedisPlugin.class)
			.jedisPool().getResource();

	public synchronized static Result index() throws UnknownHostException, MongoException {
		return ok(views.html.index.render(MongoDbClient.getInstance()
				.getAllDummies()));
	}

	/**
	 * REST method used to receive a dummy json object
	 * 
	 * @return
	 */
	@BodyParser.Of(BodyParser.Json.class)
	public synchronized static Result receiveDummy() {
		JsonNode json = request().body().asJson();
		
		try {
			jedis.publish("dummyChannel", convertNode(json));
			Logger.info("Message published to redis: " + convertNode(json));
			if(dummySockets!=null){
				for(WebSocket.Out<String> socket: dummySockets){
					socket.write(convertNode(json));
				}
				
			}
		} catch (JsonProcessingException e) {
			Logger.error("Error publishing json", e);
		}

		return ok("JSON Dummy received");
	}

	public static Result getAllDummies() {
		try {
			List<String> allDummies = MongoDbClient.getInstance()
					.getAllDummies();
			ObjectNode result = Json.newObject();
			ArrayNode arrNode = Json.newObject().arrayNode();
			for (String dummy : allDummies) {
				arrNode.add(dummy);
			}
			result.put("arrayResults", arrNode);
			return ok(result);
		} catch (UnknownHostException | MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok("Error retrieving dummies");
	}

	private static final ObjectMapper SORTED_MAPPER = new ObjectMapper();
	static {
		SORTED_MAPPER.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,
				true);
	}

	private static String convertNode(final JsonNode node)
			throws JsonProcessingException {
		final Object obj = SORTED_MAPPER.treeToValue(node, Object.class);
		final String json = SORTED_MAPPER.writeValueAsString(obj);
		return json;
	}

	public synchronized static WebSocket<String> startSocket() throws UnknownHostException, MongoException{
		return new WebSocket<String>() {

			public void onReady(WebSocket.In<String> in,
					WebSocket.Out<String> out) {
				try {
					dummySockets.add(out);
				} catch (Exception e) {
					Logger.error("Error loading socket", e);
				}
			}

		};
	}

}
