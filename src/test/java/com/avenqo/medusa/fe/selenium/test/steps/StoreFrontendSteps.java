package com.avenqo.medusa.fe.selenium.test.steps;

import static java.lang.invoke.MethodHandles.lookup;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.avenqo.medusa.be.api.test.OrderApi;
import com.avenqo.medusa.fe.selenium.test.data.Address;
import com.avenqo.medusa.fe.selenium.test.data.Customer;
import com.avenqo.medusa.fe.selenium.test.data.CustomerFactory;
import com.avenqo.medusa.fe.selenium.test.data.DeliveryAddress;
import com.avenqo.medusa.fe.selenium.test.data.TestDataProvider;
import com.avenqo.medusa.fe.selenium.test.pages.BasketPage;
import com.avenqo.medusa.fe.selenium.test.pages.CheckoutPage;
import com.avenqo.medusa.fe.selenium.test.pages.LandingPage;
import com.avenqo.medusa.fe.selenium.test.pages.NavigationBar;
import com.avenqo.medusa.fe.selenium.test.pages.OrderConfirmationPage;
import com.avenqo.medusa.fe.selenium.test.pages.ProductDetailsPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.ValidatableResponse;

public class StoreFrontendSteps {

	private static final String DELIVERY_STANDARD = "Standard";

	static final Logger LOG = getLogger(lookup().lookupClass());

	private static final String STEP = "";
	
	private OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage(); 

	@Given("ich bin ein unregistrierter Kunde")
	public void unregisteredUser() {
		LOG.debug(STEP);
		TestDataProvider.get().setCurrentCustomer(CustomerFactory.getUnregisteredUser());
	}

	@Given("ich lege das Produkt {string} in den Warenkorb")
	public void ich_lege_das_produkt_in_den_warenkorb(String productName) {
		LOG.debug(STEP);
		LandingPage landingPage = new LandingPage();
		landingPage.selectProductByName(productName);

		ProductDetailsPage productDetailsPage = new ProductDetailsPage();
		productDetailsPage.waitUntilVisible();
		productDetailsPage.addToCart();

		NavigationBar navigationBar = new NavigationBar();
		navigationBar.waitUntilBagIsContainingItems(1);

		assertEquals(1, navigationBar.getNumberOfArticlesInBasket(), "Number of articles in basket is wrong.");
	}

	@Given("ich gehe mit dem Warenkorb zur Kasse")
	public void ich_gehe_mit_dem_Warenkorb_zur_kasse() {
		LOG.debug(STEP);
		NavigationBar navigationBar = new NavigationBar();
		navigationBar.gotoBag();

		BasketPage basketPage = new BasketPage();
		basketPage.waitUntilVisible();
		basketPage.checkout();

		CheckoutPage checkoutPage = new CheckoutPage();
		checkoutPage.waitUntilVisible();

	}

	@Given("ich gebe meine minimalen Versandinformationen ein")
	public void ich_gebe_meine_minimalen_versandinformationen_ein() {
		LOG.debug(STEP);
		CheckoutPage checkoutPage = new CheckoutPage();
		checkoutPage.enterMinimalAddressAndContinue(TestDataProvider.get().getCurrentCustomer());
	}

	@Given("ich wähle die Versandmethode {string}")
	public void ich_wähle_die_versandmethode(String string) {
		LOG.debug(STEP);
		new CheckoutPage().selectDeliveryService(DELIVERY_STANDARD);
	}

	@When("ich das Produkt kaufe")
	public void ich_das_produkt_kaufe() {
		LOG.debug(STEP);
		new CheckoutPage().pushCheckout();
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
		DeliveryAddress deliveryAddress = new OrderConfirmationPage().getDeliveryAddress();
		Customer currentCustomer = TestDataProvider.get().getCurrentCustomer();
		assertEquals(currentCustomer.getFirstName() + " " + currentCustomer.getLastName(), deliveryAddress.getName());

		Address address = currentCustomer.getAddress();
		assertEquals(address.getStreet(), deliveryAddress.getAddress());
		assertEquals(address.getCity() + ", " + address.getCode(), deliveryAddress.getCityCode());
		assertTrue(deliveryAddress.getDeliveryMethod().contains(DELIVERY_STANDARD));
	}

	@Then("die Bestellbestätigung enthält die Artikel")
	public void die_bestellbestätigung_enthält_die_artikel(io.cucumber.datatable.DataTable dataTable) {
		LOG.debug(STEP);

		List<Map<String, String>> rows = dataTable.asMaps();
		OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage();
		assertEquals(rows.get(0).get("Artikel"), orderConfirmationPage.getArticle());
		assertEquals(rows.get(0).get("Variante"), orderConfirmationPage.getVariant());
		assertEquals(rows.get(0).get("Anzahl"), orderConfirmationPage.getCount());
		assertEquals(rows.get(0).get("Preis"), orderConfirmationPage.getPrice());
	}

	@Then("die Bestellbestätigung enthält in der Zusammenfassung")
	public void die_bestellbestätigung_enthält(io.cucumber.datatable.DataTable dataTable) {
		LOG.debug(STEP);

		Map<String, String> items = dataTable.asMap();

		OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage();
		assertEquals(items.get("Teilkosten"), orderConfirmationPage.getSubtotal());
		assertEquals(items.get("Versandkosten"), orderConfirmationPage.getDeliveryPrice());
		assertEquals(items.get("Gesamtkosten"), orderConfirmationPage.getTotal());
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

}
