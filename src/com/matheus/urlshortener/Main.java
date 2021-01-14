package com.matheus.urlshortener;

import com.matheus.urlshortener.database.UrlShortenerDatabase;
import com.matheus.urlshortener.http.Server;

public class Main {

	private static UrlShortenerDatabase db;

	public static void main(String[] args) {
		new Server(3001);
		db = new UrlShortenerDatabase();
	}

	public static UrlShortenerDatabase getDatabase() {
		return db;
	}

}
