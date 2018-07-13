package org.wecancodeit.shoppingcart;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.URI;

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
		ResponseEntity<String> response = restTemplate.getForEntity("/cart/items", String.class);
		HttpStatus status = response.getStatusCode();
		assertThat(status, is(HttpStatus.OK));
	}

	@Test
	public void shouldBeOkForAddProductInQuantity() {
		ResponseEntity<String> response = restTemplate.postForEntity("/cart/addProduct/1/inQuantity/2", null, String.class);
		HttpStatus status = response.getStatusCode();
		assertThat(status, is(HttpStatus.OK));
	}

//	@Test
//	public void shouldBeOkForUpdateItemSetQuantity() {
//		restTemplate.put("/cart/updateItem/1/setQuantity/1", null);
//	}

//	@Test
//	public void shouldBeOkForRemoveItem() {
//		restTemplate.delete("/cart/removeItem/1");
//	}

}
