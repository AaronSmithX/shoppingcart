package org.wecancodeit.shoppingcart;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;

@Entity
public class CartItem {
	
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
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
