package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import play.*;
import play.mvc.*;
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
		return ok("JSON Dummy received");
	}

}
