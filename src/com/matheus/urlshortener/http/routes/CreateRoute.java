package com.matheus.urlshortener.http.routes;

import java.util.Random;

import com.matheus.urlshortener.Main;
import com.matheus.urlshortener.database.templates.ShortenURL;
import com.matheus.urlshortener.http.EndpointBuilder;
import com.matheus.urlshortener.http.HttpErrorHandler;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Service;

public class CreateRoute implements EndpointBuilder {

	@Override
	public void configure(Service spark, String basePath) {
		spark.get(basePath + "/create", create);
	};

	private Route create = (Request request, Response response) -> {
		if (request.queryParams("url") == null) {
			response.status(400);
			return HttpErrorHandler.createError("O parametro URL é invalido ou inexistente!");
		}
		String slug = "";
		if (request.queryParams("slug") != null) {
			slug = request.queryParams("slug");
		} else {
			slug = getSaltString();
		}
		if (slug.equalsIgnoreCase("api") || Main.getDatabase().getShortenURL(slug, false) != null) {
			return HttpErrorHandler.createError("O Slug já está em utilização, por favor selecione outro!");
		}

		String redirectUrl = request.queryParams("url");
		ShortenURL url = new ShortenURL(slug, redirectUrl, 0);
		response.status(200);
		return Main.getDatabase().createShortenURL(url);
	};

	private String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 20) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

}
