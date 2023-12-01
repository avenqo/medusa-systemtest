package com.avenqo.medusa.fe.selenium.test.steps;

import static java.lang.invoke.MethodHandles.lookup;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.avenqo.medusa.be.api.test.OrderApi;
import com.avenqo.medusa.fe.selenium.test.data.Address;
import com.avenqo.medusa.fe.selenium.test.data.Customer;
import com.avenqo.medusa.fe.selenium.test.data.CustomerFactory;
import com.avenqo.medusa.fe.selenium.test.data.DeliveryAddress;
import com.avenqo.medusa.fe.selenium.test.data.Order;
import com.avenqo.medusa.fe.selenium.test.data.OrderedArticle;
import com.avenqo.medusa.fe.selenium.test.data.TestDataProvider;
import com.avenqo.medusa.fe.selenium.test.pages.BasketPage;
import com.avenqo.medusa.fe.selenium.test.pages.CheckoutPage;
import com.avenqo.medusa.fe.selenium.test.pages.LandingPage;
import com.avenqo.medusa.fe.selenium.test.pages.NavigationBar;
import com.avenqo.medusa.fe.selenium.test.pages.OrderConfirmationPage;
import com.avenqo.medusa.fe.selenium.test.pages.ProductDetailsPage;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.ValidatableResponse;

public class StoreFrontendSteps {

	private static final String DELIVERY_STANDARD = "Standard";

	static final Logger LOG = getLogger(lookup().lookupClass());

	private static final String STEP = "";
	
	private OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage(); 
	private NavigationBar navigationBar = new NavigationBar();
	private BasketPage basketPage = new BasketPage();
	private CheckoutPage checkoutPage = new CheckoutPage();
	private LandingPage landingPage = new LandingPage();
	private ProductDetailsPage productDetailsPage = new ProductDetailsPage();
	
	@Given("ich bin ein unregistrierter Kunde")
	public void unregisteredUser(DataTable dataTable) {
		LOG.debug(STEP);
		Map<String, String> items = dataTable.asMap();
		TestDataProvider.get().setCurrentCustomer(CustomerFactory.getUnregisteredUser());
		
		//Customer
		Customer customer = TestDataProvider.get().getCurrentCustomer();
		customer.setFirstName(items.get("Vorname"));
		customer.setLastName(items.get("Nachname"));
		customer.setEmail(items.get("E-Mail"));
		
		//Address
		Address address = customer.getAddress();
		address.setCity(items.get("Ort"));
		address.setCode(items.get("PLZ"));
		address.setStreet(items.get("Strasse"));
		customer.setAddress(address);
	}
	
	@Given("ich wähle Produkte aus dem Store und lege sie in den Warenkorb")
	public void ich_waehle_das_produkt(DataTable dataTable) {
		LOG.debug(STEP);

		Order order = TestDataProvider.get().getCurrentOrder();
		
		for (Map<String, String> rowMap : dataTable.asMaps()) {
			
			navigationBar.gotoStore();
			landingPage.waitUntilVisible();
			landingPage.selectProductByName(rowMap.get("Artikelname"));
			
			productDetailsPage.waitUntilVisible();

			// select variants
			String color = rowMap.get("Color");
			if (StringUtils.isNotBlank(color))
				productDetailsPage.selectVariant(color);
			String size = rowMap.get("Size");
			if (StringUtils.isNotBlank(size))
				productDetailsPage.selectVariant(size);

			// set Anzahl
			String count = rowMap.get("Anzahl").trim();
			for (int i = 0; i < Integer.parseInt(count); i++) {
				int basketCount = navigationBar.getNumberOfArticlesInBasket();
				productDetailsPage.addToCart();
				navigationBar.waitUntilBagIsContainingItems(basketCount + 1);
			}
			
			//store for further usage
			OrderedArticle orderedArticle = new OrderedArticle();
			orderedArticle.setNumber(count);
			orderedArticle.setName(productDetailsPage.getProductName());
			orderedArticle.setArticlePrice(productDetailsPage.getPrice());
			orderedArticle.setColor(color);
			orderedArticle.setSize(size);
			order.getArticles().add(orderedArticle);
		}
	}
	
	@Given("ich gehe zum Warenkorb")
	public void ich_gehe_zum_warenkorb() {
		LOG.debug(STEP);
		navigationBar.gotoBag();
		basketPage.waitUntilVisible();
	}
	
	
	@Given("ich gehe zur Kasse")
	public void ich_gehe_zur_kasse() {
		LOG.debug(STEP);
		basketPage.checkout();
		checkoutPage.waitUntilVisible();
	}
	
	@Given("ich gebe meine minimalen Versandinformationen ein")
	public void ich_gebe_meine_minimalen_versandinformationen_ein() {
		LOG.debug(STEP);
		checkoutPage.enterMinimalAddressAndContinue(TestDataProvider.get().getCurrentCustomer());
	}
	
	@Given("ich wähle die Versandmethode {string}")
	public void ich_wähle_die_versandmethode(String string) {
		LOG.debug(STEP);
		checkoutPage.selectDeliveryService(DELIVERY_STANDARD);
	}
	
	//@When("ich (die|das) Produkt(e) kaufe")
	@When("^ich (?:die|das) Produkte? kaufe$")
	public void ich_das_produkt_kaufe() {
		LOG.debug(STEP);
		checkoutPage.pushCheckout();
	}
	
