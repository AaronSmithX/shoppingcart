package org.wecancodeit.shoppingcart;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerMockMvcTest {
	// Setup
	@Resource
	private MockMvc mvc;
	
	@MockBean
	private CategoryRepository categoryRepo;
	
	// Mock Stuff
	@Mock
	private Category category;
	
	
	
	// home
	@Test
	public void shouldRouteToHome() throws Exception{
		mvc.perform(get("/")).andExpect(view().name(is("index")));
	}
	
	@Test
	public void shouldBeOkForHome() throws Exception{
	mvc.perform(get("/")).andExpect(status().isOk());
	}
	
	// Categories
	@Test
	public void shouldRouteToSingleCategory() throws Exception{
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
	
	
	
}
