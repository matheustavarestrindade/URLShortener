package com.matheus.urlshortener.database;

import com.google.gson.JsonObject;
import com.matheus.database.database.Database;
import com.matheus.database.database.DatabaseConfig;
import com.matheus.database.model.Table;
import com.matheus.urlshortener.database.templates.ShortenURL;
import com.matheus.urlshortener.http.HttpErrorHandler;

public class UrlShortenerDatabase {

	private Table<ShortenURL> urlsTable;

	public UrlShortenerDatabase() {
		DatabaseConfig.Builder builder = DatabaseConfig.builder("localhost", "urlshortener", "root", "db password");
		builder.reconnect(true).ssl(false);

		DatabaseConfig config = builder.build();
		Database database = new Database(config);
		database.connect();

		urlsTable = new Table<ShortenURL>(database, "urls", ShortenURL.class);
		try {
			urlsTable.create();
		} catch (Exception e) {
			System.out.println("Tabela de URLS já existe!");
		}
	}

	public void deleteShortenURL(String slug) {
		try {
			urlsTable.delete().where("slug", slug).execute();
		} catch (Exception e) {
			System.out.println("URL não encontrado no bando de dados");
		}
	}

	public ShortenURL getShortenURL(String slug, boolean countClick) {
		try {
			ShortenURL url = urlsTable.select().where("slug", slug).limit(1).execute().first();
			if (countClick) {
				addClick(url);
			}
			return url;
		} catch (Exception e) {
			System.out.println("URL não encontrado no bando de dados");
			return null;
		}
	}

	public String createShortenURL(ShortenURL url) {
		JsonObject json = new JsonObject();
		try {
			urlsTable.insert(url).execute();
			json.addProperty("slug", url.getSlug());
			json.addProperty("id", url.getId());
			json.addProperty("url", url.getUrl());
			json.addProperty("short_url", "srtlink.xyz/" + url.getSlug());
			return json.toString();
		} catch (Exception e) {
			System.out.println("Error ao adicionar o URL no banco de dados!");
			return HttpErrorHandler.createError("Não foi possivel inserir o URL no banco de dados!");
		}
	}

	private void addClick(ShortenURL url) {
		try {
			urlsTable.update().where("id", url.getId()).set("clicks", url.addClick()).execute();
		} catch (Exception e) {
			System.out.println("URL não encontrado no bando de dados");
		}
	}

}
