package com.matheus.urlshortener.http.routes;

import com.matheus.urlshortener.Main;
import com.matheus.urlshortener.database.templates.ShortenURL;
import com.matheus.urlshortener.http.EndpointBuilder;
import com.matheus.urlshortener.http.HttpErrorHandler;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Service;

public class RedirectRoute implements EndpointBuilder {

	@Override
	public void configure(Service spark, String basePath) {
		spark.get("/:slug", redirect);
	}

	private Route redirect = (Request request, Response response) -> {
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
		ShortenURL url = Main.getDatabase().getShortenURL(slug, true);
		response.redirect(url.getUrl());
		response.status(200);
		return null;
	};
}
