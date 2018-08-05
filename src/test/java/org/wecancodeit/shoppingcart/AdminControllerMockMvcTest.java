package org.wecancodeit.shoppingcart;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerMockMvcTest {
	
	// Setup
	@Resource
	private MockMvc mvc;
	
	@MockBean
	private ImageUploadService imageUploadService;
	
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

	final Cookie adminRoleCookie = new Cookie("role", "admin");
	final Cookie customerRoleCookie = new Cookie("role", "customer");
	byte[] mockImageData = new byte[10 * 1024];
	
	@Before
	public void setup() {
		new Random().nextBytes(mockImageData);
	}
	
	// Admin Home
	@Test
	public void shouldRouteToAdminPanelIfAdmin() throws Exception {
				
		mvc.perform(get("/admin").cookie(adminRoleCookie))
		.andExpect(view().name(is("admin")));
	}
	
	@Test
	public void shouldBeOKForAdminPanelIfAdmin() throws Exception {
		mvc.perform(get("/admin").cookie(adminRoleCookie))
		.andExpect(status().isOk()); // 200
	}
	
	@Test
	public void shouldRedirectToLoginFromAdminPanelIfNotAdmin() throws Exception {
		mvc.perform(get("/admin")).andExpect(status().isFound()); // 302
		mvc.perform(get("/admin").cookie(customerRoleCookie)).andExpect(status().isFound()); // 302
	}
	
	// Admin Controls
	@Test
	public void shouldBe3xxForAdminAddCategory() throws Exception {
		mvc.perform(multipart("/admin/addCategory?name=NewCategory").file("imageFile", mockImageData))
			.andExpect(status().is3xxRedirection());
	}
	
	@Test
	public void shouldBe4xxForAdminAddCategoryIfExists() throws Exception {
		String newCategoryName = "New Category";
		when(category.getName()).thenReturn(newCategoryName);
		when(categoryRepo.findByName(newCategoryName)).thenReturn(Optional.of(category));
		
		mvc.perform(multipart("/admin/addCategory?name=" + newCategoryName).file("imageFile", mockImageData))
			.andExpect(status().is4xxClientError());
	}
}
