package com.matheus.urlshortener.http;

import com.matheus.urlshortener.http.routes.CreateRoute;
import com.matheus.urlshortener.http.routes.InfoRoute;
import com.matheus.urlshortener.http.routes.MainPageRoute;
import com.matheus.urlshortener.http.routes.RedirectRoute;

import spark.Service;

public class Server {

	private final Service spark;
	private final String basePath = "/api";

	public void enableCors() {
		spark.before((request, response) -> {
			response.header("Access-Control-Allow-Origin", "*");
			response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			response.header("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization");
		});
	}

	public Server(int serverPort) {
		spark = Service.ignite().port(serverPort);

		String base = "/home/urlshortener";
		spark.secure(base + "/srtlink.jks", "MatheusMat010203", null, null);

		spark.staticFileLocation("com/matheus/urlshortener/http/template/frontend");
		new CreateRoute().configure(spark, basePath);
		new RedirectRoute().configure(spark, basePath);
		new MainPageRoute().configure(spark, "");
		new InfoRoute().configure(spark, "");
		System.out.println("Server running on port: " + serverPort);
	}

}
