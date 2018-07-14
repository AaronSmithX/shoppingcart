package org.wecancodeit.shoppingcart;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CartItemRestControllerTest {

	// Use the TestRestTemplate to test the CLIENT side of an application
	// In other words, use it to test whether a connection can be made to each URL
	// MockMvc is for testing the SERVER side and ensuring correctness of data
	@Resource
	private TestRestTemplate restTemplate;
	
	
	@Test
	public void shouldBeOkForCartItems() {
		ResponseEntity<String> response =
				restTemplate.getForEntity("/cart/items", String.class);
		HttpStatus status = response.getStatusCode();
		assertThat(status, is(HttpStatus.OK));
	}

	@Test
	public void shouldBeOkForGetItemQuantity() {
		ResponseEntity<String> response =
				restTemplate.getForEntity("/cart/itemQuantity", String.class);
		HttpStatus status = response.getStatusCode();
		assertThat(status, is(HttpStatus.OK));
	}

	@Test
	public void shouldBeOkForAddProductInQuantity() {
		// TODO: Build a form and send it
		ResponseEntity<String> response = restTemplate.postForEntity("/cart/addProduct/1/inQuantity/2", null, String.class);
		HttpStatus status = response.getStatusCode();
		assertThat(status, is(HttpStatus.OK));
	}

	@Test
	public void shouldBeOkForUpdateItem() throws URISyntaxException {
		
		// URI where we will send request
		URI putUri = new URI("/cart/updateItem");
		
		// JSON we will send
		String json = "{ \"id\":\"1\", \"quantity\":\"3\" }";
		
		// Build request out of URI and form
		RequestEntity<String> request =
				RequestEntity.put(putUri) // is a PUT request to this URI
				.header("Content-Type", "application/json") // send body as JSON
				.accept(MediaType.APPLICATION_JSON) // accept JSON response
				.body(json); // add the JSON as the request body
		
		// Get response by sending request
		ResponseEntity<String> response = restTemplate.exchange(request, String.class);
		
		// Ensure status is OK
		HttpStatus status = response.getStatusCode();
		assertThat(status, is(HttpStatus.OK));
	}


	@Test
	public void shouldBeOkForRemoveItem() throws URISyntaxException {
		
		URI deleteUri = new URI("/cart/removeItem/1");
		
		RequestEntity<Void> request =
				RequestEntity.delete(deleteUri)
				.accept(MediaType.APPLICATION_JSON)
				.build();
		
		ResponseEntity<String> response = restTemplate.exchange(request, String.class);
		
		HttpStatus status = response.getStatusCode();
		assertThat(status, is(HttpStatus.OK));
	}

}
