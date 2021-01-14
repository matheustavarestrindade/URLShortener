package com.matheus.urlshortener.http.routes;

import java.util.HashMap;

import com.matheus.urlshortener.http.EndpointBuilder;
import com.matheus.urlshortener.http.template.HTMLTemplateEngine;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Service;

public class MainPageRoute implements EndpointBuilder {

	@Override
	public void configure(Service spark, String basePath) {
		spark.get("/", mainPage);
	}

	private Route mainPage = (Request request, Response response) -> {
		return new HTMLTemplateEngine().render(new ModelAndView(new HashMap<String, Object>(), "index.html"));
	};
}
