package org.wecancodeit.shoppingcart;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.annotation.Resource;

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
	
	// Admin Home
	@Test
	public void shouldRouteToAdminPanelIfAdmin() throws Exception {
		mvc.perform(get("/admin?role=admin")).andExpect(view().name(is("admin")));
	}
	
	@Test
	public void shouldBeOKForAdminPanelIfAdmin() throws Exception {
		mvc.perform(get("/admin?role=admin")).andExpect(status().isOk()); // 200
	}
	
	@Test
	public void shouldBeUnauthorizedForAdminPanelIfNotAdmin() throws Exception {
		mvc.perform(get("/admin")).andExpect(status().isUnauthorized()); // 401
		mvc.perform(get("/admin?role=customer")).andExpect(status().isUnauthorized()); // 401
	}
	
	// Admin Controls
}
