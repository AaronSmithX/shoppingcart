package org.wecancodeit.shoppingcart;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CartItem {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@ManyToOne
	private Product product;
	
	private int qty;
	

	protected CartItem() {

	}

	public CartItem(Product product, int qty, Status status) {
		this.product = product;
		this.qty = qty;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public Product getProduct() {
		return product;
	}

	public Status getStatus() {
		return status;
	}



}
