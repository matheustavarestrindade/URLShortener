package com.matheus.urlshortener.http.routes;

import com.google.gson.JsonObject;
import com.matheus.urlshortener.Main;
import com.matheus.urlshortener.database.templates.ShortenURL;
import com.matheus.urlshortener.http.EndpointBuilder;
import com.matheus.urlshortener.http.HttpErrorHandler;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Service;

public class InfoRoute implements EndpointBuilder {

	@Override
	public void configure(Service spark, String basePath) {
		spark.get(basePath + "/info/:slug", info);
	};

	private Route info = (Request request, Response response) -> {
		if (request.params(":slug") == null) {
			response.status(400);
			return HttpErrorHandler.createError("O parametro SLUG é invalido ou inexistente!");
		}
		if (request.params(":slug") == null) {
			response.status(400);
			return HttpErrorHandler.createError("O parametro Slug deve ser preenchido!");
		}
		String slug = request.params(":slug");
		if (Main.getDatabase().getShortenURL(slug, false) == null) {
			response.status(400);
			return HttpErrorHandler.createError("O Slug não existem em nosso banco de dados!");
		}
		ShortenURL url = Main.getDatabase().getShortenURL(slug, false);

		JsonObject json = new JsonObject();
		json.addProperty("slug", url.getSlug());
		json.addProperty("id", url.getId());
		json.addProperty("url", url.getUrl());
		json.addProperty("short_url", "srtlink.xyz/" + url.getSlug());
		json.addProperty("clicks", url.getClicks());
		return json.toString();
	};
}
