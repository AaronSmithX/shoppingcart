package org.wecancodeit.shoppingcart;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/*
 * NOTE: Whatever class is being tested here [visible in @WebMvcTest(_____.class)]
 * NEEDS to have all of its dependencies provided as @MockBean in the Test class.
 * 
 * In this case, ProductController has 3 @Resource member variables.
 * Thus, those 3 member variables must appear here as @MockBean member variables
 * EVEN IF they are not used directly.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerMockMvcTest {
	
	// Setup
	@Resource
	private MockMvc mvc;
	
	@MockBean
	private CategoryRepository categoryRepo;

	@MockBean
	private ProductRepository productRepo;

	@MockBean
	private CartItemRepository cartRepo;
	
	// Mock Stuff
	@Mock
	private Category category;
	
	@Mock
	private Product product;
	
	
	// Home
	@Test
	public void shouldRouteToHomeCategories() throws Exception{
		mvc.perform(get("/")).andExpect(view().name(is("categories")));
	}
	
	@Test
	public void shouldBeOkForHome() throws Exception {
	mvc.perform(get("/")).andExpect(status().isOk());
	}
	
	// Categories
	@Test
	public void shouldRouteToSingleCategory() throws Exception {
		long arbitraryCategoryId = 1L;
		when(categoryRepo.findById(arbitraryCategoryId)).thenReturn(Optional.of(category));
		mvc.perform(get("/category?id=1")).andExpect(view().name(is("category")));
	}
	
	@Test
	public void shouldBeOKForCategory() throws Exception {
		long arbitraryCategoryId = 1L;
		when(categoryRepo.findById(arbitraryCategoryId)).thenReturn(Optional.of(category));
		mvc.perform(get("/category?id=1")).andExpect(status().isOk());
	}
	
	@Test
	public void shouldRouteToSingleProduct() throws Exception { 
		long arbritraryProductId = 9001;
		when(productRepo.findById(arbritraryProductId)).thenReturn(Optional.of(product));
		mvc.perform(get("/product?id=9001")).andExpect(view().name(is("product")));
	}
	
	@Test
	public void shouldBeOKForProduct() throws Exception {
		long arbitraryProductId = 9001;
		when(productRepo.findById(arbitraryProductId)).thenReturn(Optional.of(product));
		mvc.perform(get("/product?id=9001")).andExpect(status().isOk());
	}
	
	@Test
	public void shouldAddItemToCart() throws Exception {
		long arbitraryProductId = 1;
		when(productRepo.findById(arbitraryProductId)).thenReturn(Optional.of(product));
		mvc.perform(post("/addItemToCart?id=1&quantity=2")).andExpect(status().is3xxRedirection());
	}
	
	
	// Cart
	@Test
	public void shouldRouteToCart() throws Exception{
		mvc.perform(get("/cart")).andExpect(view().name(is("cart")));
	}
	
	@Test
	public void shouldBeOkForCart() throws Exception {
		mvc.perform(get("/cart")).andExpect(status().isOk());
	}
	
	
}
