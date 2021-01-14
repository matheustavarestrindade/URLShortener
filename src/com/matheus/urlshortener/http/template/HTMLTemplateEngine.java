package com.matheus.urlshortener.http.template;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import spark.ModelAndView;
import spark.TemplateEngine;

public class HTMLTemplateEngine extends TemplateEngine {

	@Override
	public String render(ModelAndView modelAndView) {
		try {
			URL url = getClass().getResource("frontend/" + modelAndView.getViewName());
			Path path = Paths.get(url.toURI());
			return new String(Files.readAllBytes(path), Charset.defaultCharset());
		} catch (IOException | URISyntaxException e) {
			// Add your own exception handlers here.
		}
		return null;
	}
}