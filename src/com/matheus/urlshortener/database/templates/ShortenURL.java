package com.matheus.urlshortener.database.templates;

import com.matheus.database.annotation.Column;
import com.matheus.database.annotation.Id;
import com.matheus.database.model.DatabaseModel;

public class ShortenURL extends DatabaseModel {

	@Id
	@Column
	private int id;
	@Column
	private String slug;
	@Column
	private String url;
	@Column
	private int clicks;

	public ShortenURL() {

	}

	public ShortenURL(String slug, String url, int clicks) {
		this.slug = slug;
		this.url = url;
		this.clicks = clicks;
	}

	public int getId() {
		return id;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getClicks() {
		return clicks;
	}

	public int addClick() {
		clicks++;
		return clicks;
	}

}
