package org.wecancodeit.shoppingcart;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class JPAMappingsTest {

	@Resource
	private ProductRepository productRepo;

	@Resource
	private CategoryRepository categoryRepo;

	@Resource
	private CartItemRepository cartRepo;

	@Resource
	private EntityManager entityManager;

	@Test
	public void shouldSaveAndLoadProduct() {
		Product product = productRepo.save(new Product("Widget"));
		long productId = product.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<Product> result = productRepo.findById(productId);
		Product resultProduct = result.get();
		assertThat(resultProduct.getName(), is("Widget"));
	}

	@Test
	public void shouldSaveAndLoadCategory() {
		Category category = categoryRepo.save(new Category("Gadgets"));
		long categoryId = category.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<Category> result = categoryRepo.findById(categoryId);
		Category resultCategory = result.get();
		assertThat(resultCategory.getName(), is("Gadgets"));
	}

	@Test
	public void shouldEstablishProductCategoryRelationship() {
		Category category = categoryRepo.save(new Category("Gadgets"));
		Product product1 = productRepo.save(new Product("Widget", category));
		Product product2 = productRepo.save(new Product("iPhone", category));
		long categoryId = category.getId();
		long productId1 = product1.getId();
		long productId2 = product2.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<Category> categoryOptional = categoryRepo.findById(categoryId);
		Optional<Product> productOptional1 = productRepo.findById(productId1);
		Optional<Product> productOptional2 = productRepo.findById(productId2);
		Category resultCategory = categoryOptional.get();
		Product resultProduct1 = productOptional1.get();
		Product resultProduct2 = productOptional2.get();
		assertThat(resultProduct1.getCategory().getName(), is("Gadgets"));
		assertThat(resultCategory.getProducts(), containsInAnyOrder(resultProduct1, resultProduct2));
	}

	@Test
	public void shouldSaveAndLoadCartWithStatus() {
		
		Product product = productRepo.save(new Product("Widget"));
				
		CartItem cart = cartRepo.save(new CartItem(product, 6, Status.WIP));
		long cartItemId = cart.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<CartItem> cartItemOptional = cartRepo.findById(cartItemId);
		CartItem resultCartItem = cartItemOptional.get();
		
		assertThat(resultCartItem.getProduct().getName(), is("Widget"));
		assertThat(resultCartItem.getStatus(), is(Status.WIP));
	}
}
