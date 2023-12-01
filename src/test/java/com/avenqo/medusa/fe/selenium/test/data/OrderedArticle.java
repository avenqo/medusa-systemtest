package com.avenqo.medusa.fe.selenium.test.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import lombok.Data;

@Data
public class OrderedArticle {

	String name;
	String size;
	String color;
	String number;
	String articlePrice;
	String summaryPrice;
	
	/**
	 * Find single article by unique name
	 * 
	 * @param articles
	 * @param name
	 * @return
	 */
	public static OrderedArticle getByName(List<OrderedArticle> articles, String name) {
		List<OrderedArticle> foundArticles = articles.stream().filter(a -> a.name.equals(name)).toList();
		assertTrue(foundArticles.size() < 2, "Artcle name [" + name + "] isn't unique.");
		return foundArticles.size() == 0 ? null : foundArticles.get(0);
	}
	
	public void addVariant(String key, String value) {
		String variant = key.toLowerCase().trim();

		if (variant.equals("color"))
			color = value;
		else if (variant.equals("size"))
			size = value;
		else
			throw new RuntimeException("Don't know how to handle variant [" + key + "] and value [" + value + "]");
	}
}
