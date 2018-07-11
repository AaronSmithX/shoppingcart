package org.wecancodeit.shoppingcart;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {

	Optional<CartItem> findByProduct(Product product);

}