	@Then("enthält die Bestellbestätigung meine Kundendaten und die Bestellnummer")
	public void die_bestellbestätigung_enthält_meine_kundendaten() {
		LOG.debug(STEP);
		DeliveryAddress deliveryAddress = orderConfirmationPage.getDeliveryAddress();
		Customer currentCustomer = TestDataProvider.get().getCurrentCustomer();
		assertEquals(currentCustomer.getFirstName() + " " + currentCustomer.getLastName(), deliveryAddress.getName());

		Address address = currentCustomer.getAddress();
		assertEquals(address.getStreet(), deliveryAddress.getAddress());
		assertEquals(address.getCity() + ", " + address.getCode(), deliveryAddress.getCityCode());
		assertTrue(deliveryAddress.getDeliveryMethod().contains(DELIVERY_STANDARD));
		

		String id = orderConfirmationPage.getOrderId();
		assertTrue(StringUtils.isNotBlank(id));
		TestDataProvider.get().getCurrentOrder().setId("order_" + id);
	}
	
	@Then("die Bezahlung der aktuellen Bestellung ist veranlasst")
	public void die_bestellbestätigung_enthält_meine_zahlmethode(io.cucumber.datatable.DataTable dataTable) {
		LOG.debug(STEP);
		ValidatableResponse validatableResponse = new OrderApi().getOrderById(TestDataProvider.get().getCurrentOrder().getId());
		
		Map<String, String> items = dataTable.asMap();
		
		String betrag = items.get("Gesamtbetrag").replace(".", "");
		validatableResponse.body("order.paid_total",equalTo( Integer.valueOf (betrag)));
		
		validatableResponse.body("order.currency_code", equalTo(items.get("Währung")));
		validatableResponse.body("order.payment_status", equalTo(items.get("Payment Status")));
	}
	
	@Then("die Bestellbestätigung enthält die Artikel")
	public void die_bestellbestätigung_enthält_die_artikel(io.cucumber.datatable.DataTable dataTable) {
		LOG.debug(STEP);
		
		// check size
		List<OrderedArticle> articles = orderConfirmationPage.getArticles();
		List<Map<String, String>> rows = dataTable.asMaps();
		assertEquals(rows.size(), articles.size(), "The number of articles are different.");
		
		// check article by article
		for (Map<String, String> row : rows) {
			//find article by name
			String name = row.get("Name");
			OrderedArticle foundArticle = OrderedArticle.getByName(articles, name);
			assertNotNull(foundArticle, "Article not found by name ["+name+"]");
			
			//proceed with option check
			assertEquals(row.get("Color"), foundArticle.getColor());
			assertEquals(row.get("Size"), foundArticle.getSize());
			assertEquals(row.get("Anzahl"), foundArticle.getNumber());
			assertEquals(row.get("Einzelpreis"), foundArticle.getArticlePrice());
			assertEquals(row.get("Gesamtpreis"), foundArticle.getSummaryPrice());
		}
	}
	
	@Then("die Bestellbestätigung enthält in der Zusammenfassung")
	public void die_bestellbestätigung_enthält(io.cucumber.datatable.DataTable dataTable) {
		LOG.debug(STEP);
		Map<String, String> items = dataTable.asMap();
		assertEquals(items.get("Teilkosten"), orderConfirmationPage.getSubtotal());
		assertEquals(items.get("Versandkosten"), orderConfirmationPage.getDeliveryPrice());
		assertEquals(items.get("Gesamtkosten"), orderConfirmationPage.getTotal());
		assertEquals(items.get("Steuer"), orderConfirmationPage.getTax());
	}
/*
 * 
 * 
	@Given("ich wähle das Produkt {string}")
	public void ich_waehle_das_produkt(String productName) {
		LOG.debug(STEP);
		landingPage.selectProductByName(productName);
		productDetailsPage.waitUntilVisible();
	}
	
	@Given("ich lege das selektierte Produkt in den Warenkorb")
	public void ich_lege_das_selektierte_produkt_in_den_warenkorb() {
		LOG.debug(STEP);
		productDetailsPage.addToCart();
		navigationBar.waitUntilBagIsContainingItems(1);
	}
	

	@Then("sehe ich die Bestellbestätigung")
	public void sehe_ich_die_bestellbestätigung() {
		LOG.debug(STEP);
		orderConfirmationPage.waitUntilVisible();
		TestDataProvider.get().initOrder();
		
		String id = orderConfirmationPage.getOrderId();
		assertThat (id, not(emptyOrNullString()));
		TestDataProvider.get().getCurrentOrder().setId("order_" + id);
	}

	@Then("die Bestellbestätigung enthält meine Kundendaten")
	public void die_bestellbestätigung_enthält_meine_kundendaten() {
		LOG.debug(STEP);
		DeliveryAddress deliveryAddress = orderConfirmationPage.getDeliveryAddress();
		Customer currentCustomer = TestDataProvider.get().getCurrentCustomer();
		assertEquals(currentCustomer.getFirstName() + " " + currentCustomer.getLastName(), deliveryAddress.getName());

		Address address = currentCustomer.getAddress();
		assertEquals(address.getStreet(), deliveryAddress.getAddress());
		assertEquals(address.getCity() + ", " + address.getCode(), deliveryAddress.getCityCode());
		assertTrue(deliveryAddress.getDeliveryMethod().contains(DELIVERY_STANDARD));
	}

	*/
}
