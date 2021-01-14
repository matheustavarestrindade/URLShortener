package com.matheus.urlshortener.http;

import com.google.gson.JsonObject;

public class HttpErrorHandler {

	public static String createError(String error) {
		JsonObject json = new JsonObject();
		json.addProperty("error", error);
		return json.toString();
	}

}
