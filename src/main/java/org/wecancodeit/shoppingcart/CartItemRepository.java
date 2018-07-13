package org.wecancodeit.shoppingcart;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {

	Optional<CartItem> findByProduct(Product product);
	
	default int addItemInQuantity(Product product, int quantity) {
		
		Optional<CartItem> foundItem = this.findByProduct(product);
		CartItem item;
		
		if (foundItem.isPresent()) {
			item = foundItem.get();
			item.setQuantity(item.getQuantity() + quantity);
		}
		else {
			item = new CartItem(product, quantity, Status.WIP);
		}
		
		this.save(item);
		
		return item.getQuantity();
	}

}
