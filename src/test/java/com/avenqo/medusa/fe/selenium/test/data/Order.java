package com.avenqo.medusa.fe.selenium.test.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Order {

	String id;
	List<OrderedArticle> articles = new ArrayList<>();
	// i.e. Standard
	String deliveryMethod;
}
